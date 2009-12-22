package iweb2.ch3.collaborative.evaluation;

import iweb2.ch3.collaborative.data.MovieLensDataset;
import iweb2.ch3.collaborative.model.Dataset;
import iweb2.ch3.collaborative.model.Rating;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MovieLensEvaluationDataProvider implements EvaluationDataProvider {

    /*
     * Location for files with test and training data.
     */
    private String evaluationDataDir;
    
    /*
     * Provides data that will be used to produce training and test files.
     */
    private Dataset dataset;
    
    /*
     * Prefix that will be used in filename for files with test
     * ratings.
     */
    private String testFilenamePrefix;
    
    /*
     * Prefix that will be used in filename for files with training
     * ratings.
     */
    private String trainingFilenamePrefix;

    public MovieLensEvaluationDataProvider(Dataset dataset) {
        this.dataset = dataset;
    }

    public String getEvaluationDataDir() {
        return evaluationDataDir;
    }


    public void setEvaluationDataDir(String value) {
        this.evaluationDataDir = value;
    }


    public String getTestFilenamePrefix() {
        return testFilenamePrefix;
    }

    public void setTestFilenamePrefix(String testFilenamePrefix) {
        this.testFilenamePrefix = testFilenamePrefix;
    }

    public String getTrainingFilenamePrefix() {
        return trainingFilenamePrefix;
    }

    public void setTrainingFilenamePrefix(String trainingFilenamePrefix) {
        this.trainingFilenamePrefix = trainingFilenamePrefix;
    }
    
    /**
     * Creates a set of training and test data.  
     * 
     * @param testSize number of ratings that will be used to create testing set.
     * Size of training set is defined as AllAvailableRatings - testSize 
     */
    public void prepareTestData(int testSize) {
        prepareTestData(testSize, 1);
    }
    
    /**
     * Creates multiple sets of training and test data. Should be used when we 
     * need to create multiple test files for the same tests. 
     * 
     * @param testSize number of test ratings.
     * @param sequence test sequence.
     */
    public void prepareTestData(int testSize, int sequence) {
        if( !testDataExist(testSize, sequence) ) {
            createData(testSize, sequence);
        }
    }
    
    /**
     * Checks if the test set already exists.
     * 
     * @param testSize
     * @param sequence
     * @return
     */
    public boolean testDataExist(int testSize, int sequence) {
        // create temporary directory if it doesn't exist yet.
        File tmpDirFile = new File(evaluationDataDir);
        if(!tmpDirFile.exists()) {
            tmpDirFile.mkdirs();
        }
        
        boolean filesExist = false;
        String testFilename = createFilename(testFilenamePrefix, testSize, sequence);
        String trainingFilename = createFilename(trainingFilenamePrefix, testSize, sequence); 
        if( new File(evaluationDataDir, testFilename).exists() && 
            new File(evaluationDataDir, trainingFilename).exists() ) {
            filesExist = true;
        }
        
        return filesExist;
    }
    
    public boolean testDataExist(int testSize) {
        return testDataExist(testSize, 1);
    }

    /**
     * Deletes test data.
     * 
     * @param testSize
     * @param sequence
     */
    public void removeTestData(int testSize, int sequence) {
        String testFilename = createFilename(testFilenamePrefix, testSize, sequence);
        removeFile(testFilename);
        
        String trainingFilename = createFilename(trainingFilenamePrefix, testSize, sequence); 
        removeFile(trainingFilename);
    }

    /**
     * Deletes test data. Defaults sequence to 1.
     * 
     * @param testSize
     */
    public void removeTestData(int testSize) {
        removeTestData(testSize, 1);
    }
    
    public void createData(int testSize) {
        createData(testSize, 1);
    }

    /**
     * Creates evaluation data by splitting original item rating set into two 
     * sets: training set and test sets. Test set is built by randomly selecting 
     * ratings from the original ratings set. Training set is built by selecting
     * everything that is left from the original set. 
     * 
     * @param testSize number of ratings in test set.
     * @param testSequence allows to generate multiple test sets with the same 
     *                     number or ratings. 
     */
    public void createData(int testSize, int sequence) {
        
        /* start with complete list of all available ratings */
        List<Rating> allRatings = new ArrayList<Rating>(
                dataset.getRatings());
        
        /* extract required number of ratings and use them as testing set */
        List<Rating> testRatings = removeRatings(allRatings, testSize);
        /* use the of ratings as a training set */
        List<Rating> trainingRatings = allRatings;
        
        String testRatingsFilename = createFilename(
                testFilenamePrefix, testSize, sequence);
        
        String trainingRatingsFilename = createFilename(
                trainingFilenamePrefix, testSize, sequence);
        
        saveRatings(testRatingsFilename, testRatings);
        saveRatings(trainingRatingsFilename, trainingRatings);
    }
    
    private List<Rating> removeRatings(
            List<Rating> allRatings, int n) {
        
        List<Rating> removedRatings = new ArrayList<Rating>();
        Random rnd = new Random();
        while( removedRatings.size() < n ) {
            int randomIndex = rnd.nextInt(allRatings.size());
            Rating rating = allRatings.remove(randomIndex);
            removedRatings.add(rating);
        }
        return removedRatings;
    }
    
    private void saveRatings(String filename, Collection<Rating> ratings) {
        File f = new File(evaluationDataDir, filename);
        MovieLensDataset.createNewRatingsFile(f, ratings);
    }
    
    private void removeFile(String filename) {
        File f = new File(evaluationDataDir, filename);
        if( f.exists() ) {
            f.delete();
        }
    }
    
    /**
     * Builds unique filename for file that contains ratings for training or test. 
     * 
     * @param namePrefix identifies source of the data and the purpose (testing or training) of the file.   
     * @param n number or ratings that were randomly selected from the original set of ratings and put in
     * test file. Both training and test files are identified by this number.  
     * @param sequence random selection sequence. In some cases when we need to generate multiple 
     * test files with the same number of ratings but with different selection every time. 
     * Defaults to 1. 
     *
     * Example:
     * 
     * MovieLensRatingsTrainingN16000Rnd1.dat - first training file that was obtained by 
     * removing 16000 ratings from original ratings file. 
     * MovieLensRatingsTestN16000Rnd1.dat - first test file with 16000 ratings that were 
     * removed from original ratings file. 
     * MovieLensRatingsTrainingN16000Rnd2.dat - second training file that was obtained by 
     * removing 16000 ratings from original ratings file.
     * MovieLensRatingsTestN16000Rnd2.dat - second test file with 16000 ratings that were 
     * removed from original ratings file.
     */
    public String createFilename(String namePrefix, int n, int sequence) {
        return namePrefix + "N" + n + "Rnd" + sequence + ".dat";
    }

    public List<Rating> loadTestRatings(int testSize, int testSequence) {
        String filename = createFilename(
                testFilenamePrefix, testSize, testSequence);
        File f = new File(evaluationDataDir, filename);
        
        return MovieLensDataset.loadRatings(f);
    }


    public List<Rating> loadTrainingRatings(int testSize, int testSequence) {
        String filename = createFilename(
                trainingFilenamePrefix, testSize, testSequence);
        File f = new File(evaluationDataDir, filename);
        return MovieLensDataset.loadRatings(f);
    }
    
    
   
}
