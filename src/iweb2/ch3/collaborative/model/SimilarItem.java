/**
 * 
 */
package iweb2.ch3.collaborative.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author babis
 *
 */
public class SimilarItem {

	private Item item;

    /*
     * Similarity 
     */
    private double similarity=-1;
 
	public SimilarItem(Item item, double sim) {
		this.item = item;
		similarity = sim;
	}
	
    public static void sort(List<SimilarItem> similarItems) {
    	
        Collections.sort(similarItems, new Comparator<SimilarItem>() {
        	
            public int compare(SimilarItem f1, SimilarItem f2) {
            	
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
    
    public static SimilarItem[] getTopSimilarItems(List<SimilarItem> similarItems, int topN) {
        
        // sort friends based on itemAgreement
        SimilarItem.sort(similarItems);
        
        // select top N friends
        List<SimilarItem> topItems = new ArrayList<SimilarItem>();
        for(SimilarItem f : similarItems) {
            if( topItems.size() >= topN ) {
                // have enough friends.
                break;
            }
            topItems.add(f);
        }
        
        return topItems.toArray(new SimilarItem[topItems.size()]);
    }
	
    public static void printItems(SimilarItem[] items, String header) {
        System.out.println("\n" + header + "\n");
        for(SimilarItem f : items) {
            System.out.printf("name: %-36s, similarity: %f\n", 
                    f.getItem().getName(), f.getSimilarity());
        }
    }
	
    
    
	//----------------------------------------------
	// GETTERS / SETTERS
	//----------------------------------------------

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the similarity
	 */
	public double getSimilarity() {
		return similarity;
	}
	
	
}
