package iweb2.ch3.collaborative.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class that acts as a holder for user and similarity value that was 
 * assigned to the user.  
 */
public class SimilarUser {
    
    /*
     * The friend User . 
     */
    private User friend;
    
    /*
     * Similarity 
     */
    private double similarity=-1;
    
    public SimilarUser(User user, double similarity) {
    	friend = user;
    	this.similarity = similarity;
    }    

    public int getId() {
        return friend.getId();
    }

    public String getName() {
        return friend.getName();
    }

    public User getUser() {
        return friend;
    }
    
    public static void sort(List<SimilarUser> similarUsers) {
    	
        Collections.sort(similarUsers, new Comparator<SimilarUser>() {
            public int compare(SimilarUser f1, SimilarUser f2) { 
                int result = 0;
                if( f1.getSimilarity() < f2.getSimilarity() ) {
                    result = 1; // reverse order
                }
                else if( f1.getSimilarity() > f2.getSimilarity() ) {
                    result = -1;
                }
                else {
                    result = 0;
                }
                return result;
            }
        });
    }
    
    public static SimilarUser[] getTopNFriends(List<SimilarUser> similarUsers, int topN) {
        
        // sort friends based on itemAgreement
        SimilarUser.sort(similarUsers);
        
        // select top N friends
        List<SimilarUser> topFriends = new ArrayList<SimilarUser>();
        for(SimilarUser f : similarUsers) {
            if( topFriends.size() >= topN ) {
                // have enough friends.
                break;
            }
            
            // This is useful when we compose results from different recommenders
            if (!topFriends.contains(f)) {
            	topFriends.add(f);
            }
        }
        
        return topFriends.toArray(new SimilarUser[topFriends.size()]);
    }

    /**
     * Prints a list of user names with their similarities.
     * 
     * @param friends similar users
     * @param header title that will be printed at the top of the list.
     */
    public static void print(SimilarUser[] friends, String header) {
        System.out.println("\n" + header + "\n");
        for(SimilarUser f : friends) {
            System.out.printf("name: %-36s, similarity: %f\n", 
                    f.getName(), f.getSimilarity());
        }
    }
    
	/**
	 * @return the similarity
	 */
	public double getSimilarity() {
		return similarity;
	}
}
