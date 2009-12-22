/**
 * 
 */
package iweb2.ch6.usecase.credit.data.users;

public class GoodUserType extends UserType {

	{
		setAge(new int[] { 2, 3, 4, 5, 6, 7, 8 });
		setBancruptcy(new int[] { 0 });
		setCarOwnership(new int[] { 1 });
		setCreditScore(new int[] { 3, 4, 5, 6 });
		setCriminalRecord(new int[] { 0 });
		setDownPayment(new int[] { 2, 3 });
		setIncome(new int[] { 5, 6, 7, 8 });
		setJobClass(new int[] {2, 3, 4, 5});
		setMotorcycleOwnership(new int[] { 0, 1});
		setPropertyOwnership(new int[] { 0, 1 });
		setRetirementAccounts(new int[] { 1, 2, 3, 4 });
	}
	
    @Override
    public String getUserType() {
        return UserType.GOOD;
    }
}