package iweb2.ch3.collaborative.model;

/**
 * Generic representation of a rating given by user to a product (item).
 */
public class Rating implements java.io.Serializable {

    /**
	 * SVUID 
	 */
	private static final long serialVersionUID = 1438346522502387789L;

	protected Item item;
	
    private int userId;
    private int itemId;
    private int rating;
    
    public Rating(int userId, int bookId, int rating) {
        this.userId = userId;
        this.itemId = bookId;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int bookId) {
        this.itemId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    @Override
	public String toString() {
        return this.getClass().getSimpleName() + "[userId: " + userId
                + ", itemId: " + itemId + ", rating: " + rating + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + itemId;
        result = prime * result + rating;
        result = prime * result + userId;
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
        final Rating other = (Rating) obj;
        if (itemId != other.itemId)
            return false;
        if (rating != other.rating)
            return false;
        if (userId != other.userId)
            return false;
        return true;
    }

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
}
