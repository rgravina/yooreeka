package iweb2.ch2.shell;

import iweb2.ch2.webcrawler.BasicWebCrawler;
import iweb2.ch2.webcrawler.CrawlData;
import iweb2.ch2.webcrawler.URLFilter;
import iweb2.ch2.webcrawler.URLNormalizer;
import iweb2.util.config.IWeb2Config;

import java.util.ArrayList;
import java.util.List;

public class FetchAndProcessCrawler {

	public static final int DEFAULT_MAX_DEPTH = 3;
	public static final int DEFAULT_MAX_DOCS = 1000;
	
	//INSTANCE VARIABLES
	// A reference to the crawled data
	CrawlData crawlData;
	
	// The location where we will store the fetched data
	String rootDir;

	// total number of iterations
    int maxDepth = DEFAULT_MAX_DEPTH;
    
    // max number of pages that will be fetched within every crawl/iteration.
    int maxDocs = DEFAULT_MAX_DOCS; 
        
    List<String> seedUrls;
    
    URLFilter urlFilter;

    public FetchAndProcessCrawler(String dir, int maxDepth, int maxDocs) {
    	
    	rootDir = dir;
    	
    	if ( rootDir == null || rootDir.trim().length() == 0) {
    		
    		String prefix = System.getProperty("iweb2.home");
    		if (prefix == null) {
    			prefix = "..";
    		}

    		rootDir = System.getProperty("iweb2.home")+System.getProperty("file.separator")+"data";
    	}
    	
    	rootDir = rootDir+System.getProperty("file.separator")+"crawl-" + System.currentTimeMillis();
    	
    	this.maxDepth = maxDepth;
    	
    	this.maxDocs = maxDocs;
    	
    	this.seedUrls = new ArrayList<String>();
    	
    	/* default url filter configuration */
    	this.urlFilter = new URLFilter();
    	urlFilter.setAllowFileUrls(true);
    	urlFilter.setAllowHttpUrls(true);
    }
    
    
    
    public void run() {
    	        
    	crawlData = new CrawlData(rootDir);

        BasicWebCrawler webCrawler = new BasicWebCrawler(crawlData);
        webCrawler.addSeedUrls(getSeedUrls());

        webCrawler.setURLFilter(urlFilter);
        
    	long t0 = System.currentTimeMillis();

        /* run crawl */
        webCrawler.fetchAndProcess(maxDepth, maxDocs);

        System.out.println("Timer (s): [Crawler processed data] --> " + 
                (System.currentTimeMillis()-t0)*0.001);
    	
    }
    
    public List<String> getSeedUrls() { 

    	return seedUrls;
    }

    public void addUrl(String val) {
    	URLNormalizer urlNormalizer = new URLNormalizer();
    	seedUrls.add(urlNormalizer.normalizeUrl(val));
    }

    public void setAllUrls() {
    	
    	setDefaultUrls();
    	
    	String iWeb2Home = IWeb2Config.getHome();
    	
    	// Include the spam pages ... all of them!
    	addUrl("file:///"+iWeb2Home+"/data/ch02/spam-01.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/spam-biz-01.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/spam-biz-02.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/spam-biz-03.html");    	
    }
    
    public void setDefaultUrls() {
    	
    	String iWeb2Home = IWeb2Config.getHome();

    	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-01.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-02.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-03.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-04.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-05.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-06.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-07.html");
    	
    	addUrl("file:///"+iWeb2Home+"/data/ch02/sport-01.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/sport-02.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/sport-03.html");

    	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-01.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-02.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-03.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-04.html");

    	addUrl("file:///"+iWeb2Home+"/data/ch02/world-01.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/world-02.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/world-03.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/world-04.html");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/world-05.html");
    	
    	setFilesOnlyUrlFilter();    	
	}
    
    public void setUrlFilter(URLFilter urlFilter) {
        this.urlFilter = urlFilter;
    }
    
