package iweb2.ch4.utils;

import java.util.HashMap;
import java.util.Map;

public class TermFrequencyBuilder {

    /**
     * Calculates term frequency vectors based on two sets of terms.
     */
    public static double[][] buildTermFrequencyVectors(String[] x, String[] y) {

        // create a set of terms with flags
        Map<String, Integer> allAttributes = new HashMap<String, Integer>();
        for(String s : x) {
            // set flags to indicate that this term is present only in x[]
            allAttributes.put(s, 0x01);  
        }
        for(String s : y) {
            if( !allAttributes.containsKey(s) ) {
                // set flags to indicate that this term is present only in y[]
                allAttributes.put(s, 0x02); 
            }
            else {
                // set flags to indicate that this term is present in x[] and y[]
                allAttributes.put(s, 0x03); 
            }
        }

        // create term frequency vectors
        int n = allAttributes.size();
        double[] termFrequencyForX = new double[n];
        double[] termFrequencyForY = new double[n];
        int i = 0;
        for(Map.Entry<String, Integer> e : allAttributes.entrySet()) {
            // 0x01 - x[] only , 
            // 0x02 - y[] only, 
            // 0x03 - x[] and y[]
            int flags = e.getValue(); 
            termFrequencyForX[i] = flags & 0x01;
            termFrequencyForY[i] = flags >> 1;
            i++;
        }
        
        return new double[][] { termFrequencyForX, termFrequencyForY };
    }

}
