package iweb2.ch3.collaborative.model;

import iweb2.util.analyzer.CustomAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;


public class Content implements java.io.Serializable {

    /**
	 * SVUID
	 */
	private static final long serialVersionUID = 1098727290087922462L;
	
	private String id;
    private String text;
    private String[] terms;
    private int[] termFrequencies;
    private Map<String, Integer> tfMap;
    

    public Content(String id, String text) {
        this(id, text, 10);
    }
    
    public Content(String id, String text, int topNTerms) {
        this.id = id;
        this.text = text;

        
        Map<String, Integer> allTermFrequencyMap = buildTermFrequencyMap(text);
        tfMap = getTopNTermFrequencies(allTermFrequencyMap, topNTerms);

        terms = new String[tfMap.size()];
        termFrequencies = new int[tfMap.size()];
        
        int i = 0;
        for(Map.Entry<String, Integer> e : tfMap.entrySet()) {
            terms[i] = e.getKey();
            termFrequencies[i] = e.getValue();
            i++;
        }
    }
    
    public Map<String, Integer> getTFMap() {
        return this.tfMap;
    }
    
    public int[] getTermFrequencies() {
        return termFrequencies;
    }

    public String[] getTerms() {
        return terms;
    }
    
    public double[] getTermVector(String[] terms) {
        double[] termVector = new double[terms.length];
        for(int i = 0, n = terms.length; i < n; i++) {
            if( tfMap.containsKey(terms[i]) ) {
                termVector[i] = 1;
            }
            else {
                termVector[i] = 0;
            }
        }
        return termVector;
    }
    
    public String getText() {
        return text;
    }
    
    public String getId() {
        return id;
    }
    
    private Map<String, Integer> buildTermFrequencyMap(String text) {

        Analyzer analyzer = new CustomAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));

        Map<String, Integer> termFrequencyMap = new HashMap<String, Integer>();        

        boolean hasTokens = true;
        try {
            while (hasTokens) {
                Token t = tokenStream.next();
                if (t == null) {
                    hasTokens = false;
                } else {
                    String term = new String(t.termBuffer(), 0, t.termLength());
                    Integer frequency = termFrequencyMap.get(term);
                    if( frequency == null ) {
                        termFrequencyMap.put(term, 1);
                    }
                    else {
                        termFrequencyMap.put(term, frequency + 1);
                    }
                }
            }
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        
       return termFrequencyMap;
    }
    
    private Map<String, Integer> getTopNTermFrequencies(
            Map<String, Integer> termFrequencyMap, int topN) {

        List<Map.Entry<String, Integer>> terms = 
            new ArrayList<Map.Entry<String, Integer>>(termFrequencyMap.entrySet());

        // Different terms can have the same frequency.
        Collections.sort(terms, 
                new Comparator<Map.Entry<String, Integer>>() {
                    public int compare(Map.Entry<String, Integer> e1, 
                            Map.Entry<String, Integer> e2) { 
                        int result = 0;
                        if( e1.getValue() < e2.getValue() ) {
                            result = 1; // reverse order
                        }
                        else if( e1.getValue() > e2.getValue() ) {
                            result = -1;
                        }
                        else {
                            result = 0;
                        }
                        return result;
                    }
                }
       );
        
       Map<String, Integer> topNTermsFrequencyMap = new HashMap<String, Integer>();
       for(Map.Entry<String, Integer> term : terms) {
           topNTermsFrequencyMap.put(term.getKey(), term.getValue());
           if( topNTermsFrequencyMap.size() >= topN ) {
               break;
           }
       }
      
       return topNTermsFrequencyMap;
    }
    
}
