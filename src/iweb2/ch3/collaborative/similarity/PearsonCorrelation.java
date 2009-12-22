/**
 * 
 */
package iweb2.ch3.collaborative.similarity;

import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.User;


/**
 * @author babis
 *
 */
public class PearsonCorrelation {
	
	private static final double ZERO = 0.0d;
	
	int n;
	
	double[] x;
	double[] y;
	
	public PearsonCorrelation(Dataset ds, Item iA, Item iB) {
		
		double aAvgR = iA.getAverageRating();
		double bAvgR = iB.getAverageRating();
		
		Integer[] uid = Item.getSharedUserIds(iA, iB);
		n = uid.length;

		x = new double[n];
		y = new double[n];
		
		User u;
		double urA=0;
		double urB=0;
		
		for (int i=0; i<n; i++) {
			
			u = ds.getUser(uid[i]);
			urA = u.getItemRating(iA.getId()).getRating();
			urB = u.getItemRating(iB.getId()).getRating();
			
			x[i] = urA - aAvgR;
			y[i] = urB - bAvgR;
		}
	}
	
	public PearsonCorrelation(double[] x, double[] y) throws java.lang.IllegalArgumentException {
		
		if (x.length != y.length) {
			throw new IllegalArgumentException("Arrays x and y should have the same length!");
		}
	
		n = x.length;
		//System.out.print("N="+n);
		
		this.x = x;
		this.y = y;
	}
	
	public double calculate() {
	    
	    if( n == 0) {
	        return 0.0;
	    }
	    
		double rho=0.0d;
		
		double avgX = getAverage(x);
		double avgY = getAverage(y);
		
		double sX = getStdDev(avgX,x);
		double sY = getStdDev(avgY,y);
		
		double xy=0;
		
		for (int i=0; i < n; i++) {
		
			xy += (x[i]-avgX)*(y[i]-avgY);
		}

        //No variation -- all points have the same values for either X or Y or both
        if( sX == ZERO || sY == ZERO) {
            
        	double indX = ZERO;
        	double indY = ZERO;
        	
    		for (int i=1; i < n; i++) {
    			
    			indX += (x[0]-x[i]);
    			indY += (y[0]-y[i]); 
    		}
    		    		
    		if (indX == ZERO && indY == ZERO) {
    			// All points refer to the same value
    			// This is a degenerate case of correlation
    			return 1.0;
    		} else {
    			//Either the values of the X vary or the values of Y
    			if (sX == ZERO) {
    				sX = sY;
    			} else {
    				sY = sX;
    			}
    		}
        }
				
		rho = xy / (n*(sX*sY));
		
		return rho;
	}

	private double getAverage(double[] v) {
		double avg=0;
		
		for (double xi : v ) {
			avg += xi;
		}
		
		avg = avg/v.length;
		
		//System.out.print("Average: "+avg);
		return avg;
	}
	
	private double getStdDev(double m, double[] v) {
		double sigma=0;
		
		for (double xi : v ) {
			sigma += (xi - m)*(xi - m);
		}
		
		sigma = sigma / v.length;
		
		//System.out.print("StdDev: "+Math.sqrt(sigma));
		return Math.sqrt(sigma);
	}
	
}
