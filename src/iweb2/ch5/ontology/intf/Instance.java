/**
 * 
 */
package iweb2.ch5.ontology.intf;

/**
 * @author babis
 *
 */
public interface Instance {

	public Attribute[] getAtrributes();
	
	public Concept getConcept();
	
	public void print();
	
	public Attribute getAttributeByName(String attrName); 
}
