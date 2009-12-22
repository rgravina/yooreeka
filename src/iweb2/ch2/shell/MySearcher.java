package iweb2.ch2.shell;

import iweb2.ch2.clicks.UserClick;
import iweb2.ch2.clicks.UserQuery;
import iweb2.ch2.data.SearchResult;
import iweb2.ch2.ranking.Rank;
import iweb2.ch5.classification.bayes.NaiveBayes;
import iweb2.ch5.ontology.core.BaseConcept;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

public class MySearcher {

	/**
	 * An arbitrary small value
	 */
	public static final double EPSILON = 0.0001;

	private static final String PRETTY_LINE = 
		"_______________________________________________________________________";
	
	private String indexDir;

	private NaiveBayes learner = null;
	
	private boolean verbose = true;
	
    public MySearcher(String indexDir) {
		this.indexDir = indexDir;
	}
	
	public void setUserLearner(NaiveBayes nb) {
		learner = nb;
	}
	
	public SearchResult[] search(String query, int numberOfMatches) {
	    
		SearchResult[] docResults = new SearchResult[0];
		
		IndexSearcher is = null;
		
		try {

			is = new IndexSearcher(FSDirectory.getDirectory(indexDir));
			
		} catch (IOException ioX) {
			System.out.println("ERROR: "+ioX.getMessage());
		}

		QueryParser qp = new QueryParser("content", new StandardAnalyzer());

		Query q = null;
		try {

			q = qp.parse(query);

		} catch (ParseException pX) {
			System.out.println("ERROR: "+pX.getMessage());
		}

		Hits hits = null;
		try {
			hits = is.search(q);

			int n = Math.min(hits.length(), numberOfMatches);
			docResults = new SearchResult[n];
			
			for (int i = 0; i < n; i++) {

				docResults[i] = new SearchResult(hits.doc(i).get("docid"),
				                                 hits.doc(i).get("doctype"),
				                                 hits.doc(i).get("title"),
												 hits.doc(i).get("url"),
												 hits.score(i));
			}

			is.close();

		} catch (IOException ioX) {
			System.out.println("ERROR: "+ioX.getMessage());
		}

        String header = "Search results using Lucene index scores:";
        boolean showTitle = true;
        printResults(header, "Query: " + query, docResults, showTitle);
		
		return docResults;
	}
	
	/**
	 * A method that combines the score of an index based search 
	 * and the score of the PageRank algorithm to achieve better 
	 * relevance results.
	 */
	public SearchResult[] search(String query, int numberOfMatches, Rank pR) {

        SearchResult[] docResults = search(query, numberOfMatches);
	    
		String url;

		int n = pR.getH().getSize();

/** 
 * TODO: 2.3 -- The PageRank scaling factor m (Book Section 2.3) 
 * 
 *       When the number of pages in your graph are few, the PageRank values
 *       need some boosting. As the number of pages increases m approaches the
 *       value 1 quickly because 1/n goes to zero. 
 */ 
		double m = 1 - (double) 1/n;

		// actualNumberOfMatches <= numberOfMatches
		int actualNumberOfMatches = docResults.length;

		for (int i = 0; i < actualNumberOfMatches; i++) {

			url = docResults[i].getUrl();
			
			double hScore = docResults[i].getScore() * Math.pow(pR.getPageRank(url),m);
						
			// Update the score of the results
			docResults[i].setScore(hScore); 
		}

		// sort results by score
		SearchResult.sortByScore(docResults);
		
		String header = "Search results using combined Lucene scores and page rank scores:";
		boolean showTitle = false;
		printResults(header, "Query: " + query, docResults, showTitle);
		
		return docResults;
	}
	

	/**
	 * A method that combines the score of an index based search 
	 * and the score of the PageRank algorithm to achieve better 
	 * relevance results, while personalizing the result set based on
	 * past user clicks on the same or similar queries.
	 * 
	 * NOTE: You would typically refactor all these search methods 
	 *       in order to consider it production quality code. Here,
	 *       we repeat the code of the previous method, so that it 
	 *       is easier to read.
	 *       
	 * @param userID identifies the person who issues the query
	 * @param query is the whole query
	 * @param numberOfMatches defines the maximim number of desired matches 
	 * @param pR the PageRank vector
	 * @return the result set
	 */
	public SearchResult[] search(UserQuery uQuery, int numberOfMatches, Rank pR) {
		
		SearchResult[] docResults = search(uQuery.getQuery(), numberOfMatches);
		
		String url;
		
		int docN = docResults.length;
		
		if (docN > 0) {
			
			int loop = (docN<numberOfMatches) ? docN : numberOfMatches;
			
			for (int i = 0; i < loop; i++) {
	
				url = docResults[i].getUrl();
				
				UserClick uClick = new UserClick(uQuery,url);
			
/**
 * TODO: 2.6 -- Weighing the scores to meet your needs (Book Section 2.4.2)
 * 
 *       At this point, we have three scores of relevance. The relevance score that
 *       is based on the index search, the PageRank score, and the score that is based
 *       on the user's prior selections. There is no golden formula for everybody.
 *       Below we are selecting a formula that we think would make sense for most people.
 *       
 *       Feel free to change the formula, experiment with different weighting factors,
 *       to find out the choices that are most appropriate for your own site.
 * 
 */				
				double indexScore     = docResults[i].getScore();
				
				double pageRankScore  = pR.getPageRank(url);
				
				BaseConcept bC = new BaseConcept(url);
				
				double userClickScore = learner.getProbability(bC, uClick); 
				
				// Create the final score
				double hScore;
				
				if (userClickScore == 0) {
					
					hScore = indexScore * pageRankScore * EPSILON;
					
				} else {

					hScore = indexScore * pageRankScore * userClickScore;
				}
							
				// Update the score of the results
				docResults[i].setScore(hScore);
			}
		}		

        // Sort array of results
        SearchResult.sortByScore(docResults);
		
        String header = "Search results using combined Lucene scores, " + 
		                "page rank scores and user clicks:";
        String query = "Query: user=" + uQuery.getUid() + ", query text=" + uQuery.getQuery();
        boolean showTitle = false;
		printResults(header, query, docResults, showTitle);
		
		return docResults;
	}
	
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    private void printResults(String header, String query, SearchResult[] values, boolean showDocTitle) {

        if( verbose ) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            
            boolean printEntrySeparator = false;
            if( showDocTitle ) { // multiple lines per entry
                printEntrySeparator = true;
            }
            
            pw.print("\n");
            pw.println(header);
            if( query != null ) {
                pw.println(query);
            }
            pw.print("\n");
            for(int i = 0, n = values.length; i < n; i++) {
                if( showDocTitle ) {
                    pw.printf("Document Title: %s\n", values[i].getTitle());
                }
                pw.printf("Document URL: %-46s  -->  Relevance Score: %.15f\n", 
                          values[i].getUrl(), values[i].getScore());
                if( printEntrySeparator ) {   
                    pw.printf(PRETTY_LINE);
                    pw.printf("\n");
                }
            }
            if( !printEntrySeparator ) { 
                pw.print(PRETTY_LINE);
            }

            System.out.println(sw.toString());
        }
    }
	
}
