package iweb2.ch2.webcrawler.utils;

public class DocumentIdUtils {

    private static final String GROUP_PREFIX = "g";
    private static final String SEQUENCE_PREFIX = "d";
    private static final String ID_COMPONENTS_DELIMITER = "-";
    
    public String getDocumentId(String docGroupId, int docSequence) {
        return getDocumentId(docGroupId, String.valueOf(docSequence)); 
    }

    public String getDocumentId(String docGroupId, String docSequence) {
        return "g" + docGroupId + "-d" + docSequence; 
    }
    
    public String getDocumentGroupId(String documentId) {
        String[] idComponents = documentId.split(ID_COMPONENTS_DELIMITER);
        return idComponents[0].substring(GROUP_PREFIX.length());
    }
    
    public String getDocumentSequence(String documentId) {
        String[] idComponents = documentId.split(ID_COMPONENTS_DELIMITER);
        return idComponents[1].substring(SEQUENCE_PREFIX.length());
    }
}
