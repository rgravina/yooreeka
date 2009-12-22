package iweb2.ch5.usecase.email.data;

/**
 * Represents one email document.  
 */
public class Email {

    /*
     * ID that we will use to identify email.
     */
    private String id;
    
    /*
     * Email subject line
     */
    private String subject;
    
    /*
     * Email Text body
     */
    private String textBody;

    
    private String from;
    
    private String to;
    
    
    public Email() {
        // empty
    }
    
    
    
    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }



    public String getSubject() {
        return subject;
    }



    public void setSubject(String subject) {
        this.subject = subject;
    }



    public String getTextBody() {
        return textBody;
    }



    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }



    public String getFrom() {
        return from;
    }



    public void setFrom(String from) {
        this.from = from;
    }



    public String getTo() {
        return to;
    }



    public void setTo(String to) {
        this.to = to;
    }


    int ruleFired = 0;
    
    public int getRuleFired() {
        return ruleFired;
    }

    public void setRuleFired(int ruleNum) {
        System.out.println("Invoked " + this.getClass().getSimpleName() + ".setRuleFired(" + ruleNum + "), current value ruleFired=" + this.ruleFired + ", emailId: " + id );        
        this.ruleFired = ruleNum;
    }



    @Override
	public String toString() {
        return "id: " + id + "\n" + 
               "from: " + from + "\n" +
               "to: " + to + "\n" +
               "subject: " + subject + "\n" + 
               textBody + "\n";
    }
}
