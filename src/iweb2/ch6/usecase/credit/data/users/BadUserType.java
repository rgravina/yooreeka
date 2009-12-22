    /**
 * 
 */
package iweb2.ch6.usecase.credit.data.users;

public class BadUserType extends UserType {

	{
		setAge(new int[] {1, 8, 9, 10});
		setBancruptcy(new int[] { 0, 1 });
		setCarOwnership(new int[] { 0, 1});
		setCreditScore(new int[] {1, 2, 3, 4 });
		setCriminalRecord(new int[] { 0 });
		setDownPayment(new int[] { 1, 2 });
		setIncome(new int[] {3, 4, 5, 6 });
		setJobClass(new int[] { 4, 5 });
		setMotorcycleOwnership(new int[] { 0, 1});
		setPropertyOwnership(new int[] { 0 });
		setRetirementAccounts(new int[] { 1, 2 });
	}
		
    @Override
    public String getUserType() {
        return UserType.BAD;
    }
}