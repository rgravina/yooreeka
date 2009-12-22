package iweb2.ch2.webcrawler.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlUtils {

    public static List<UrlGroup> groupByProtocolAndHost(List<String> urls) {
        Map<String, UrlGroup> allGroups = new HashMap<String, UrlGroup>();
        for(String url : urls ) {
            URL u = null;
            try {
                u = new URL(url);
                String protocol = u.getProtocol();
                String host = u.getHost();
                String key = protocol + "|" + host;
                UrlGroup urlGroup = allGroups.get(key);
                if( urlGroup == null ) {
                    urlGroup = new UrlGroup(protocol, host);
                    allGroups.put(key, urlGroup);
                }
                urlGroup.addUrl(url);
            }
            catch(MalformedURLException e) {
                throw new RuntimeException("Invalid url format url: '" + url + "': ", e);
            }
        }
        return new ArrayList<UrlGroup>(allGroups.values());
    }
    
}
