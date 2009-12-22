package iweb2.ch2.clicks;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 * This is a class that encapsulates a personalized query
 * 
 * @author babis
 *
 */
public class UserQuery {

	private String uid;
	private String query;
	private String[] queryTerms;
	
	public UserQuery(String uid, String q) throws IOException {
		
		setUid(uid);
		setQuery(q);
		
		ArrayList<String> qTerms = new ArrayList<String>();
		StandardAnalyzer analyzer = new StandardAnalyzer();
		
		TokenStream stream = analyzer.tokenStream("query", new StringReader(q));
		
		boolean hasTokens = true; 
		while (hasTokens) {
			
			Token t = stream.next();
			
			if (t == null) {
				
				hasTokens = false;
				
			} else {
				
				qTerms.add(new String(t.termBuffer(), 0, t.termLength()));
			}
		}
		
		queryTerms = qTerms.toArray(new String[qTerms.size()]);
	}
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getName() {
		return UserQuery.class.getCanonicalName();
	}

	public UserQuery getValue() {
		
		return this;
	}

	/**
	 * @return the queryTerms
	 */
	public String[] getQueryTerms() {
		return queryTerms;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((query == null) ? 0 : query.hashCode());
        result = prime * result + Arrays.hashCode(queryTerms);
        result = prime * result + ((uid == null) ? 0 : uid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserQuery other = (UserQuery) obj;
        if (query == null) {
            if (other.query != null)
                return false;
        } else if (!query.equals(other.query))
            return false;
        if (!Arrays.equals(queryTerms, other.queryTerms))
            return false;
        if (uid == null) {
            if (other.uid != null)
                return false;
        } else if (!uid.equals(other.uid))
            return false;
        return true;
    }

}
