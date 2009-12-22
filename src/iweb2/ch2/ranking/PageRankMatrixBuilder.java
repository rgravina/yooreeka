package iweb2.ch2.ranking;

import iweb2.ch2.webcrawler.CrawlData;
import iweb2.ch2.webcrawler.CrawlDataProcessor;
import iweb2.ch2.webcrawler.db.KnownUrlDB;
import iweb2.ch2.webcrawler.db.PageLinkDB;
import iweb2.ch2.webcrawler.model.KnownUrlEntry;

import java.util.List;
import java.util.Set;

public class PageRankMatrixBuilder implements CrawlDataProcessor {
    
//    private static final Logger logger = Logger.getLogger(PageRankMatrixBuilder.class);

    private PageRankMatrixH matrixH;
    private CrawlData crawlData;
    public PageRankMatrixBuilder(CrawlData crawlData) {
        this.crawlData = crawlData;
    }
    
    public PageRankMatrixH getH() {
        return matrixH;
    }

    public void run() {
        this.matrixH = buildMatrixH(crawlData.getKnownUrlsDB(), crawlData.getPageLinkDB());
    }
    
    private PageRankMatrixH buildMatrixH(KnownUrlDB knownUrlDB, PageLinkDB pageLinkDB) {

//    	logger.info("starting calculation of matrix H...");
        
    	// only consider URLs that with fetched and parsed content
        List<String> allProcessedUrls = knownUrlDB.findProcessedUrls(KnownUrlEntry.STATUS_PROCESSED_SUCCESS);
        
        PageRankMatrixH pageMatrix = new PageRankMatrixH( allProcessedUrls.size() );
        
        for(String url : allProcessedUrls) {
        
            // register url here in case it has no outlinks.
            pageMatrix.addLink(url);
            
        	Set<String> pageOutlinks = pageLinkDB.getOutlinks(url);
            
        	for(String outlink : pageOutlinks) {
            
        		// only consider URLs with parsed content
                if( knownUrlDB.isSuccessfullyProcessed(outlink) ) {
                    pageMatrix.addLink(url, outlink);
                }
            }
        }
        
        pageMatrix.calculate();
        
//        logger.info("matrix H is ready. Matrix size: " + pageMatrix.getMatrix().length);
        
        return pageMatrix;
    }
}
