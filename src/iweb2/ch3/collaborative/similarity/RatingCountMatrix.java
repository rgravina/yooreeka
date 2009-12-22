package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.Rating;
import iweb2.ch3.collaborative.model.User;

import java.io.Serializable;

public class RatingCountMatrix implements Serializable {

    /**
	 * Unique identifier for serialization
	 */
	private static final long serialVersionUID = -8216800040843757769L;
	
	private int matrix[][] = null;

    public RatingCountMatrix(Item itemA, Item itemB, int nRatingValues) {
        init(nRatingValues);
        calculate(itemA, itemB);
    }

    public RatingCountMatrix(User userA, User userB, int nRatingValues) {
        init(nRatingValues);
        calculate(userA, userB);
    }

    private void init(int nSize) {
        // starting point - all elements are zero
        matrix = new int[nSize][nSize];
    }

    /*
     * Populates matrix using user ratings for provided items. We only consider
     * users that rated both items.
     */
    private void calculate(Item itemA, Item itemB) {
        for (Rating ratingForA : itemA.getAllRatings()) {
            // check if the same user rated itemB
            Rating ratingForB = itemB.getUserRating(ratingForA.getUserId());
            if (ratingForB != null) {
                // element in the matrix is determined by the rating values.
                int i = ratingForA.getRating() - 1;
                int j = ratingForB.getRating() - 1;
                matrix[i][j]++;
            }
        }
    }

    /*
     * Populates matrix using ratings for items that the two users share.
     */
    private void calculate(User userA, User userB) {

        for (Rating ratingByA : userA.getAllRatings()) {

            Rating ratingByB = userB.getItemRating(ratingByA.getItemId());

            if (ratingByB != null) {

                int i = ratingByA.getRating() - 1;
                int j = ratingByB.getRating() - 1;
                matrix[i][j]++;
            }
        }
    }

    public int getTotalCount() {

        int ratingCount = 0;
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ratingCount += matrix[i][j];
            }
        }
        return ratingCount;
    }

    public int getAgreementCount() {
        int ratingCount = 0;
        for (int i = 0, n = matrix.length; i < n; i++) {
            ratingCount += matrix[i][i];
        }
        return ratingCount;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getBandCount(int bandId) {
        int bandCount = 0;
        for (int i = 0, n = matrix.length; (i + bandId) < n; i++) {
            bandCount += matrix[i][i + bandId];
            bandCount += matrix[i + bandId][i];
        }
        return bandCount;
    }
}
