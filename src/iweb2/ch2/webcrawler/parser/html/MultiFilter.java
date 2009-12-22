package iweb2.ch2.webcrawler.parser.html;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

class MultiFilter implements NodeFilter {
    
    List<NodeFilter> acceptFilters = new ArrayList<NodeFilter>();
    
    public MultiFilter() { }

    public void addAcceptFilter(NodeFilter nestedFilter) {
        acceptFilters.add(nestedFilter);
    }
    
    public short acceptNode(Node n) {
        short result = NodeFilter.FILTER_SKIP;
        for( NodeFilter f : acceptFilters ) {
            result = f.acceptNode(n);
            if( result == NodeFilter.FILTER_ACCEPT ) {
                break;
            }
        }
        return result;
    }
    
}
