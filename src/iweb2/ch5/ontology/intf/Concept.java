/**
 * 
 */
package iweb2.ch5.ontology.intf;

/**
 * @author babis
 *
 */
public interface Concept {

	public String getName();
	
	public Concept getParent();
	
	public Instance[] getInstances();
}
