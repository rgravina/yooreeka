package iweb2.ch2.ranking;

import iweb2.ch2.webcrawler.CrawlDataProcessor;
import iweb2.ch2.webcrawler.model.ProcessedDocument;
import iweb2.util.TermFreqMapUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.FSDirectory;

public class DocRankMatrixBuilder implements CrawlDataProcessor {
    
//    private static final Logger logger = Logger.getLogger(PageRankMatrixBuilder.class);

	private final int TERMS_TO_KEEP = 3;
	
	private int termsToKeep=0;
	
	private String indexDir;
    private PageRankMatrixH matrixH;

    public DocRankMatrixBuilder(String indexDir) {
        this.indexDir = indexDir;
    }
    
    public PageRankMatrixH getH() {
        return matrixH;
    }
    
    /*
     * Collects doc ids from the index for documents with matching doc type.
     */
    private List<Integer> getProcessedDocs(IndexReader idxR) 
        throws IOException {
        List<Integer> docs = new ArrayList<Integer>();
        for(int i = 0, n = idxR.maxDoc(); i < n; i++) {
            if( idxR.isDeleted(i) == false ) {
                Document doc = idxR.document(i);
                if( eligibleForDocRank(doc.get("doctype") ) ) {
                    docs.add(i);
                }
            }
        }
        return docs;
        
    }
    
    /*
     * Checks if the index entry belongs to the category that we want to use
     * DocRank on.
     */
    private boolean eligibleForDocRank(String doctype) {
        return ProcessedDocument.DOCUMENT_TYPE_MSWORD.equalsIgnoreCase(doctype);
    }
    
    private PageRankMatrixH buildMatrixH(IndexReader idxR) 
        throws IOException {

    	// only consider URLs that with fetched and parsed content
        List<Integer> allDocs = getProcessedDocs(idxR);
        
        PageRankMatrixH docMatrix = new PageRankMatrixH( allDocs.size() );
        
        for(int i = 0, n = allDocs.size(); i < n; i++) {
        
        	for(int j = 0, k = allDocs.size(); j < k; j++) {
            	
    			double similarity = 0.0d;
    			
                Document docX = idxR.document(i);
    			String xURL= docX.get("url");
    			
    			if ( i == j ) {
    				
    				// Avoid shameless self-promotion ;-)
        			docMatrix.addLink(xURL, xURL, similarity);
        		
    			} else {
        		
	                TermFreqVector x = idxR.getTermFreqVector(i, "content");
	                TermFreqVector y = idxR.getTermFreqVector(j, "content");
	                
	                similarity = getImportance(x.getTerms(), x.getTermFrequencies(), 
	                		                   y.getTerms(), y.getTermFrequencies());
	                
	                // add link from docX to docY 
	                Document docY = idxR.document(j);
	                String yURL = docY.get("url");
	                
	                docMatrix.addLink(xURL, yURL, similarity);
	            }
	        }
        }        
        
        docMatrix.calculate();
        
        return docMatrix;
    }
    
    /*
     * Calculates importance of document Y in the context of document X
     */
    private double getImportance(String[] xTerms, int[] xTermFreq, 
                                 String[] yTerms, int[] yTermFreq) {
    
    	// xTerms is an array of the most frequent terms for the first document
        Map<String, Integer> xFreqMap = buildFreqMap(xTerms, xTermFreq);

        // yTerms is an array of the most frequent terms for the second document
        Map<String, Integer> yFreqMap = buildFreqMap(yTerms, yTermFreq);

        // sharedTerms is the intersection of the two sets
        Set<String> sharedTerms = new HashSet<String>(xFreqMap.keySet());
        sharedTerms.retainAll(yFreqMap.keySet());
        
        double sharedTermsSum = 0.0;
        
        // Notice that this way of assigning importance is not symmetric.
        // That is, if you swap X with Y then you get a different value; 
        // unless the frequencies are equal, of course!
        
        double xF, yF;
        for(String term : sharedTerms) {
        	
        	xF = xFreqMap.get(term).doubleValue();
        	yF = yFreqMap.get(term).doubleValue();
            
        	
        	
        	sharedTermsSum += Math.round(Math.tanh(yF/xF));
        }
        
        
        return sharedTermsSum;
    }
    
    private Map<String, Integer> buildFreqMap(String[] terms, int[] freq) {
        
        int topNTermsToKeep = (termsToKeep == 0)? TERMS_TO_KEEP: termsToKeep;
        
        Map<String, Integer> freqMap = 
            TermFreqMapUtils.getTopNTermFreqMap(terms, freq, topNTermsToKeep);
        
        return freqMap;
    }

    public void run() {
        try {
            IndexReader idxR = IndexReader.open(FSDirectory.getDirectory(indexDir));
            matrixH = buildMatrixH(idxR);
        }
        catch(Exception e) {
            throw new RuntimeException("Error while building matrix: ", e);
        }
    }

	/**
	 * @return the termsToKeep
	 */
	public int getTermsToKeep() {
		return termsToKeep;
	}

	/**
	 * @param termsToKeep the termsToKeep to set
	 */
	public void setTermsToKeep(int termsToKeep) {
		this.termsToKeep = termsToKeep;
	}
    
}
