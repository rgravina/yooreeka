/**
 * 
 */
package iweb2.ch3.collaborative.data;

import iweb2.ch3.collaborative.model.Content;
import iweb2.ch3.collaborative.model.Item;
import iweb2.ch3.collaborative.model.Rating;

import java.util.ArrayList;

/**
 * Item for news dataset.
 */
public class ContentItem extends Item {
	
	/**
     * SVUID
     */
    private static final long serialVersionUID = 6349342365379966975L;
	
	public ContentItem(int id, String name, Content content) {
		super(id, name, new ArrayList<Rating>(3));
		setItemContent(content);
	}

	public ContentItem(Item val) {
		super(val.getId(),val.getName(),new ArrayList<Rating>(3));
		this.setItemContent(val.getItemContent());
	}

}
