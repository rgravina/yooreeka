package iweb2.ch4.model;


import iweb2.ch4.similarity.EuclideanDistance;
import iweb2.ch4.utils.Attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Thing to be clustered. Defined by a set of attributes.
 */
public class DataPoint {

    /**
     * Descriptive label or name. We also use it as unique ID for the instance. 
     */
    private String label;
    
    /**
     * Collection of attributes that define this point.
     */
    private Attribute[] attributes;
    
    /*
     * Values derived from attributes.
     */
    private String[] attributeNames;  
    private double[] numericAttributeValues;
    private String[] textAttributeValues;
    
    /** 
     * Creates a new point with numerical attributes. Attribute names are
     * auto-generated. 
     */
    public DataPoint(String label, double[] attrValues) {
        // create attributes with auto-generated names
        init(label, Attributes.createAttributes(attrValues));
    }
    
    public DataPoint(String label, String[] attrValues) {
     // create attributes with auto-generated names
        init(label, Attributes.createAttributes(attrValues));
    }

    public DataPoint(String label, Attribute[] attributes) {
        init(label, attributes);        
    }
    
    private void init(String label, Attribute[] attributes) {
        this.label = label;
        this.attributes = attributes;
        this.attributeNames = Attributes.getNames(attributes);
        if( Attributes.allText(attributes) ) {
            this.textAttributeValues = Attributes.getTextValues(attributes);    
        }
        else {
            this.textAttributeValues = null;
        }
        if( Attributes.allNumeric(attributes) ) {
            this.numericAttributeValues = Attributes.getNumericValues(attributes);
        }
        else {
            this.numericAttributeValues = null;
        }
    }
    
    public int getAttributeCount() {
        return numericAttributeValues.length;
    }
    
    public Attribute[] getAttributes() {
        return attributes;
    }

    public String[] getAttributeNames() {
        return attributeNames;
    }
    
    public String[] getTextAttrValues() {
        return textAttributeValues;
    }
    
    public double[] getNumericAttrValues() {
        return numericAttributeValues;
    }
    
    
    public String getLabel() {
        return label;
    }

    @Override
	public String toString() {
    	return label + "(" + Arrays.toString(attributes) + ")";  
    }
    
    public String toShortString() {
        List<String> attrValues = new ArrayList<String>();
        for(Attribute a : attributes) {
            if( a.isNumeric() ) {
                attrValues.add(String.valueOf(a.getNumericValue()));
            }
            else {
                attrValues.add(a.getTextValue());
            }
        }
        return label + "(" + attrValues.toString() + ")"; 
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(attributes);
        result = prime * result + ((label == null) ? 0 : label.hashCode());
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
        final DataPoint other = (DataPoint) obj;
        if (!Arrays.equals(attributes, other.attributes))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }

	public double getR() {

		EuclideanDistance euclid = new EuclideanDistance();
		
		int n = attributes.length;
		
		double[] x = new double[n];
		
		for (int i=0; i < n; i++) {
			x[i] = 0d;
		}
		
		return euclid.getDistance(x, this.numericAttributeValues);		
	}
    
    
}
