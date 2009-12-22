package iweb2.ch5.classification.dtree;

import iweb2.ch5.ontology.intf.Instance;

import java.util.List;
import java.util.Map;

public class InfoGain {

    public InfoGain() {
    }

    /**
     * Gain ratio.
     * 
     * @param allData initial set of instances.
     * @param allDataSubsets initial set split into subsets.
     * 
     * @return gain ratio.
     */
    public Double gainRatio(List<Instance> allData,
            List<List<Instance>> allDataSubsets) {

        return gain(allData, allDataSubsets)
                / splitInfo(allData, allDataSubsets);

    }

    public Double splitInfo(List<Instance> allData,
            List<List<Instance>> allDataSubsets) {

        double sum = 0.0;

        int n = allData.size();

        for (List<Instance> dataSubset : allDataSubsets) {

            double ratio = (double) dataSubset.size() / (double) n;

            sum += ratio * log2(ratio);

        }

        return -sum;

    }

    /**
     * Information gain for a given split.
     * 
     * @param allData initial set of instances.
     * @param allDataSubsets initial set split into subsets.
     * 
     * @return information gain.
     */
    public Double gain(List<Instance> allData,
            List<List<Instance>> allDataSubsets) {

        return entropy(allData) - expectedInformation(allData, allDataSubsets);

    }

    public Double expectedInformation(List<Instance> allData,
            List<List<Instance>> allDataSubsets) {

        double sum = 0.0;

        int n = allData.size();

        for (List<Instance> dataSubset : allDataSubsets) {

            sum += (double) dataSubset.size() / (double) n
                    * entropy(dataSubset);

        }

        return sum;

    }

    /**
     * Entropy of the dataset.
     *  
     * @param data
     * @return
     */
    public Double entropy(List<Instance> data) {

        /*
         * How many times each class (category) occurs in the data.
         */
        Map<String, Integer> instanceCountByClassMap = 
            ConceptUtils.countConcepts(data);

        int n = data.size();

        double sum = 0.0;

        for (Integer count : instanceCountByClassMap.values()) {

            double p = (double) count / (double) n;

            sum += p * log2(p);

        }

        return -sum;

    }

    private double log2(double d) {

        return Math.log(d) / Math.log(2.0);

    }

}