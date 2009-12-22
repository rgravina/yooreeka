package iweb2.ch2.webcrawler;

import iweb2.ch2.webcrawler.db.FetchedDocsDB;
import iweb2.ch2.webcrawler.db.KnownUrlDB;
import iweb2.ch2.webcrawler.db.ProcessedDocsDB;
import iweb2.ch2.webcrawler.model.FetchedDocument;
import iweb2.ch2.webcrawler.model.KnownUrlEntry;
import iweb2.ch2.webcrawler.model.Outlink;
import iweb2.ch2.webcrawler.model.ProcessedDocument;
import iweb2.ch2.webcrawler.parser.common.DocumentParser;
import iweb2.ch2.webcrawler.parser.common.DocumentParserFactory;
import iweb2.ch2.webcrawler.transport.common.Transport;
import iweb2.ch2.webcrawler.transport.file.FileTransport;
import iweb2.ch2.webcrawler.transport.http.HTTPTransport;
import iweb2.ch2.webcrawler.utils.DocumentIdUtils;
import iweb2.ch2.webcrawler.utils.UrlGroup;
import iweb2.ch2.webcrawler.utils.UrlUtils;

import java.util.List;

public class BasicWebCrawler {
    
    private CrawlData crawlData;

    private URLFilter urlFilter;
    
    private static final int DEFAULT_MAX_BATCH_SIZE = 50;
    
    private long DEFAULT_PAUSE_IN_MILLIS = 500;
    private long pauseBetweenFetchesInMillis = DEFAULT_PAUSE_IN_MILLIS;
    
    /*
     * Number of URLs to fetch and parse at a time.
     */
    private int maxBatchSize = DEFAULT_MAX_BATCH_SIZE;
    
    /*
     * Number of fetched and parsed URLs so far.
     */
    private int processedUrlCount = 0;
    
    
    public BasicWebCrawler(CrawlData crawlData) {
        this.crawlData = crawlData;
    }
    
    public void addSeedUrls(List<String> seedUrls) {
        int seedUrlDepth = 0;
        KnownUrlDB knownUrlsDB = crawlData.getKnownUrlsDB(); 
        for(String url : seedUrls) {
            knownUrlsDB.addNewUrl(url, seedUrlDepth);
        }
    }

    public void fetchAndProcess(int maxDepth, int maxDocs) {
        
        boolean maxUrlsLimitReached = false;
        int documentGroup = 1;
        
        crawlData.init();
        
        if( maxBatchSize <= 0) {
            throw new RuntimeException("Invalid value for maxBatchSize = " + maxBatchSize);
        }

        for(int depth = 0; depth < maxDepth; depth++) {

            int urlsProcessedAtThisDepth = 0;
            
            boolean noMoreUrlsAtThisDepth = false;
            
            while( maxUrlsLimitReached == false && noMoreUrlsAtThisDepth == false) {

                System.out.println("Starting url group: " + documentGroup + 
                        ", current depth: " + depth +
                        ", total known urls: " + 
                            crawlData.getKnownUrlsDB().getTotalUrlCount() +
                        ", maxDepth: " + maxDepth + 
                        ", maxDocs: " + maxDocs +
                        ", maxDocs per group: " + maxBatchSize +
                        ", pause between docs: " + pauseBetweenFetchesInMillis + "(ms)");
                
                List<String> urlsToProcess = 
                    selectNextBatchOfUrlsToCrawl(maxBatchSize, depth);
                
                
                /* for batch of urls create a separate document group */
                String currentGroupId = String.valueOf(documentGroup);
                fetchPages(urlsToProcess, crawlData.getFetchedDocsDB(), currentGroupId);
                
                // process downloaded data
                processPages(currentGroupId, 
                        crawlData.getProcessedDocsDB(), 
                        crawlData.getFetchedDocsDB());
                
                // get processed doc, get links, add links to all-known-urls.dat
                processLinks(currentGroupId, depth + 1, crawlData.getProcessedDocsDB());
                
                int lastProcessedBatchSize = urlsToProcess.size();
                processedUrlCount += lastProcessedBatchSize;
                urlsProcessedAtThisDepth += lastProcessedBatchSize; 
                
                System.out.println("Finished url group: " + documentGroup + 
                        ", urls processed in this group: " + lastProcessedBatchSize + 
                        ", current depth: " + depth +
                        ", total urls processed: " + processedUrlCount);
                
                documentGroup += 1;

                if( processedUrlCount >= maxDocs ) {
                    maxUrlsLimitReached = true;
                }
                
                if( lastProcessedBatchSize == 0 ) {
                    noMoreUrlsAtThisDepth = true;
                }
            }

            if( urlsProcessedAtThisDepth == 0) {
                break;
            }
            
            if( maxUrlsLimitReached ) {
                break;
            }
            
        }
     }
    
    private Transport getTransport(String protocol) {
        if( "http".equalsIgnoreCase(protocol) ) {
            return new HTTPTransport();
        }
        else if ( "file".equalsIgnoreCase(protocol) ) {
            return new FileTransport();
        }
        else {
            throw new RuntimeException("Unsupported protocol: '" + protocol + "'.");
        }
    }
    
