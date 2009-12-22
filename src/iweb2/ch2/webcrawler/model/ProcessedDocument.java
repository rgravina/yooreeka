package iweb2.ch2.webcrawler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Processed document with attributes that we are interested in.
 */
public class ProcessedDocument {
    
    public static final String DOCUMENT_TYPE_HTML = "html";
    public static final String DOCUMENT_TYPE_MSWORD = "msword";
    
    /*
     * Unique document id.
     */
    private String documentId;
    
    /*
     * All document outlinks (links that document has to other documents).
     */
    private List<Outlink> outlinks = new ArrayList<Outlink>();
    
    /*
     * URL that was used to retrieve the document.
     */
    private String documentURL;
    
    /*
     * Document title. 
     */
    private String title;
    
    /*
     * Processed document content. In case of HTML doc it can be HTML
     * with only relevant tags (<P>, <B>,..) preserved.
     */
    private String content;
    
    /*
     * Text extracted from the document with all formatting removed.
     */
    private String text;
    
    /*
     * Document type.
     */
    private String documentType;
    
    public ProcessedDocument() {
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public String getDocumentURL() {
        return documentURL;
    }
   
    public List<Outlink> getOutlinks() {
        return outlinks;
    }

    public void setOutlinks(List<Outlink> outlinks) {
        this.outlinks = outlinks;
    }

    public void setDocumentURL(String documentURL) {
        this.documentURL = documentURL;
    }

    public void setDocumentTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return this.content;
    }

    public String getDocumentTitle() {
        return this.title;
    }

    public void setDocumentId(String docId) {
        this.documentId = docId;
    }
    
    public String getDocumentId() {
        return documentId;
    }
    
    public String getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(String docType) {
        this.documentType = docType;
    }
    
    @Override
	public String toString() {
        return "[docId: " + documentId +
                ", type: " + documentType +
                ", url: " + documentURL + 
                "]"; 
    }
}
