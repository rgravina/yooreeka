package iweb2.ch2.webcrawler;


/**
 * Performs url filtering before url is registered in 'known urls' database.
 */
public class URLFilter {

    private boolean allowFileUrls = true;
    private boolean allowHttpUrls = true;
    
    public URLFilter() {
        // empty
    }
    
    /**
     * Basic implementation of url filter. Only allows urls that start 
     * with 'http:' and 'file:'
     * 
     * <p>
     * Other features that can be added are: 
     * <ul>
     *   <li>extract host from the url and check against robots.txt</li>
     *   <li>check against the list of excluded urls</li>
     *   <li>user defined criteria</li>
     * </ul>
     * </p>
     */
    public boolean accept(String url) {
        boolean acceptUrl = false;
        if( allowFileUrls && url.startsWith("file:") ) { 
            acceptUrl = true;
        }
        else if( allowHttpUrls && url.startsWith("http:") ) {
            acceptUrl = true;
        }
        else {
            acceptUrl = false;
            System.out.println("DEBUG: Filtered url: '" + url + "'");
        }
        
        return acceptUrl;
    }
    
    public void setAllowFileUrls(boolean flag) {
        this.allowFileUrls = flag;
    }
    
    public void setAllowHttpUrls(boolean flag) {
        this.allowHttpUrls = flag;
    }
}
