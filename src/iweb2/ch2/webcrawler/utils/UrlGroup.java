package iweb2.ch2.webcrawler.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Group of URLs for specific host and protocol.
 */
public class UrlGroup {
    private String protocol;
    private String host;
    private List<String> urls;
    
    public UrlGroup(String protocol, String host) {
        this.protocol = protocol;
        this.host = host;
        this.urls = new ArrayList<String>();
    }
    
    public void addUrl(String url) {
        urls.add(url);
    }
    
    public List<String> getUrls() {
        return urls;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public String getHost() {
        return host;
    }
    
    @Override
	public String toString() {
        return "[protocol: " + protocol + ", host: " + host + ", url count: " + urls.size() + "]";
    }
}
