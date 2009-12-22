package iweb2.ch5.usecase.fraud;

import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Instance;

public class TransactionInstance implements Instance {

    public static final String ATTR_NAME_N_TXN_AMT = "n_txnamt";
    public static final String ATTR_NAME_N_LOCATION = "n_location";
    public static final String ATTR_NAME_N_DESCRIPTION = "n_description";
    public static final String ATTR_NAME_USERID = "userid";
    public static final String ATTR_NAME_TXNID = "txnid";
    public static final String ATTR_NAME_TXN_AMT = "txnamt";
    public static final String ATTR_NAME_LOCATION_X = "location_x";
    public static final String ATTR_NAME_LOCATION_Y = "location_y";
    public static final String ATTR_NAME_DESCRIPTION = "description";
    
    
    protected TransactionConcept concept;
    protected Attribute[] attributes;
    
    public TransactionInstance(TransactionConcept c, Attribute[] attrs) {
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

    public TransactionConcept getConcept() {
        return concept;
    }

    public void print() {
        if (attributes != null) {
            for (Attribute a : attributes) {
                
                if ( a == null || a.getName() == null) {
                    System.out.print(" -  <NULL ATTRIBUTE> ");
                } else {
                    if (a.getValue() == null) {
                        System.out.print(" -  <NULL ATTRIBUTE VALUE> ");
                    } else {
                        System.out.print(" -  "+a.getName()+" = "+a.getValue());
                    }
                }
            }
        }
        
        System.out.println(" -->  "+getConcept().getName());
    }
    
}
