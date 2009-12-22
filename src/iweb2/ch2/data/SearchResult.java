/**
 * 
 */
package iweb2.ch2.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author bmarmanis
 *
 */
public class SearchResult {

	private String docId;
    private String docType;	
    private String title;
	private String url;
	
	private double score;
	
    public SearchResult(String docId, String docType, String title, String url,double score) {
		
		this.docId = docId;
		this.docType = docType;
		this.title = title;
		this.url   = url;
		this.score = score;
	}

	/**
	 * @return the docId
	 */
	public String getDocId() {
		return docId;
	}

	/**
	 * @param docId the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return document title if available
	 */
    public String getTitle() {
        return title;
    }

    /**
     * @param title document title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    
	public String print() {
		StringBuilder strB = new StringBuilder();
//		strB.append("Document ID    : ").append(docId).append("\n");
		strB.append("Document Type: ").append(docType).append("\n");
		strB.append("Document Title : ").append(title).append("\n");
		strB.append("Document URL: ").append(url).append("  -->  ");
		strB.append("Relevance Score: ").append(score).append("\n");
		return strB.toString();
	}
	
    /**
     * Sorts list in descending order of score value.
     */
    public static void sortByScore(List<SearchResult> values) {
        Collections.sort(values, new Comparator<SearchResult>() {
            public int compare(SearchResult r1, SearchResult r2) { 
                int result = 0;
                // sort based on score value
                if( r1.getScore() < r2.getScore() ) {
                    result = 1; // sorting in descending order
                }
                else if( r1.getScore() > r2.getScore() ) {
                    result = -1;
                }
                else {
                    result = 0;
                }
                return result;
            }
        });
    }
    
    /**
     * Sorts array in descending order of score value.
     */
    public static void sortByScore(SearchResult[] values) {
        Arrays.sort(values, new Comparator<SearchResult>() {
            public int compare(SearchResult r1, SearchResult r2) { 
                int result = 0;
                // sort based on score value
                if( r1.getScore() < r2.getScore() ) {
                    result = 1; // sorting in descending order
                }
                else if( r1.getScore() > r2.getScore() ) {
                    result = -1;
                }
                else {
                    result = 0;
                }
                return result;
            }
        });
    }
}
