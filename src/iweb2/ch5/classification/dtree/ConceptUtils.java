package iweb2.ch5.classification.dtree;

import iweb2.ch5.ontology.intf.Instance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConceptUtils {

    public static String[] getUniqueConcepts(List<Instance> instances) {
        Set<String> concepts = new HashSet<String>();
        for(Instance i : instances) {
            concepts.add(i.getConcept().getName());
        }
        return concepts.toArray(new String[concepts.size()]);
    }
    
    
    
    public static String findMostFrequentConcept(List<Instance> instances) {

        Map<String, Integer> conceptCounts = countConcepts(instances);
        
        String mostFrequentConceptLabel = null;
        
        int n = 0;
        for(Map.Entry<String, Integer> e : conceptCounts.entrySet()) {
            if( e.getValue() > n ) {
                n = e.getValue();
                mostFrequentConceptLabel = e.getKey();
            }
        }
        
        return mostFrequentConceptLabel;
    }
    
    public static Map<String, Integer> countConcepts(List<Instance> instances) {

        Map<String, Integer> conceptCounts = new HashMap<String, Integer>();
        
        for( Instance i : instances) {
            String conceptName = i.getConcept().getName();
            Integer count = conceptCounts.get(conceptName);
            if( count == null ) {
                count = 1;
            }
            else {
                count++;
            }
            conceptCounts.put(conceptName, count);
        }

        return conceptCounts;
    }
}
