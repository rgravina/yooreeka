package iweb2.ch3.content.digg;

import iweb2.ch3.collaborative.model.Content;
import iweb2.ch3.collaborative.model.Item;

public class DiggStoryItem extends Item {

    /**
     * SVUID
     */
    private static final long serialVersionUID = 1924555535749825404L;
    
    private String link;
    private String description;
    private String topic;
    private String username;
    private String category;
    
    public DiggStoryItem(int storyId, String title, String description) {
        super(storyId, title);
        this.description = description;
        String text = title + " " + description;
        Content content = new Content(String.valueOf(storyId), text);
        setItemContent(content);
    }
    
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return getName();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public void print() {
    	System.out.println("---------------------------------------------------------------------");
    	System.out.println("Category: "+this.getCategory()+"     -- NewsCategory: "+this.getTopic());
    	System.out.println("Title: "+this.getTitle());
    	System.out.println("_____________________________________________________________________");
    	System.out.println("Description:\n"+this.getDescription());
    	System.out.println("_____________________________________________________________________");
    }
}
