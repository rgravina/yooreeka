package iweb2.ch5.classification.dtree;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows us to associate type with the attribute.
 */
public class AttributeDefinition implements java.io.Serializable {

    private static final long serialVersionUID = -8446442452030956318L;
    
    /*
     * Attribute name
     */
    private String name;
    
    /*
     * Attribute can be described as continuous (has numeric values) or 
     * discrete (has nominal/categorical values).
     */ 
    private boolean isDiscrete;
    
    public AttributeDefinition(String name, boolean isDiscrete) {
        this.name = name;
        this.isDiscrete = isDiscrete;
    }
    
    public String getName() {
        return name;
    }
    public boolean isDiscrete() {
        return isDiscrete;
    }
    
    /**
     * Removes attribute definition with specified name from the list.
     * 
     * @param attrName attribute name to remove.
     * @param attributes list to remove from.
     */
    public static void removeAttributeDef(String attrName, 
            List<AttributeDefinition> attributes) {

        if( attrName != null ) {
            for(int i = 0, n = attributes.size(); i < n; i++ ) {
                AttributeDefinition a = attributes.get(i);
                if( attrName.equalsIgnoreCase(a.getName()) ) {
                    attributes.remove(i);
                    break;
                }
            }
        }
    }
    
    /**
     * Creates a copy of attribute definitions.
     * 
     * @param attrs original list of attributes.
     * @return new list.
     */
    public static List<AttributeDefinition> copyAttributeDefs(
            List<AttributeDefinition> attrs) {
        return new ArrayList<AttributeDefinition>(attrs);
    }
}
