package iweb2.ch2.webcrawler.transport.http;

import iweb2.ch2.webcrawler.model.FetchedDocument;
import iweb2.ch2.webcrawler.transport.common.Transport;
import iweb2.ch2.webcrawler.transport.common.TransportException;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HTTPTransport implements Transport {

    HttpState initialState = null;
    HttpClient httpclient = null;
    
    public HTTPTransport() {
    }
    
    /* (non-Javadoc)
     * @see iweb2.ch2.webcrawler.transport.common.Transport#init()
     */
    public void init() {
    	
    	System.out.println("Initializing HTTPTransport ...");

        initialState = new HttpState();
        httpclient = new HttpClient();
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(30000);
        httpclient.setState(initialState);
        httpclient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        httpclient.getParams().setParameter(
                HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, Boolean.TRUE);
        // Set default number of connections per host to 1
        httpclient.getHttpConnectionManager().
            getParams().setMaxConnectionsPerHost(
                    HostConfiguration.ANY_HOST_CONFIGURATION, 1);
        // Set max for total number of connections
        httpclient.getHttpConnectionManager().getParams().setMaxTotalConnections(10);
    }
    
    /* (non-Javadoc)
     * @see iweb2.ch2.webcrawler.transport.common.Transport#clear()
     */
    public void clear() {
        httpclient = null;
        initialState = null;
    }
    
    /* (non-Javadoc)
     * @see iweb2.ch2.webcrawler.transport.common.Transport#fetch(java.lang.String)
     */
    public FetchedDocument fetch(String documentUrl) 
        throws TransportException {

        GetMethod httpget = null;
        FetchedDocument doc = null;
        try {
            httpget = new GetMethod(documentUrl);
            httpget.setFollowRedirects(true);
            int result = httpclient.executeMethod(httpget);
            if( result != 200) {
                String errorMessage = "HTTP Response code: " + result + 
                    ", status text: " + httpget.getStatusText() + 
                    " for url: '" + documentUrl + "'."; 
                throw new TransportException(errorMessage);
            }
            else {
                doc = createDocument(documentUrl, httpget);
            }
        }
        catch(IOException e) {
            throw new TransportException("Failed to fetch url: '" + documentUrl + "': ", e); 
        }
        finally {
            if( httpget != null ) {
                httpget.releaseConnection();
            }
        }
        
        return doc;
    }

    private FetchedDocument createDocument(String targetURL, GetMethod httpget) 
        throws IOException, HTTPTransportException {
        FetchedDocument doc = new FetchedDocument();        

        /*
         * Maximum document length that transport will attempt to download.
         */
        int MAX_DOCUMENT_LENGTH = 512 * 1024; // 512K
        
        /* IOException will be thrown for documents that exceed max length */
        byte[] data = httpget.getResponseBody(MAX_DOCUMENT_LENGTH);

        /*
         * Check if server sent content in compressed form and uncompress the content if
         * necessary.
         */
        Header contentEncodingHeader = httpget.getResponseHeader("Content-Encoding");
        if( contentEncodingHeader != null ) {
            data = HTTPUtils.decodeContent(contentEncodingHeader.getValue(), data);
        }
        
        
        /* 'Content-Type' HTTP header value */
        String contentTypeHeaderValue = null;
        Header header = httpget.getResponseHeader("Content-Type");
        if( header != null ) {
            contentTypeHeaderValue = header.getValue();
        }
        
        /*
         * Determine MIME type of the document.
         *  
         * It is easy if we have Content-Type http header. In cases 
         * when this header is missing or for protocols that don't pass metadata about the 
         * documents (ftp://, file://) we would have to resort to url and/or content analysis
         * to determine MIME type.
         */ 
        String DEFAULT_CONTENT_TYPE = "text/html";
        String contentType = HTTPUtils.getContentType(contentTypeHeaderValue, targetURL, data);
        if( contentType == null ) {
            contentType = DEFAULT_CONTENT_TYPE;
        }

        /*
         * Determine Character encoding used in the document. 
         * In some cases it may be specified in the http header, in html file  
         * itself or we have to perform content analysis to choose the encoding. 
         */
        String DEFAULT_CONTENT_CHARSET = "UTF-8";
        String contentCharset = HTTPUtils.getCharset(contentTypeHeaderValue, contentType, data);
        if( contentCharset == null ) {
            contentCharset = DEFAULT_CONTENT_CHARSET;
        }
        
        doc.setContentType(contentType);
        doc.setDocumentURL(targetURL); 
        doc.setContentCharset(contentCharset);
        doc.setDocumentContent(data);
        doc.setDocumentMetadata(new HashMap<String, String>());
        return doc;
    }

    public boolean pauseRequired() {
        return true;
    }
}