    private void fetchPages(List<String> urls, FetchedDocsDB fetchedDocsDB, String groupId) {
        DocumentIdUtils docIdUtils = new DocumentIdUtils();
        int docSequenceInGroup = 1;
        List<UrlGroup> urlGroups = UrlUtils.groupByProtocolAndHost(urls);
        for( UrlGroup urlGroup : urlGroups ) {
            Transport t = getTransport(urlGroup.getProtocol());
            try {
                t.init();
                for(String url : urlGroup.getUrls() ) {
                    try {
                        FetchedDocument doc = t.fetch(url);
                        String documentId = docIdUtils.getDocumentId(groupId, docSequenceInGroup);
                        doc.setDocumentId(documentId);
                        fetchedDocsDB.saveDocument(doc);
                        if( t.pauseRequired() ) {
                            pause();
                        }
                    }
                    catch(Exception e) {
                    	System.out.println("Failed to fetch document from url: '" + url + "'.\n"+
                    			e.getMessage());
                        crawlData.getKnownUrlsDB().updateUrlStatus(
                                url, KnownUrlEntry.STATUS_PROCESSED_ERROR);
                    }
                    docSequenceInGroup++;
                }
            }
            finally {
                t.clear();                
            }
        }
    }

    public long getPauseBetweenFetchesInMillis() {
        return pauseBetweenFetchesInMillis;
    }

    public void setPauseBetweenFetchesInMillis(long pauseBetweenFetchesInMillis) {
        this.pauseBetweenFetchesInMillis = pauseBetweenFetchesInMillis;
    }

    public void pause() {
        try {
            Thread.sleep(pauseBetweenFetchesInMillis);
        }
        catch(InterruptedException e) {
            // do nothing
        }
    }

    private void processPages(String groupId, 
            ProcessedDocsDB parsedDocsService, 
            FetchedDocsDB fetchedDocsDB) {
        
    	List<String> docIds = fetchedDocsDB.getDocumentIds(groupId);

        for(String id : docIds) {
            FetchedDocument doc = null;
            try {
                doc = fetchedDocsDB.getDocument(id);
                String url = doc.getDocumentURL();
                
                DocumentParser docParser = DocumentParserFactory
                    .getInstance().getDocumentParser(doc.getContentType());
                ProcessedDocument parsedDoc = docParser.parse(doc);
                parsedDocsService.saveDocument(parsedDoc);
                crawlData.getKnownUrlsDB().updateUrlStatus(
                        url, KnownUrlEntry.STATUS_PROCESSED_SUCCESS);                    
            }
            catch(Exception e) {
                
            	if( doc != null  ) {
                
                	System.out.println("ERROR:\n");
                	System.out.println("Unexpected exception while processing: '" +	id + "', ");
                	System.out.println("   URL='" + doc.getDocumentURL() + "'\n");
                	System.out.println("Exception message: "+e.getMessage());
                
                } else {
                	System.out.println("ERROR:\n");
                	System.out.println("Unexpected exception while processing: '" +	id + "', ");
                	System.out.println("Exception message: "+e.getMessage());
                }
            }
        }
    }

    private void processLinks(String groupId, int currentDepth, ProcessedDocsDB parsedDocs) {
        URLNormalizer urlNormalizer = new URLNormalizer();
        if( urlFilter == null ) {
            urlFilter = new URLFilter();
            urlFilter.setAllowFileUrls(true);
            urlFilter.setAllowHttpUrls(false);
            System.out.println("Using default URLFilter configuration that only accepts 'file://' urls");
        }
        
        List<String> docIds = parsedDocs.getDocumentIds(groupId);
        for(String documentId : docIds) {
            ProcessedDocument doc = parsedDocs.loadDocument(documentId);
            // register url without any outlinks first
            crawlData.getPageLinkDB().addLink(doc.getDocumentURL());
            List<Outlink> outlinks = doc.getOutlinks();
            for(Outlink outlink : outlinks) {
                String url = outlink.getLinkUrl();
                String normalizedUrl = urlNormalizer.normalizeUrl(url);
                if( urlFilter.accept(normalizedUrl) ) {
                    crawlData.getKnownUrlsDB().addNewUrl(url, currentDepth); 
                    crawlData.getPageLinkDB().addLink(doc.getDocumentURL(), url);
                }
            }
        }
        crawlData.getKnownUrlsDB().save();
        crawlData.getPageLinkDB().save();
    }

    /**
     * @deprecated use method that uses depth 
     * 
     * @param maxDocs
     * @return
     */
    @Deprecated
	public List<String> selectURLsForNextCrawl(int maxDocs) {
        return crawlData.getKnownUrlsDB().findUnprocessedUrls(maxDocs);
    }
    
    private List<String> selectNextBatchOfUrlsToCrawl(int maxBatchSize, int depth) {
        return crawlData.getKnownUrlsDB().findUnprocessedUrls(maxBatchSize, depth);
    }
    
    
    public URLFilter getURLFilter() {
        return urlFilter;
    }
    
    public void setURLFilter(URLFilter urlFilter) {
        this.urlFilter = urlFilter;
    }
    
}
