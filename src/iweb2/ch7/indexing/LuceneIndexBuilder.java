package iweb2.ch7.indexing;

import iweb2.ch7.core.NewsDataset;
import iweb2.ch7.core.NewsStory;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

public class LuceneIndexBuilder {

    private File indexDir;
    private NewsDataset ds;
    private long indexedDocCount;
    
    
    public LuceneIndexBuilder(File indexDir, NewsDataset ds) {

    	this.indexDir = indexDir;
    	this.ds = ds;
        this.indexedDocCount = 0;
        
    	try {
			IndexWriter indexWriter = new IndexWriter(indexDir,	new StandardAnalyzer(), true);
			indexWriter.close();
		} catch (IOException ioX) {
			throw new RuntimeException("Error while creating lucene index: ", ioX);
		}
    }
    
    private void indexDocument(IndexWriter iw, NewsStory newsStory) throws IOException {
        
    	org.apache.lucene.document.Document doc = 
    	    new org.apache.lucene.document.Document();
        
        doc.add(new Field("content", 
        		          newsStory.getContent().getText(), 
        		          Field.Store.NO, 
        		          Field.Index.TOKENIZED,
        		          Field.TermVector.YES));
        
        doc.add(new Field("url", 
        		          newsStory.getUrl(), 
        		          Field.Store.YES, 
        		          Field.Index.NO));
        
        doc.add(new Field("docid", 
        		          newsStory.getId(), 
        		          Field.Store.YES, 
        		          Field.Index.NO));
        
        doc.add(new Field("title", 
        		          newsStory.getTitle(), 
        		          Field.Store.YES, 
        		          Field.Index.NO));

        
        iw.addDocument(doc);
    }

    public void run() {
        IndexWriter indexWriter = null;
        indexedDocCount = 0;
        try {
            
            indexWriter = new IndexWriter(indexDir, new StandardAnalyzer(), false);
            
            for(NewsStory newsStory : ds.getStories()) {
                indexDocument(indexWriter, newsStory);
                indexedDocCount++;
            }
        }
        catch(IOException ioX) {
            throw new RuntimeException("Error while creating lucene index: ", ioX);
        }
        finally {
            if( indexWriter != null ) {
                try {
                    indexWriter.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public long getIndexedDocCount() {
        return indexedDocCount;
    }

    public void setIndexedDocCount(long indexedDocCount) {
        this.indexedDocCount = indexedDocCount;
    }
    
    public String getIndexDir() {
        if( indexDir == null ) {
            return null;
        }
        
        try {
            return indexDir.getCanonicalPath();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
