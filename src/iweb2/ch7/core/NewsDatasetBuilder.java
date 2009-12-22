package iweb2.ch7.core;

import java.util.List;

import iweb2.ch2.webcrawler.CrawlData;
import iweb2.ch2.webcrawler.db.ProcessedDocsDB;
import iweb2.ch2.webcrawler.model.ProcessedDocument;
import iweb2.ch7.crawling.CrawlResultsNewsDataset;
import iweb2.ch7.crawling.FileListNewsDataset;
import iweb2.util.config.IWeb2Config;

public class NewsDatasetBuilder {

    public static int TOP_N_TERMS = 50;
    public static final String TRAINING_FILES_DIR_CH7 = IWeb2Config.getHome()+"/data/ch07/training";
    public static final String TEST_FILES_DIR_CH7 = IWeb2Config.getHome()+"/data/ch07/test";
    
    public static NewsDataset createNewsDatasetFromFileList(
            String datasetName, String dir) {

    	FileListNewsDataset ds = new FileListNewsDataset(datasetName,dir);
        ds.setTopTerms(TOP_N_TERMS);
        ds.loadTopics();
        ds.loadStories();
        
        return ds;
    }
    
    public static NewsDataset createNewsDatasetFromCrawledData(String datasetName, String crawlDataDir) {
    	
        CrawlData crawlData = new CrawlData(crawlDataDir);
        crawlData.init();
        
        ProcessedDocsDB processedDocsDB = crawlData.getProcessedDocsDB();

        CrawlResultsNewsDataset dataset = new CrawlResultsNewsDataset(datasetName, crawlDataDir);
        dataset.setTopTerms(TOP_N_TERMS);
        dataset.loadTopics();
        dataset.loadStories();
        /* Load all document groups into dataset */
        List<String> allGroups = processedDocsDB.getAllGroupIds();
        for(String groupId : allGroups) {
            List<ProcessedDocument> docs = 
                processedDocsDB.loadAllDocumentsInGroup(groupId);
            dataset.addDocuments(docs);
        }
        
        return dataset;
    }
}
