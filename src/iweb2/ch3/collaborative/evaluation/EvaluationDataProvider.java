package iweb2.ch3.collaborative.evaluation;

import iweb2.ch3.collaborative.model.Rating;

import java.util.List;

/**
 * Interface to access previously generated evaluation data.  
 */
public interface EvaluationDataProvider {
    List<Rating> loadTrainingRatings(int testSize, int testSequence);
    List<Rating> loadTestRatings(int testSize, int testSequence);
}
