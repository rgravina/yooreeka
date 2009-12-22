package iweb2.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TermFreqMapUtils {

    public static Map<String, Integer> getTopNTermFreqMap(
            String[] terms, int[] frequencies, int topNTerms) {
        
        Map<String, Integer> tfMap = TermFreqMapUtils.buildTermFreqMap(terms, frequencies);
        boolean descending = true;
        String[] sortedTerms = TermFreqMapUtils.sortTermsByFrequencies(
                tfMap, descending);
        int n = Math.min(sortedTerms.length, topNTerms);
        Map<String, Integer> topNTermFreqMap = new HashMap<String, Integer>();
        for(int i = 0; i < n; i++) {
            String key = sortedTerms[i];
            Integer value = tfMap.get(sortedTerms[i]);
            topNTermFreqMap.put(key, value);
        }
        
        return topNTermFreqMap;
        
    }
    
    public static String[] sortTermsByFrequencies(
            final Map<String, Integer> tfMap, final boolean descending) {
        
        String[] sortedTerms = tfMap.keySet().toArray(new String[tfMap.size()]);
        
        Arrays.sort(sortedTerms, new Comparator<String>() {

            public int compare(String key1, String key2) {
                int v1 = tfMap.get(key1);
                int v2 = tfMap.get(key2);
                if( descending ) {
                    return v2 - v1;
                }
                else {
                    return v1 - v2;
                }
            }
            
        });
        
        return sortedTerms;
    }
    
    public static Map<String, Integer> buildTermFreqMap(String[] keys, int[] values) {
        int n = keys.length;
        Map<String, Integer> map = new HashMap<String, Integer>(n);
        
        for(int i = 0; i < n; i++) {
            map.put(keys[i], values[i]);
        }
        
        return map;
    }
}
