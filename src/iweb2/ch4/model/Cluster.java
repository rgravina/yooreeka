package iweb2.ch4.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Group of data points.
 */
public class Cluster {

    private String label;
    
    
    private Set<DataPoint> elements;
    
    // Empty cluster with no elements.
    public Cluster() {
        init("");
    }

    public Cluster(String label) {
        init(label);
    }

    public Cluster(String label, Collection<DataPoint> elements) {
        init(label);
        for(DataPoint e : elements) {
            add(e);
        }
    }
    
    // New cluster that contains all elements from provided clusters.
    public Cluster(Cluster c1, Cluster c2) {
        init("");
        add(c1);
        add(c2);
    }

    public Cluster(Collection<DataPoint> elements) {
        init("");
        for(DataPoint e : elements) {
            add(e);
        }
    }
    
    public Cluster(DataPoint element) {
        init("");
        add(element);
    }
    
    
    private void init(String label) {
        this.label = label;
        elements = new LinkedHashSet<DataPoint>();
    }
    
    public String getLabel() {
        return label;
    }
    
    public int size() {
        return elements.size();
    }
    
    /*
     * Returns number of attributes used to define points in the cluster.
     */
    public int getDimensionCount() {
        if( elements == null || elements.isEmpty() ) {
            return 0;
        }
        
        return elements.iterator().next().getAttributeCount();
    }
    
    public Cluster copy() {
        Cluster copy = new Cluster();
        for(DataPoint e : this.getElements()) {
            // DataPoint is immutable. No need to create a copy.
            copy.add(e);
        }
        return copy;
    }
    
    
    /**
     * Modifies existing cluster by adding all elements from provided cluster.
     * 
     * @param c
     */
    public void add(Cluster c) {
        for(DataPoint e : c.getElements() ) {
            elements.add(e);
        }
    }
    
    /**
     * Modifies existing cluster by adding a new element.
     * 
     * @param e
     */
    public void add(DataPoint e) {
        elements.add(e);
    }
    
    public boolean contains(Cluster c) {
        boolean result = true;
        for(DataPoint e : c.getElements()) {
            if( !contains(e) ) {
                result = false;
                break;
            }
        }
        return result;
    }
    
    public boolean contains(DataPoint e) {
        return elements.contains(e);
    }
    
    public Set<DataPoint> getElements() {
        return new LinkedHashSet<DataPoint>(elements);
    }
    
    public String getElementsAsString() {
        StringBuffer buf = new StringBuffer();
        for(DataPoint e : elements) {
            if( buf.length() > 0 ) {
                buf.append(",\n");
            }
            buf.append(e.getLabel());
        }

        return "{" + buf.toString() + "}";
    }
    
    @Override
	public String toString() {
        return getElementsAsString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((elements == null) ? 0 : elements.hashCode());
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
        final Cluster other = (Cluster) obj;
        if (elements == null) {
            if (other.elements != null)
                return false;
        } else if (!elements.equals(other.elements))
            return false;
        return true;
    }

}
