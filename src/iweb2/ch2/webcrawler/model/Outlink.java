package iweb2.ch2.webcrawler.model;


public class Outlink {

    private String linkUrl;
    private String text;
    
    public Outlink(String linkUrl, String text) {
        this.linkUrl = linkUrl;
        this.text = text;
    }
    
    public String getLinkUrl() {
        return linkUrl;
    }
    
    public String getText() {
        return text;
    }
    
    @Override
	public String toString() {
        return "[link:" + linkUrl + ", text:" + text + "]";
    }
}
