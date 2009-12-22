package iweb2.ch2.webcrawler.parser.html;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

/**
 * Filter for nodes that are elements with specified name and 
 * attribute.
 */
class ElementNodeFilter implements NodeFilter {
    private String elementName = null;
    private String attributeName = null;
    
    public ElementNodeFilter(String elementName, String attributeName) {
        this.elementName = elementName;
        this.attributeName = attributeName;
    }
    
    public short acceptNode(Node n) {
        short result = FILTER_SKIP;
        if( Node.ELEMENT_NODE == n.getNodeType() ) {
            Element e = (Element)n;
            if( e.getNodeName().equalsIgnoreCase(elementName) ) {
                if( e.getAttributeNode(attributeName) != null ) {
                    result = FILTER_ACCEPT;
                }
            }
        }
        return result;
    }
}
