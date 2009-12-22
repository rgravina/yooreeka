package iweb2.ch5.usecase.fraud;

import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;

public class TransactionConcept implements Concept {

    public static final String CONCEPT_LABEL_FRAUD = "FRAUD_TXN";
    public static final String CONCEPT_LABEL_VALID = "VALID_TXN"; 
    
    private String name;
    
    public TransactionConcept(boolean isFraud) {
        if( isFraud) {
            name = CONCEPT_LABEL_FRAUD;
        }
        else {
            name = CONCEPT_LABEL_VALID;
        }
    }
    
    public TransactionConcept(String name) {
        this.name = name;
    }
    
    public Instance[] getInstances() {
        throw new UnsupportedOperationException("not implemented.");
    }

    public String getName() {
        return name;
    }

    public Concept getParent() {
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        final TransactionConcept other = (TransactionConcept) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    
}
