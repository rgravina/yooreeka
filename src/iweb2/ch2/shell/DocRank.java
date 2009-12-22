/**
 * 
 */
package iweb2.ch2.shell;

import iweb2.ch2.ranking.DocRankMatrixBuilder;
import iweb2.ch2.ranking.PageRankMatrixH;
import iweb2.ch2.ranking.Rank;

/**
 * @author babis
 *
 */
public class DocRank extends Rank {

    DocRankMatrixBuilder docRankBuilder;
        
    public DocRank(String luceneIndexDir, int termsToKeep) {
        docRankBuilder = new DocRankMatrixBuilder(luceneIndexDir);
        docRankBuilder.setTermsToKeep(termsToKeep);
        docRankBuilder.run();
    }
    
    @Override
	public PageRankMatrixH getH() {
        return docRankBuilder.getH();
    }
}
