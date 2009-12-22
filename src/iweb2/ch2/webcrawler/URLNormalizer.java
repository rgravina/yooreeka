package iweb2.ch2.webcrawler;

import java.net.URL;

/**
 * Performs url normalization. 
 */
public class URLNormalizer {
    public URLNormalizer() {
        // empty
    }
    
    /**
     * Implementation that does nothing.
     * 
     * <p>
     * Other features that can be added are: 
     * <ul>
     *   <li>convert IP address into DNS name</li>
     *   <li>lower-case DNS name</li>
     *   <li>extract session id from the URL</li>
     *   <li>process escape sequences</li>
     *   <li>remove default port</li>
     *   <li>remove fragment portion from the url</li>
     *   <li>sort variables</li>
     *   <li>...and a lot more</li>
     * </ul>
     * </p>
     * 
     */
    public String normalizeUrl(String url) {
        String normalizedUrl = url;
        if( url.startsWith("file://") ) {
            normalizedUrl = normalizeFileUrl(url);
        }
        return normalizedUrl;
    }
    
    private String normalizeFileUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            return url.toExternalForm();
        } 
        catch (Exception e) {
            throw new RuntimeException("URL Normalization error: ", e);
        }
    }
}
