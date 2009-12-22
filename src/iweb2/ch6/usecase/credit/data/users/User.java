package iweb2.ch6.usecase.credit.data.users;

public class User {

    private String username;
    private int jobClass;
    private int carOwnership;
    private int bicycleOwnership;
    private int propertyOwnership;
    private int retirementAccount;
    private int creditScore;
    private int age;
    private int downPayment;
    private int bankruptcy;
    private int criminalRecord;
    private int income;
    
 
    public User() {
        // empty
    }
    
    public String getCategory() {
        return username.substring(0,2);
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    /**
	 * @return the jobClass
	 */
	public int getJobClass() {
		return jobClass;
	}

	/**
	 * @param jobClass the jobClass to set
	 */
	public void setJobClass(int jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * @return the carOwnership
	 */
	public int getCarOwnership() {
		return carOwnership;
	}

	/**
	 * @param carOwnership the carOwnership to set
	 */
	public void setCarOwnership(int carOwnership) {
		this.carOwnership = carOwnership;
	}

	/**
	 * @return the bicycleOwnership
	 */
	public int getBicycleOwnership() {
		return bicycleOwnership;
	}

	/**
	 * @param bicycleOwnership the bicycleOwnership to set
	 */
	public void setBicycleOwnership(int bicycleOwnership) {
		this.bicycleOwnership = bicycleOwnership;
	}

	/**
	 * @return the propertyOwnership
	 */
	public int getPropertyOwnership() {
		return propertyOwnership;
	}

	/**
	 * @param propertyOwnership the propertyOwnership to set
	 */
	public void setPropertyOwnership(int propertyOwnership) {
		this.propertyOwnership = propertyOwnership;
	}

	/**
	 * @return the retirementAccount
	 */
	public int getRetirementAccount() {
		return retirementAccount;
	}

	/**
	 * @param retirementAccount the retirementAccount to set
	 */
	public void setRetirementAccount(int retirementAccount) {
		this.retirementAccount = retirementAccount;
	}

	/**
	 * @return the creditScore
	 */
	public int getCreditScore() {
		return creditScore;
	}

	/**
	 * @param creditScore the creditScore to set
	 */
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the downPayment
	 */
	public int getDownPayment() {
		return downPayment;
	}

	/**
	 * @param downPayment the downPayment to set
	 */
	public void setDownPayment(int downPayment) {
		this.downPayment = downPayment;
	}

	/**
	 * @return the bankruptcy
	 */
	public int getBankruptcy() {
		return bankruptcy;
	}

	/**
	 * @param bankruptcy the bankruptcy to set
	 */
	public void setBankruptcy(int bankruptcy) {
		this.bankruptcy = bankruptcy;
	}

	/**
	 * @return the criminalRecord
	 */
	public int getCriminalRecord() {
		return criminalRecord;
	}

	/**
	 * @param criminalRecord the criminalRecord to set
	 */
	public void setCriminalRecord(int criminalRecord) {
		this.criminalRecord = criminalRecord;
	}

	/**
	 * @return the income
	 */
	public int getIncome() {
		return income;
	}

	/**
	 * @param income the income to set
	 */
	public void setIncome(int incomeType) {
		this.income = incomeType;
	}

	public String toExternalString() {
        return username +
        ":" + jobClass +
        ":" + carOwnership + 
        ":" + bicycleOwnership + 
        ":" + propertyOwnership + 
        ":" + retirementAccount + 
        ":" + creditScore +
        ":" + age +
        ":" + downPayment +
        ":" + bankruptcy +
        ":" + criminalRecord +
        ":" + income;
    }
    
    @Override
	public String toString() {
        return toExternalString();
    }

    public void loadFromExternalString(String text) {

        String[] values = text.split(":");
        
        username = values[0];
        jobClass = Integer.parseInt(values[1]);
        carOwnership = Integer.parseInt(values[2]); 
        bicycleOwnership = Integer.parseInt(values[3]); 
        propertyOwnership = Integer.parseInt(values[4]); 
        retirementAccount = Integer.parseInt(values[5]);
        creditScore = Integer.parseInt(values[6]);
        age = Integer.parseInt(values[7]);
        downPayment = Integer.parseInt(values[8]);
        bankruptcy = Integer.parseInt(values[9]);
        criminalRecord = Integer.parseInt(values[10]);
        income = Integer.parseInt(values[11]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + carOwnership;
        result = prime * result + creditScore;
        result = prime * result + income;
        result = prime * result + jobClass;
        result = prime * result + downPayment;
        result = prime * result + bicycleOwnership;
        result = prime * result + propertyOwnership;
        result = prime * result + criminalRecord;
        result = prime * result + bankruptcy;
        result = prime * result + retirementAccount;
        result = prime * result
                + ((username == null) ? 0 : username.hashCode());
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
        final User other = (User) obj;
        if (age != other.age)
            return false;
        if (carOwnership != other.carOwnership)
            return false;
        if (creditScore != other.creditScore)
            return false;
        if (income != other.income)
            return false;
        if (jobClass != other.jobClass)
            return false;
        if (downPayment != other.downPayment)
            return false;
        if (bicycleOwnership != other.bicycleOwnership)
            return false;
        if (propertyOwnership != other.propertyOwnership)
            return false;
        if (criminalRecord != other.criminalRecord)
            return false;
        if (bankruptcy != other.bankruptcy)
            return false;
        if (retirementAccount != other.retirementAccount)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
    
    
}
