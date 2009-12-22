package iweb2.ch6.usecase.credit;

import iweb2.ch5.ontology.core.BaseConcept;
import iweb2.ch5.ontology.intf.Instance;
import iweb2.ch6.usecase.credit.data.users.UserType;

public class CreditConcept extends BaseConcept {

    public static final String CONCEPT_LABEL_EX = UserType.EXCELLENT;
    public static final String CONCEPT_LABEL_VG = UserType.VERY_GOOD;
    public static final String CONCEPT_LABEL_GD = UserType.GOOD;
    public static final String CONCEPT_LABEL_BD = UserType.BAD;
    public static final String CONCEPT_LABEL_DN = UserType.DANGEROUS;
    
    public CreditConcept(String name) {
        super(name);
    }
    
    @Override
	public Instance[] getInstances() {
        throw new UnsupportedOperationException("not implemented.");
    }

    public static int getIndex(String val) {
    	int index=-1; 
    	if (val.equals(CONCEPT_LABEL_EX)) {
    		index=0;
    	} else if (val.equals(CONCEPT_LABEL_VG)) {
    		index=1;
    	} else if (val.equals(CONCEPT_LABEL_GD)) {
    		index=2;
    	} else if (val.equals(CONCEPT_LABEL_BD)) {
    		index=3;
    	} else if (val.equals(CONCEPT_LABEL_DN)) {
    		index=4;
    	} else {
    		throw new IllegalArgumentException("Unknown CreditConcept name!");
    	}
    	return index;
    }
    
    public static String getLabel(int val) {

    	String label=null;
    	
    	if (val == 0) {
    		label = CONCEPT_LABEL_EX;
    	} else if (val == 1) {
    		label = CONCEPT_LABEL_VG;
    	} else if (val == 2) {
    		label = CONCEPT_LABEL_GD;
    	} else if (val == 3) {
    		label = CONCEPT_LABEL_BD;
    	} else if (val == 4) {
    		label = CONCEPT_LABEL_DN;
    	} else {
    		throw new IllegalArgumentException("Unknown CreditConcept index for label!");
    	}
    	return label;
    }
}
