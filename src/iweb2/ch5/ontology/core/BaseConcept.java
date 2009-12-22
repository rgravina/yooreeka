package iweb2.ch5.ontology.core;

import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;

import java.util.ArrayList;

/**
 * @author babis
 *
 */
public class BaseConcept implements Concept {

	private String name;
	private BaseConcept parent;

	private ArrayList<Instance> instances = new ArrayList<Instance>();
	
	public BaseConcept(String name) {
		this.name = name;
	}
	
	public BaseConcept(String name, BaseConcept parent) {
		this.name = name;
		this.parent = parent;
	}

	public synchronized void addInstance(Instance i) {
		instances.add(i);
	}
	
	public Instance[] getInstances() {
		return instances.toArray(new Instance[instances.size()]);
	}

	public String getName() {
		return name;
	}

	public Concept getParent() {
		return parent;
	}
	
	public void setParent(BaseConcept parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
	    return name;
	}
	
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        
    	final BaseConcept other = (BaseConcept) obj;

    	if (this == obj) {
            return true;
    	}

    	if ( !(obj instanceof BaseConcept) ) {
            return false;
    	}

        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        
        if (parent == null) {
            if (other.parent != null) {
                return false;
            }
        } else if (!parent.equals(other.parent)) {
            return false;
        }
        
        return true;
    }	
	
	
}
