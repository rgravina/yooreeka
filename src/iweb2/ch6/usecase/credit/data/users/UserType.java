package iweb2.ch6.usecase.credit.data.users;

import java.util.HashMap;
import java.util.Random;

public abstract class UserType {
   
	public static final String EXCELLENT = "EX";
	public static final String VERY_GOOD = "VG";
	public static final String GOOD = "GD";
	public static final String BAD = "BD";
	public static final String DANGEROUS = "DN";
	
	private static volatile HashMap<String,Double[]> noiseLevels;
	
    private Random rnd = new Random();
    
    private int nUsers;
    
    private int[] jobClass;
    private int[] carOwnership;
    private int[] motorcycleOwnership;
    private int[] propertyOwnership;
    private int[] retirementAccounts;
    private int[] creditScore;
    private int[] age;
    private int[] downPayment;
    private int[] bancruptcy;
    private int[] criminalRecord;
    private int[] income;
    
    static {
    	//Set the default noise levels
        noiseLevels = new HashMap<String,Double[]>();
        
        Double[] exLevels = new Double[] {1.0d, 3.0d, 7.5d, 10.0d};
        Double[] vgLevels = new Double[] {1.0d, 3.0d, 6.0d, 10.0d};
        Double[] gdLevels = new Double[] {1.0d, 3.0d, 4.0d, 8.0d};
        Double[] bdLevels = new Double[] {1.0d, 3.0d, 7.5d, 10.0d};
        Double[] dnLevels = new Double[] {1.0d, 4.5d, 9.0d, 13.5d};
        
        noiseLevels.put(EXCELLENT,exLevels);
        noiseLevels.put(VERY_GOOD, vgLevels);
        noiseLevels.put(GOOD, gdLevels);
        noiseLevels.put(BAD, bdLevels);
        noiseLevels.put(DANGEROUS, dnLevels);
    }

    public UserType() {
    	//empty
    }
    
    public abstract String getUserType();
    
    public void setNUsers(int nUsers) {
        this.nUsers = nUsers;
    }
    
    public int getNUsers() {
        return nUsers;
    }
        
    //-----------------------------------------------------------------
    /**
	 * @return the jobClass
	 */
	public int[] getJobClass() {
		return jobClass;
	}

	/**
	 * This method, and the other "pickX()" methods in this class, select
	 * a random value from the set of eligible values for a particular 
	 * <code>UserType</code>. Hence, clearly, the returned values will be 
	 * different for the different <code>UserType</code>s.
	 * 
	 * @return a random selection from the set of eligible job classes. 
	 */
	public int pickJobClass() {
	    return jobClass[rnd.nextInt(jobClass.length)];
	}
	
	/**
	 * @param jobClass the jobClass to set
	 */
	public void setJobClass(int[] jobClass) {
		this.jobClass = jobClass;
	}
	
	//-----------------------------------------------------------------
	/**
	 * @return the carOwnership
	 */
	public int[] getCarOwnership() {
		return carOwnership;
	}

	public int pickCarOwnership() {
	    return carOwnership[rnd.nextInt(carOwnership.length)];		
	}
	
