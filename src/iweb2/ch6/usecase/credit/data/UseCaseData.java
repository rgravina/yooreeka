package iweb2.ch6.usecase.credit.data;

import iweb2.ch6.usecase.credit.data.users.BadUserType;
import iweb2.ch6.usecase.credit.data.users.DangerousUserType;
import iweb2.ch6.usecase.credit.data.users.ExcellentUserType;
import iweb2.ch6.usecase.credit.data.users.GoodUserType;
import iweb2.ch6.usecase.credit.data.users.User;
import iweb2.ch6.usecase.credit.data.users.UserType;
import iweb2.ch6.usecase.credit.data.users.VeryGoodUserType;
import iweb2.ch6.usecase.credit.util.DataGenerator;
import iweb2.ch6.usecase.credit.util.DataUtils;
import iweb2.util.config.IWeb2Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Example for how to configure and generate file with transactions. 
 */
public class UseCaseData {

    /*
     * Generated transactions will be saved into this file.
     */
    public static String TRAINING_USERS_FILENAME = 
    	IWeb2Config.getHome()+"/data/ch06/generated-training-users.txt";
    
    public static String TEST_USERS_FILENAME = 
    	IWeb2Config.getHome()+"/data/ch06/generated-test-users.txt";

    DataGenerator dataGenerator = new DataGenerator();
    
    //INSTANCE VARIABLES
    int nTrainingUsers;
    int nTestUsers;
    
    
    public UseCaseData(int nTrainingUsers, int nTestUsers) {
    	this.nTrainingUsers=nTrainingUsers;
    	this.nTestUsers=nTestUsers;
    }

	public void create() {
    	
		System.out.println("Creating data for the credit worthiness (score) use case:");
		System.out.println("   Number of users in the training set: "+nTrainingUsers);
		System.out.println("    Number of users in the testing set: "+nTestUsers);
		System.out.println("___________________________________________________________");
		
        List<UserType> trainingUserTypes = createUserTypes(nTrainingUsers);
        int userIdSequenceStart = 1;
        generateUsers(TRAINING_USERS_FILENAME, userIdSequenceStart, trainingUserTypes);
        
        dataGenerator.setNoiseOn(true);
        
        List<UserType> testUserTypes = createUserTypes(nTestUsers);
        userIdSequenceStart = 500000;
        // generateUsers(TEST_USERS_FILENAME, 2*nTrainingUsers, testUserTypes);
        generateUsers(TEST_USERS_FILENAME, userIdSequenceStart, testUserTypes);
        
        System.out.println("Done!");        
    }
    
	public void create(boolean overwrite) {
		if (overwrite) {
			TRAINING_USERS_FILENAME = IWeb2Config.getHome()+"/data/ch06/training-users.txt";
			TEST_USERS_FILENAME = IWeb2Config.getHome()+"/data/ch06/test-users.txt";
		}		
		create();
	}
	
    public static void main(String[] args) {

    	UseCaseData useCaseData = new UseCaseData(100000,50000);
        //UseCaseData useCaseData = new UseCaseData(10000,5000);
    	useCaseData.create();
    }
    
    public void generateUsers(String filename, int nextUserId, 
            List<UserType> userTypes) {
        
        dataGenerator.setNextUserId(nextUserId);
        System.out.println("Generating users...");        
        List<User> allUsers = dataGenerator.generateUsers(userTypes);
        System.out.println("Saving users into '" + filename + "'");        
        DataUtils.saveUsers(filename, allUsers);
    }
    
    public List<UserType> createUserTypes(int nUsers) {
        List<UserType> allUserTypes = new ArrayList<UserType>();

        // Excellent credit users
        // 5% of the total number of users
        UserType userType = new ExcellentUserType();
        userType.setNUsers((int) (nUsers*0.05));
        
        allUserTypes.add( userType );
        
        // Very good credit users
        // 15% of the total number of users
        userType = new VeryGoodUserType();
        userType.setNUsers((int) (nUsers*0.15));
        
        allUserTypes.add( userType );

        // Good credit users
        // 50% of the total number of users
        userType = new GoodUserType();
        userType.setNUsers((int) (nUsers*0.50));
        
        allUserTypes.add( userType );

        // Bad credit users
        // 25% of the total number of users
        userType = new BadUserType();
        userType.setNUsers((int) (nUsers*0.25));
        
        allUserTypes.add( userType );
        
        // Dangerous credit users
        // 5% of the total number of users
        userType = new DangerousUserType();
        userType.setNUsers((int) (nUsers*0.05));
        allUserTypes.add( userType );
        
        return allUserTypes;
    }

	/**
	 * @return the nTrainingUsers
	 */
	public int getTrainingUsers() {
		return nTrainingUsers;
	}

	/**
	 * @param trainingUsers the nTrainingUsers to set
	 */
	public void setTrainingUsers(int n) {
		nTrainingUsers = n;
	}

	/**
	 * @return the nTestUsers
	 */
	public int getTestUsers() {
		return nTestUsers;
	}

	/**
	 * @param testUsers the nTestUsers to set
	 */
	public void setTestUsers(int n) {
		nTestUsers = n;
	}
}
