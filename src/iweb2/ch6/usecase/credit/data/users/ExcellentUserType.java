/**
 * 
 */
package iweb2.ch6.usecase.credit.data.users;

public class ExcellentUserType extends UserType {

	{
		setAge(new int[] {1, 8, 9, 10});                 
		setBancruptcy(new int[] { 0 });                  
		setCarOwnership(new int[] { 1 });                
		setCreditScore(new int[] { 6, 7, 8});            
		setCriminalRecord(new int[] { 0 });              
		setDownPayment(new int[] { 4 });                 
		setIncome(new int[] { 7, 8, 9, 10 });            
		setJobClass(new int[] {2, 3, 4, 5});             
		setMotorcycleOwnership(new int[] { 0, 1});       
		setPropertyOwnership(new int[] { 1 });           
		setRetirementAccounts(new int[] { 5, 6, 7, 8 }); 
	}
	
    @Override
	public String getUserType() {
        return UserType.EXCELLENT;
    }
}