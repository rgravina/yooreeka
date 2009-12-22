/**
 * 
 */
package iweb2.ch4.clustering;

import iweb2.ch4.model.DataPoint;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author babis
 * 
 */
public class SortedArrayClustering {

	public static void cluster(DataPoint[] points) {

		Arrays.sort(points, new Comparator<DataPoint>() {
			public int compare(DataPoint p1, DataPoint p2) {
				int result = 0;
				// sort based on score value
				if (p1.getR() < p2.getR()) {
					result = 1; // sorting in descending order
				} else if (p1.getR() > p2.getR()) {
					result = -1;
				} else {
					result = 0;
				}
				return result;
			}
		});
		
		for (int i=0; i < points.length; i++) {
			System.out.println(points[i].toShortString());
		}
	}
}
