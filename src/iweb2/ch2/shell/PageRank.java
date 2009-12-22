package iweb2.ch2.shell;

import iweb2.ch2.ranking.PageRankMatrixBuilder;
import iweb2.ch2.ranking.PageRankMatrixH;
import iweb2.ch2.ranking.Rank;
import iweb2.ch2.webcrawler.CrawlData;

public class PageRank extends Rank {
    
	PageRankMatrixBuilder pageRankBuilder;
	
	public PageRank(CrawlData crawlData) {
        pageRankBuilder = new PageRankMatrixBuilder(crawlData);
        pageRankBuilder.run();     
	}
	
	@Override
	public PageRankMatrixH getH() {
		return pageRankBuilder.getH();
	}
	
}
