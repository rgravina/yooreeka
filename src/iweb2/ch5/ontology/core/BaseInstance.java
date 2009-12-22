/**
 * 
 */
package iweb2.ch5.ontology.core;

import iweb2.ch5.ontology.intf.Attribute;
import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author babis
 *
 */
public class BaseInstance implements Instance {
	
	protected Concept concept;
	protected StringAttribute[] attributes;

	public BaseInstance() {
		//DO NOTHING
	}
	
	/**
	 * @param concept
	 * @param attributes
	 */
	public BaseInstance(Concept concept, StringAttribute[] attributes) {
		this.concept = concept;
		this.attributes = attributes;
	}
	
	public Attribute[] getAtrributes() {
		return attributes;
	}

	public Concept getConcept() {
		return concept;
	}
	
	/**
	 * This method loads the training instances for the user clicks.
	 * 
	 * @param fileName the name of the file that contains the user clicks
	 * @throws IOException 
	 */
	public BaseInstance[] load(String fileName) throws IOException {

	    File           file = new File(fileName);
		FileReader  fReader = new FileReader(file);
        BufferedReader bR = new BufferedReader(fReader); 

        return load(bR);
	}
		
    public BaseInstance[] load(BufferedReader bR) throws IOException {
        
        ArrayList<BaseInstance> baseInstances = new ArrayList<BaseInstance>();
        
        String line;
        boolean hasMoreLines = true;
        
        while (hasMoreLines) {
            
            line=bR.readLine();
            
            if (line == null) {
            
                hasMoreLines = false;
            
            } else {
                
                String[] data = line.split(",");
                
                int n = data.length;
                
                StringAttribute[] attributes = new StringAttribute[n-1];
                
                for (int i=0; i<n-1;i++) {
                	attributes[i] = new StringAttribute("a-"+i,data[i]);
                }
                
                //The last value is assumed to be the class/concept
                
                baseInstances.add(new BaseInstance(new BaseConcept(data[n-1]),attributes));
            }
        }
        
        return baseInstances.toArray(new BaseInstance[baseInstances.size()]);
    }
    
    public static BaseInstance createInstance(String conceptName, String[] attrNames, String[] attrValues) {
        int n = attrNames.length;
        StringAttribute[] attributes = new StringAttribute[n];
        for (int i = 0; i < n; i++) {
            attributes[i] = new StringAttribute(attrNames[i], attrValues[i]);
        }
    
        Concept concept = new BaseConcept(conceptName);
        return new BaseInstance(concept, attributes);
    }
    
    

    /**
     * Pretty print the information for this Instance
     */
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

	/**
	 * @param concept the concept to set
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public StringAttribute getAttribute(int i) {
		return attributes[i];
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

    	final BaseInstance other = (BaseInstance) obj;

    	// Check the basics first
    	if (this == obj) {
            return true;
    	}

    	if ( (getClass() != obj.getClass()) || obj == null) {
            return false;
    	}
    	
    	// Check the concept
    	if (concept == null) {
    		if (other.concept != null) {
    			return false;
    		}
    	} else {
    		if (!concept.equals(other.concept)) {
    			return false;
    		}
    	}

    	// Finally check all the attributes
    	for (int i=0; i<attributes.length; i++) {
	        if (attributes[i] == null) {
	            if (other.attributes[i] != null) {
	                return false;
	            }
	        } else {
	        	if (!attributes[i].getName().equals(other.attributes[i].getName())) {
		            return false;
	        	} else {
	        		if (!attributes[i].getValue().equals(other.attributes[i].getValue())) {
	        			return false;
	        		}
	        	}
	        }
    	}
    	return true;
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
}
