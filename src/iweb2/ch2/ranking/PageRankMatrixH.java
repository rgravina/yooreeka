package iweb2.ch2.ranking;

import iweb2.ch2.webcrawler.utils.ValueToIndexMapping;

// Sub-stochastic matrix - some rows will have all zeros
public class PageRankMatrixH {
	
    private ValueToIndexMapping indexMapping = new ValueToIndexMapping();
    
    double[][] matrix;
    
    private int numberOfPagesWithNoLinks = 0;

    
    public PageRankMatrixH(int nPages) {
        matrix = new double[nPages][nPages];
    }
    
    public int getNumberOfPagesWithNoLinks() {
        return this.numberOfPagesWithNoLinks;
    }
    
    public double[][] getMatrix() {
        return matrix;
    }
    
    /**
     * Just associate page url with an index. Used for pages that
     * have no outlinks.
     */
    public void addLink(String pageUrl) {
        indexMapping.getIndex(pageUrl);
    }
    
    public void addLink(String fromPageUrl, String toPageUrl, double weight) {
        int i = indexMapping.getIndex(fromPageUrl);
        int j = indexMapping.getIndex(toPageUrl);
        
        try {
            
            matrix[i][j] = weight;
            
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("fromPageUrl:" + fromPageUrl + ", toPageUrl: " + toPageUrl);
        }
    }
    public void addLink(String fromPageUrl, String toPageUrl) {
        addLink(fromPageUrl, toPageUrl, 1);
    }

    public void calculate() {
    	
        for(int i = 0, n = matrix.length; i < n; i++) {
        	
            double rowSum = 0;
            
            for(int j = 0, k = matrix.length; j < k; j++) {
            
            	rowSum += matrix[i][j];
            }
            
            if( rowSum > 0 ) {
            
            	for(int j = 0, k = matrix.length; j < k; j++) {
                
            		if( matrix[i][j] > 0 ) {
                    
            			matrix[i][j] = matrix[i][j] / rowSum;
                    }
                }
            	
            } else {
                
            	numberOfPagesWithNoLinks++;
            }
        }
    }
    
    public void print() {

    	StringBuilder txt = new StringBuilder("H Matrix\n\n");
    	
    	for (int i = 0, n = matrix.length; i < n; i++) {
    		txt.append("Index: ").append(i);
    		txt.append("  -->  ");
    		txt.append("Page ID: ").append(indexMapping.getValue(i));
    		txt.append("\n");
    	}
    	
    	txt.append("\n").append("\n");
    	
    	for (int i = 0, n = matrix.length; i < n; i++) {

			for (int j = 0, k = matrix.length; j < k; j++) {

				txt.append(" ");
				txt.append(matrix[i][j]);
				
				if (j < k-1) {
					txt.append(", ");
				} else {
					txt.append("\n");
				}
			}
		}
    	
    	System.out.println(txt.toString());
	}
    
    public int getSize() {
    	return matrix.length;
    }

    /** 
     * A <B>dangling node</B> corresponds to a web page that has no outlinks.
     * These nodes result in a H row that has all its values equal to 0.
     */
	public int[] getDangling() {
		
		int   n = getSize();
		int[] d = new int[n];
		
		boolean foundOne = false;
		
		for (int i=0; i < n; i++) {
			
			for (int j=0; j < n; j++) {
			
				if (matrix[i][j] > 0) {
					foundOne = true;
					break;
				} 
			}
			
			if (foundOne) {
				d[i] = 0;
			} else {
				d[i] = 1;
			}
			
			foundOne = false;
		}
		
		return d;
	}

	/**
	 * @return the indexMapping
	 */
	public ValueToIndexMapping getIndexMapping() {
		return indexMapping;
	}
}
