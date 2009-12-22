package iweb2.ch6.usecase.credit.util;

import iweb2.ch6.usecase.credit.data.users.User;
import iweb2.ch6.usecase.credit.data.users.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataGenerator {

    private long nextUserId = 1;
    
    private boolean isNoiseOn = false;
    private HashMap<UserType,Integer> userTypeDistributions; 
    
    public DataGenerator() {
    	userTypeDistributions = new HashMap<UserType,Integer>(); 
    }

    public List<User> generateUsers(List<UserType> userTypes) {
        List<User> allUsers = new ArrayList<User>();
        
        for(UserType userType : userTypes) {
            allUsers.addAll( generateUsers(userType, userType.getNUsers() ));
        }
        
        return allUsers;
    }
    
    public List<User> generateUsers(UserType userType, int n) {
        
        List<User> users = new ArrayList<User>();
        
        userTypeDistributions.put(userType, n);
        
        for(int i = 0; i < n; i++) {
            User u = generateUser(userType);
            users.add(u);
        }
        
        return users;
    }

    public User generateUser(UserType userType) {
        
    	User user = new User();
        
    	long userId = generateNextUniqueUserId();
        
        String username;
        
        if (isNoiseOn) {
        	username = userType.getNoisyType();
        } else {
        	username = userType.getUserType();
        }
        
        username = username + String.valueOf(userId);
        
        user.setUsername(username);
        
        user.setAge(userType.pickAge());
        user.setCarOwnership(userType.pickCarOwnership());
        user.setCreditScore(userType.pickCreditScore());
        user.setIncome(userType.pickIncome());
        user.setJobClass(userType.pickJobClass());
        user.setDownPayment(userType.pickDownPayment());
        user.setBicycleOwnership(userType.pickMotorcycleOwnership());
        user.setPropertyOwnership(userType.pickPropertyOwnership());
        user.setCriminalRecord(userType.pickCriminalRecord());
        user.setBankruptcy(userType.pickBancruptcy());
        user.setRetirementAccount(userType.pickRetirementAccounts());
        
        return user;
    }

	/**
	 * @return the isNoiseOn
	 */
	public boolean isNoiseOn() {
		return isNoiseOn;
	}

	/**
	 * @param isNoiseOn the isNoiseOn to set
	 */
	public void setNoiseOn(boolean isNoiseOn) {
		this.isNoiseOn = isNoiseOn;
	}
	
    public void setNextUserId(long nextUserId) {
        this.nextUserId = nextUserId;
    }
    
    private long generateNextUniqueUserId() {
        return nextUserId++;
    }    
}
