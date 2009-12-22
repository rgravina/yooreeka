package iweb2.ch7.crawling;

import iweb2.ch2.webcrawler.BasicWebCrawler;
import iweb2.ch2.webcrawler.CrawlData;
import iweb2.ch2.webcrawler.URLFilter;
import iweb2.ch2.webcrawler.URLNormalizer;
import iweb2.util.config.IWeb2Config;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A basic news crawler.
 * 
 * Remember to use <tt>setOffline(false)</tt>, if you want to use the local files 
 * 
 * @author babis
 */
public class NewsCrawler {

	public static final int DEFAULT_MAX_DEPTH = 1;
	public static final int DEFAULT_MAX_DOCS = 1000;
	
	private CrawlData crawlData;
	
	private String crawlDataDir;

    private int maxDepth = DEFAULT_MAX_DEPTH;
    
    private int maxDocs = DEFAULT_MAX_DOCS; 
        
    private List<String> seedUrls;

    /**
     * This variable determines whether we will crawl the Internet or local files 
     * Remember to use <tt>setOffline(false)</tt>, if you want to use the local files 
     */
    private boolean isOffline = false;
    
    public NewsCrawler(String rootDir, int maxDepth, int maxDocs) {
    	
    	this.crawlDataDir = buildUniqueDirectoryName(rootDir);
    	this.crawlData = new CrawlData(crawlDataDir);
    	
    	this.maxDepth = maxDepth;
    	
    	this.maxDocs = maxDocs;
    	
    	seedUrls = new ArrayList<String>();
    }
    
    private String buildUniqueDirectoryName(String rootDir) {
        return rootDir + System.getProperty("file.separator")+
            "crawl-" + System.currentTimeMillis();
    }
    
    public void run() {

        BasicWebCrawler webCrawler = new BasicWebCrawler(crawlData);
        
        webCrawler.addSeedUrls( getSeedUrls() );

        URLFilter urlFilter = new URLFilter();
        
        if (isOffline()) {
	        urlFilter.setAllowFileUrls(true);
	        urlFilter.setAllowHttpUrls(false);
        } else {
	        urlFilter.setAllowFileUrls(false);
	        urlFilter.setAllowHttpUrls(true);        	
        }
        webCrawler.setURLFilter(urlFilter);
        
    	long t0 = System.currentTimeMillis();

        /* run crawl - crawler will fetch and parse the documents */
        webCrawler.fetchAndProcess(maxDepth, maxDocs);

        System.out.println("Timer (s): [Crawler processed data] --> " + 
                (System.currentTimeMillis()-t0)*0.001);
    }
    
    public List<String> getSeedUrls() { 
    	return seedUrls;
    }

    public void addSeedUrl(String val) {
    	URLNormalizer urlNormalizer = new URLNormalizer();
    	seedUrls.add(urlNormalizer.normalizeUrl(val));
    }

    /*
     * Directory that contains "previously unseen" documents.
     */
    public static final String TEST_FILES_DIR_CH7 = IWeb2Config.getHome()+"/data/ch07/test";

    public void setAllSeedUrls() {

        seedUrls.clear();
        
        List<String> fileUrls = loadFileUrls(TEST_FILES_DIR_CH7);
        
        for(String url : fileUrls) {
            addSeedUrl(url);
        }
    }
    
    private List<String> loadFileUrls(String dir) {

        List<String> fileUrls = new ArrayList<String>();
        
        File dirFile = new File(dir);
        
        File[] docs = dirFile.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".html");
            }
        });

        try {        
            for(File f : docs) {
                URL url = f.toURI().toURL();
                fileUrls.add(url.toExternalForm());
            }
        }
        catch(IOException e) {
            throw new RuntimeException("Error while converting filename into URL: ", e);
        }
        
        return fileUrls;
    }
    
	/**
	 * @return the rootDir
	 */
	public String getCrawlDataDir() {
		return crawlDataDir;
	}

	/**
	 * @return the crawlData
	 */
	public CrawlData getCrawlData() {
		return crawlData;
	}

	/**
	 * @return the isOffline
	 */
	public boolean isOffline() {
		return isOffline;
	}

	/**
	 * @param isOffline the isOffline to set
	 */
	public void setOffline(boolean isOffline) {
		this.isOffline = isOffline;
	}
    
}
