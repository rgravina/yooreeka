package iweb2.ch6.usecase.credit;

import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Instance;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class CreditInstance implements Instance {

    public static final String ATTR_NAME_USERID = "userid";
    public static final String ATTR_NAME_JOB_CLASS = "jobClass";
    public static final String ATTR_NAME_INCOME_TYPE = "incomeType";
    public static final String ATTR_NAME_CAR_OWNERSHIP = "carOwnership";
    public static final String ATTR_NAME_MOTOR_BICYCLE_OWNERSHIP = "motorBicycleOwnership";
    public static final String ATTR_NAME_OTHER_PROPERTY_OWNERSHIP  = "otherPropertyOwnership";
    public static final String ATTR_NAME_RETIREMENT_ACCOUNT = "retirementAccount";
    public static final String ATTR_NAME_CREDIT_SCORE = "creditScore";
    public static final String ATTR_NAME_AGE = "age";
    public static final String ATTR_NAME_MORTGAGE_DOWN_PAYMENT = "mortgageDownPayment";
    public static final String ATTR_NAME_BANKRUPTCY = "priorDeclaredBankruptcy";
    public static final String ATTR_NAME_CRIMINAL_RECORD = "priorCriminalRecord";
    
    protected CreditConcept concept;
    protected Attribute[] attributes;
    
    public CreditInstance(CreditConcept c, List<Attribute> attrs) {
        this(c, attrs.toArray(new Attribute[attrs.size()]));
    }
    
    public CreditInstance(CreditConcept c, Attribute[] attrs) {
        this.concept = c;
        this.attributes = attrs;
    }

    public Attribute[] getAtrributes() {
        return attributes;
    }

    public Attribute getAttributeByName(String attrName) {
        Attribute matchedAttribute = null;
        
        if( attributes != null ) {
            for(Attribute a : attributes) {
                if( attrName.equalsIgnoreCase(a.getName())) {
                    matchedAttribute = a;
                    break;
                }
            }
        }
        
        return matchedAttribute;
    }

    public CreditConcept getConcept() {
        return concept;
    }

    @Override
	public String toString() {
        StringWriter sw = new StringWriter();
        print(new PrintWriter(sw));
        return sw.toString();
    }
    
    public void print(PrintWriter writer) {
        if (attributes != null) {
            for (Attribute a : attributes) {
                
                if ( a == null || a.getName() == null) {
                    writer.print(" -  <NULL ATTRIBUTE> ");
                } else {
                    if (a.getValue() == null) {
                        writer.print(" -  <NULL ATTRIBUTE VALUE> ");
                    } else {
                        writer.print(" -  "+a.getName()+" = "+a.getValue());
                    }
                }
            }
        }
        
        writer.println(" -->  "+getConcept().getName());
    }
    
    public void print() {
        print(new PrintWriter(System.out) );
    }
    
}
