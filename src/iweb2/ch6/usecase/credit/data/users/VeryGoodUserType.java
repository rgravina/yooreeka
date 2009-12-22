/**
 * 
 */
package iweb2.ch6.usecase.credit.data.users;

public class VeryGoodUserType extends UserType {
    
	{
		setAge(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		setBancruptcy(new int[] { 0 });
		setCarOwnership(new int[] { 1 });
		setCreditScore(new int[] { 5, 6, 7 });
		setCriminalRecord(new int[] { 0 });
		setDownPayment(new int[] { 3, 4 });
		setIncome(new int[] { 4, 5, 6, 7 });
		setJobClass(new int[] {2, 3, 4, 5});
		setMotorcycleOwnership(new int[] { 0, 1});
		setPropertyOwnership(new int[] { 1 });
		setRetirementAccounts(new int[] { 3, 4, 5 });
	}

	@Override
	public String getUserType() {
        return UserType.VERY_GOOD;
    }
}