package iweb2.ch6.usecase.credit.data.users;



public class DangerousUserType extends UserType {

	{
		setAge(new int[] {1, 2, 9, 10});
		setBancruptcy(new int[] { 1 });
		setCarOwnership(new int[] { 0 });
		setCreditScore(new int[] {1, 2 });
		setCriminalRecord(new int[] { 1 });
		setDownPayment(new int[] { 1, 2 });
		setIncome(new int[] {1, 2, 3 });
		setJobClass(new int[] { 4, 5 });
		setMotorcycleOwnership(new int[] { 0 });
		setPropertyOwnership(new int[] { 0 });
		setRetirementAccounts(new int[] { 1 });
	}
	
    @Override
    public String getUserType() {
        return UserType.DANGEROUS;
    }    
}
