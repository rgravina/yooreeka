package iweb2.ch2.webcrawler;


/**
 * Represents module that performs processing based on crawling results.
 * <p>Some module examples are:
 * <ul>
 * <li>Build Lucene index</li>
 * <li>Build matrix H for html pages</li>
 * <li>Build matrix H for documents</li>
 * </ul>
 * </p>
 */
public interface CrawlDataProcessor {
    public void run();
}