	/**
	 * @param carOwnership the carOwnership to set
	 */
	public void setCarOwnership(int[] carOwnership) {
		this.carOwnership = carOwnership;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the motorcycleOwnership
	 */
	public int[] getMotorcycleOwnership() {
		return motorcycleOwnership;
	}

	public int pickMotorcycleOwnership() {
		return motorcycleOwnership[rnd.nextInt(motorcycleOwnership.length)];
	}
	
	/**
	 * @param motorcycleOwnership the motorcycleOwnership to set
	 */
	public void setMotorcycleOwnership(int[] bicycleOwnership) {
		this.motorcycleOwnership = bicycleOwnership;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the propertyOwnership
	 */
	public int[] getPropertyOwnership() {
		return propertyOwnership;
	}

	public int pickPropertyOwnership() {
		return propertyOwnership[rnd.nextInt(propertyOwnership.length)];
	}
	
	/**
	 * @param propertyOwnership the propertyOwnership to set
	 */
	public void setPropertyOwnership(int[] propertyOwnership) {
		this.propertyOwnership = propertyOwnership;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the retirementAccounts
	 */
	public int[] getRetirementAccounts() {
		return retirementAccounts;
	}

	public int pickRetirementAccounts() {
		return retirementAccounts[rnd.nextInt(retirementAccounts.length)];
	}
	
	/**
	 * @param retirementAccounts the retirementAccounts to set
	 */
	public void setRetirementAccounts(int[] retirementAccounts) {
		this.retirementAccounts = retirementAccounts;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the creditScore
	 */
	public int[] getCreditScore() {
		return creditScore;
	}

	public int pickCreditScore() {
		return creditScore[rnd.nextInt(creditScore.length)];
	}
	
	/**
	 * @param creditScore the creditScore to set
	 */
	public void setCreditScore(int[] creditScore) {
		this.creditScore = creditScore;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the age
	 */
	public  int[] getAge() {
		return age;
	}

	public int pickAge() {
		return age[rnd.nextInt(age.length)];
	}
	
	/**
	 * @param age the age to set
	 */
	public void setAge(int[] age) {
		this.age = age;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the downPayment
	 */
	public int[] getDownPayment() {
		return downPayment;
	}

	public int pickDownPayment() {
		return downPayment[rnd.nextInt(downPayment.length)];
	}
	
	/**
	 * @param downPayment the downPayment to set
	 */
	public void setDownPayment(int[] downPayment) {
		this.downPayment = downPayment;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the bancruptcy
	 */
	public  int[] getBancruptcy() {
		return bancruptcy;
	}

	public int pickBancruptcy() {
		return bancruptcy[rnd.nextInt(bancruptcy.length)];
	}
	
	/**
	 * @param bancruptcy the bancruptcy to set
	 */
	public void setBancruptcy(int[] bancruptcy) {
		this.bancruptcy = bancruptcy;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the criminalRecord
	 */
	public int[] getCriminalRecord() {
		return criminalRecord;
	}

	public int pickCriminalRecord() {
		return criminalRecord[rnd.nextInt(criminalRecord.length)];
	}
	
	/**
	 * @param criminalRecord the criminalRecord to set
	 */
	public void setCriminalRecord(int[] criminalRecord) {
		this.criminalRecord = criminalRecord;
	}

	//-----------------------------------------------------------------
	/**
	 * @return the income
	 */
	public int[] getIncome() {
		return income;
	}

	public int pickIncome() {
		return income[rnd.nextInt(income.length)];
	}
	
	/**
	 * @param income the income to set
	 */
	public void setIncome(int[] income) {
		this.income = income;
	}

	//-----------------------------------------------------------------
	
	public String getNoisyType() {
		
		double gaussian = rnd.nextGaussian();
		
		String noisyType=null;

		String userType= getUserType();
		
		Double[] nLevels = noiseLevels.get(userType);
		
		if (getUserType().equals(EXCELLENT)) {
			
			if (gaussian <= nLevels[0]) {
				
				noisyType = EXCELLENT;
				
			} else if (gaussian >  nLevels[0] && 
					   gaussian <= nLevels[1]) {
			
				noisyType = VERY_GOOD;
				
			} else if (gaussian >  nLevels[1] && 
					   gaussian <= nLevels[2]) {
				
				noisyType = GOOD;
				
			} else if (gaussian >  nLevels[2] && 
					   gaussian <= nLevels[3]) {
				
				noisyType = BAD;
				
			} else {
				
				noisyType = DANGEROUS;
			}
			
		}  else if (getUserType().equals(VERY_GOOD)) {
			
			if (gaussian <= nLevels[0]) {
				
				noisyType = VERY_GOOD;
				
			} else if (gaussian >  nLevels[0] && 
					   gaussian <= nLevels[1]) {
			
				noisyType = GOOD;
				
			} else if (gaussian >  nLevels[1] && 
					   gaussian <= nLevels[2]) {
				
				noisyType = EXCELLENT;
				
			} else if (gaussian >  nLevels[2] && 
					   gaussian <= nLevels[3]) {
				
				noisyType = BAD;
				
			} else {
				
				noisyType = DANGEROUS;
			}
			
		} else if (getUserType().equals(GOOD)) {

			if (gaussian <= nLevels[0]) {
				
				noisyType = GOOD;
				
			} else if (gaussian >  nLevels[0] && 
					   gaussian <= nLevels[1]) {
			
				noisyType = VERY_GOOD;
				
			} else if (gaussian >  nLevels[1] && 
					   gaussian <= nLevels[2]) {
				
				noisyType = EXCELLENT;
				
			} else if (gaussian >  nLevels[2] && 
					   gaussian <= nLevels[3]) {
				
				noisyType = BAD;
				
			} else {
				
				noisyType = DANGEROUS;
			}
			
		}  else if (getUserType().equals(BAD)) {

			if (gaussian <= nLevels[0]) {
				
				noisyType = BAD;
				
			} else if (gaussian >  nLevels[0] && 
					   gaussian <= nLevels[1]) {
			
				noisyType = GOOD;
				
			} else if (gaussian >  nLevels[1] && 
					   gaussian <= nLevels[2]) {
				
				noisyType = DANGEROUS;
				
			} else if (gaussian >  nLevels[2] && 
					   gaussian <= nLevels[3]) {
				
				noisyType = VERY_GOOD;
				
			} else {
				
				noisyType = EXCELLENT;
			}
			
		}  else if (getUserType().equals(DANGEROUS)) {

			if (gaussian <= nLevels[0]) {
				
				noisyType = DANGEROUS;
				
			} else if (gaussian >  nLevels[0] && 
					   gaussian <= nLevels[1]) {
			
				noisyType = BAD;
				
			} else if (gaussian >  nLevels[1] && 
					   gaussian <= nLevels[2]) {
				
				noisyType = GOOD;
				
			} else if (gaussian >  nLevels[2] && 
					   gaussian <= nLevels[3]) {
				
				noisyType = VERY_GOOD;
				
			} else {
				
				noisyType = EXCELLENT;
			}			
		} 
		
		return noisyType;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getUserType() == null) ? 0 : getUserType().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserType other = (UserType) obj;
        if (getUserType() == null) {
            if (other.getUserType() != null)
                return false;
        } else if (!getUserType().equals(other.getUserType()))
            return false;
        return true;
    }

	/**
	 * This method returns the noise levels by credit type
	 * 
	 * @return the noiseLevels
	 */
	public static HashMap<String, Double[]> getNoiseLevels() {
		return UserType.noiseLevels;
	}

	/**
	 * This method allows the insertion of custom noise levels in bulk
	 * 
	 * @param noiseLevels the noiseLevels to set
	 */
	public static void setNoiseLevels(HashMap<String, Double[]> noiseLevels) {
		UserType.noiseLevels = noiseLevels;
	}
    
	/**
	 * This method allows the insertion of custom noise levels by credit type.
	 * 
	 * @param type
	 * @param levels
	 */
	public static void addNoiseLevel(String type, Double[] levels) {
		
		if (noiseLevels.containsKey(type)) {
			System.out.println("WARN: Replacing noise levels for credit type: "+type);
		}
		UserType.noiseLevels.put(type,levels);
	}
    
}