    private void setFilesOnlyUrlFilter() {
        /* configure url filter to accept only file:// urls */
        URLFilter urlFilter = new URLFilter();
        urlFilter.setAllowFileUrls(true);
        urlFilter.setAllowHttpUrls(false);
        setUrlFilter(urlFilter);
    }
    
    public void setUrls(String val) {

    	String iWeb2Home = IWeb2Config.getHome();

        setFilesOnlyUrlFilter();
        
    	this.seedUrls.clear();
    	
    	if (val.equalsIgnoreCase("biz")) {
    		
        	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-01.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-02.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-03.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-04.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-05.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-06.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/biz-07.html");
        	
    	} else if (val.equalsIgnoreCase("sport")) {
    		
        	addUrl("file:///"+iWeb2Home+"/data/ch02/sport-01.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/sport-02.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/sport-03.html");

    	} else if (val.equalsIgnoreCase("usa")) {
    		
        	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-01.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-02.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-03.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/usa-04.html");

    	} else if (val.equalsIgnoreCase("world")) {
    		
        	addUrl("file:///"+iWeb2Home+"/data/ch02/world-01.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/world-02.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/world-03.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/world-04.html");
        	addUrl("file:///"+iWeb2Home+"/data/ch02/world-05.html");
    	} else if (val.equalsIgnoreCase("biz-docs")) {
            
            addUrl("file:///"+iWeb2Home+"/data/ch02/biz-01.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/biz-02.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/biz-03.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/biz-04.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/biz-05.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/biz-06.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/biz-07.doc");
            
        } else if (val.equalsIgnoreCase("sport-docs")) {
            
            addUrl("file:///"+iWeb2Home+"/data/ch02/sport-01.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/sport-02.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/sport-03.doc");

        } else if (val.equalsIgnoreCase("usa-docs")) {
            
            addUrl("file:///"+iWeb2Home+"/data/ch02/usa-01.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/usa-02.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/usa-03.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/usa-04.doc");

        } else if (val.equalsIgnoreCase("world-docs")) {
            
            addUrl("file:///"+iWeb2Home+"/data/ch02/world-01.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/world-02.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/world-03.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/world-04.doc");
            addUrl("file:///"+iWeb2Home+"/data/ch02/world-05.doc");
        }    	
    	else {
    	    throw new IllegalArgumentException("Unknown value: '" + val + "'");
    	}
        	
    }
    
    public void addDocSpam() {

    	String iWeb2Home = IWeb2Config.getHome();

    	addUrl("file:///"+iWeb2Home+"/data/ch02/spam-biz-01.doc");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/spam-biz-02.doc");
    	addUrl("file:///"+iWeb2Home+"/data/ch02/spam-biz-03.doc");    	
    }
    
	/**
	 * @return the rootDir
	 */
	public String getRootDir() {
		return rootDir;
	}

	/**
	 * @param rootDir the rootDir to set
	 */
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	/**
	 * @return the maxNumberOfCrawls
	 */
	public int getMaxNumberOfCrawls() {
		return maxDepth;
	}

	/**
	 * @param maxNumberOfCrawls the maxNumberOfCrawls to set
	 */
	public void setMaxNumberOfCrawls(int maxNumberOfCrawls) {
		this.maxDepth = maxNumberOfCrawls;
	}

	/**
	 * @return the maxNumberOfDocsPerCrawl
	 */
	public int getMaxNumberOfDocsPerCrawl() {
		return maxDocs;
	}

	/**
	 * @param maxNumberOfDocsPerCrawl the maxNumberOfDocsPerCrawl to set
	 */
	public void setMaxNumberOfDocsPerCrawl(int maxNumberOfDocsPerCrawl) {
		this.maxDocs = maxNumberOfDocsPerCrawl;
	}

	/**
	 * @return the crawlData
	 */
	public CrawlData getCrawlData() {
		return crawlData;
	}
    
}
