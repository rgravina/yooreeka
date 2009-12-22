package iweb2.ch2.webcrawler.transport.file;

import iweb2.ch2.webcrawler.model.FetchedDocument;
import iweb2.ch2.webcrawler.transport.common.Transport;
import iweb2.ch2.webcrawler.transport.common.TransportException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class FileTransport implements Transport {

    public FileTransport() {
    }
    
    public FetchedDocument fetch(String documentUrl) 
        throws TransportException {

        FetchedDocument doc = null;
        try {
            doc = createDocument(documentUrl);
        }
        catch(Exception eX) {
        	System.out.println("ERROR:\n"+eX.getMessage());
            throw new FileTransportException("Failed to fetch url: '" + documentUrl + "': ", eX); 
        }
        finally {
        }
        
        return doc;
    }
    
    private byte[] loadData(File f, int maxLength) throws IOException {
        if( f.length() > maxLength ) {
            throw new IOException("The document is too long (doc: " + 
                    f.getCanonicalPath() + ", size: " + 
                    f.length() + ", max size: " + maxLength);
        }
        
        InputStream in = new BufferedInputStream(new FileInputStream(f));
        byte[] data = new byte[(int)f.length()];
        int offset = 0;
        int i = 0;
        while( (offset < data.length) &&
            (i = in.read(data, offset, data.length - offset)) >= 0) {
            offset += i;
        }
        in.close();
        return data;
    }

    private FetchedDocument createDocument(String targetURL) 
        throws IOException, FileTransportException {
        FetchedDocument doc = new FetchedDocument();        

        /*
         * Maximum document length.
         */
        int MAX_DOCUMENT_LENGTH = 512 * 1024; // 512K
        
        URL url = new URL(targetURL);
        File f = null;
        try {
         f = new File(url.toURI());
        }
        catch(URISyntaxException e) {
            throw new FileTransportException("Error while converting url to file path: ", e);
        }
        
        /* IOException will be thrown for documents that exceed max length */
        byte[] data = loadData(f, MAX_DOCUMENT_LENGTH);

        String DEFAULT_CONTENT_TYPE = "text/html";
        String contentType = DEFAULT_CONTENT_TYPE;
        if( f.getName().endsWith(".doc") ) {
            contentType = "application/msword";
        }

        String DEFAULT_CONTENT_CHARSET = "UTF-8";
        String contentCharset = DEFAULT_CONTENT_CHARSET;
        
        doc.setContentType(contentType);
        doc.setDocumentURL(targetURL);
        doc.setContentCharset(contentCharset);
        doc.setDocumentContent(data);
        doc.setDocumentMetadata(new HashMap<String, String>());
        return doc;
    }


    public void clear() {
        // DO NOTHING
    }

    public void init() {
        // DO NOTHING
    }

    public boolean pauseRequired() {
        return false;
    }
}
