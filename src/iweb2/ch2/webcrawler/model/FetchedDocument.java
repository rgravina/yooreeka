package iweb2.ch2.webcrawler.model;

import java.util.Map;

/**
 * Collection of raw (unprocessed) data about crawled/fetched document.
 */ 
public class FetchedDocument {
    
    /*
     * Document id that was assigned by the FetcherModule.
     */
    private String documentId;
    
    /*
     * Document URL. URL that was used to fetch the document.  
     */
    private String url;
    
    /*
     * MIME content type that was derived from transport protocol (HTTP headers), 
     * document content or document URL.  
     */
    private String contentType;

    /*
     * Character encoding that was derived from transport protocol (HTTP headers),
     * document content.
     */
    private String contentCharset;

    /*
     * Raw document content.
     */
    private byte[] documentContent;
    
    
    /*
     * Various optional meta data about the document that was extracted from the 
     * protocol. 
     */
    private Map<String, String> documentMetadata;
    
    public FetchedDocument() {
    }
    
    public String getDocumentId() {
        return documentId;
    }
    
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public void setDocumentURL(String url) {
        this.url = url;
    }
    
    
    public String getContentCharset() {
        return contentCharset;
    }

    public void setContentCharset(String contentCharset) {
        this.contentCharset = contentCharset;
    }

    public String getDocumentURL() {
        return url;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setDocumentContent(byte[] data) {
        this.documentContent = data;
    }
    public byte[] getDocumentContent() {
        return documentContent;
    }

    public long getContentLength() {
        return documentContent.length;
    }
    
    public Map<String, String> getDocumentMetadata() {
        return documentMetadata;
    }

    public void setDocumentMetadata(Map<String, String> metadata) {
        this.documentMetadata = metadata;
    }
}
