package iweb2.ch6.usecase.credit.util;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.ontology.core.DoubleAttribute;
import iweb2.ch5.ontology.core.StringAttribute;
import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Instance;
import iweb2.ch6.usecase.credit.CreditConcept;
import iweb2.ch6.usecase.credit.CreditInstance;
import iweb2.ch6.usecase.credit.data.UserDataset;
import iweb2.ch6.usecase.credit.data.users.User;

import java.util.ArrayList;
import java.util.List;

public class UserInstanceBuilder {

    private boolean useDoubleAttributes;
    
    public UserInstanceBuilder() {
        this( false );
    }

    /**
     * 
     * @param useDoubleAttributes determines whether instance builder should
     * produce instances with string attributes or double attributes.
     */
    public UserInstanceBuilder(boolean useDoubleAttributes) {

        this.useDoubleAttributes = useDoubleAttributes;
    }

    public TrainingSet createTrainingSet(UserDataset ds) {
        List<User> users = ds.getUsers();
        int nUsers = users.size();
        Instance[] instances = new Instance[nUsers];
        for(int i = 0; i < nUsers; i++) {
            User u = users.get(i);
            instances[i] = createInstance(u);
        }
        
        TrainingSet tS = new TrainingSet(instances);
        
        return tS;
    }
    
    public Instance createInstance(Instance i) {
        if( useDoubleAttributes ) {
            return convertToDoubleAttributes(i);
        }
        else {
            return i;
        }
    }
    
    public Instance createInstance(User u) {
        List<Attribute> attributes = new ArrayList<Attribute>();

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_JOB_CLASS, 
                String.valueOf(u.getJobClass())));
        
        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_INCOME_TYPE, 
                String.valueOf(u.getIncome())));
        
        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_AGE, 
                String.valueOf(u.getAge())));

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_CAR_OWNERSHIP, 
                String.valueOf(u.getCarOwnership())));

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_CREDIT_SCORE, 
                String.valueOf(u.getCreditScore())));

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_MORTGAGE_DOWN_PAYMENT, 
                String.valueOf(u.getDownPayment())));
        
        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_MOTOR_BICYCLE_OWNERSHIP, 
                String.valueOf(u.getBicycleOwnership())));

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_OTHER_PROPERTY_OWNERSHIP, 
                String.valueOf(u.getPropertyOwnership())));

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_CRIMINAL_RECORD, 
                String.valueOf(u.getCriminalRecord())));

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_BANKRUPTCY, 
                String.valueOf(u.getBankruptcy())));

        attributes.add(new StringAttribute(
                CreditInstance.ATTR_NAME_RETIREMENT_ACCOUNT, 
                String.valueOf(u.getRetirementAccount())));
        
        
        CreditConcept c = new CreditConcept(u.getCategory());

        CreditInstance instance = new CreditInstance(c, attributes);
        
        return createInstance(instance);
    }
    
    private CreditInstance convertToDoubleAttributes(Instance instance) {
        
        CreditInstance creditInstance = (CreditInstance) instance;
        
        List<Attribute> attributes = new ArrayList<Attribute>();

        for(Attribute a : creditInstance.getAtrributes()) {
            DoubleAttribute da = null;
            if( a instanceof StringAttribute ) {
                String name = a.getName();
                double value = Double.valueOf((String)a.getValue());
                // double normalizedValue = value;
                double normalizedValue =   
                    AttributeUtils.getNormalizedValue(name, value);
                da = new DoubleAttribute(name, normalizedValue);
            }
            else if( a instanceof DoubleAttribute ){
                da = (DoubleAttribute) a;
            }
            else {
                throw new RuntimeException("Unexpected attribute type: " + 
                        a.getClass().getSimpleName() +
                        ", attribute name: " + a.getName() + 
                        ", attribute value: " + a.getValue());
            }
            
            attributes.add(da);
        }

        return new CreditInstance(creditInstance.getConcept(), attributes);
    }
    
}
