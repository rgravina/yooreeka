package iweb2.ch5.usecase.email;

import iweb2.ch3.collaborative.model.Content;
import iweb2.ch5.ontology.core.BaseConcept;
import iweb2.ch5.ontology.core.BaseInstance;
import iweb2.ch5.ontology.core.StringAttribute;
import iweb2.ch5.usecase.email.data.Email;

import java.util.Map;

/**
 * Instance for classification.
 */
public class EmailInstance extends BaseInstance {
    
    private static int DEFAULT_TOP_N_TERMS = 10;
    
    private String id;
    
    public EmailInstance(String emailCategory, Email email) {
        this(emailCategory, email, DEFAULT_TOP_N_TERMS);
    }
    
    public EmailInstance(String emailCategory, Email email, int topNTerms) {
        super();
        this.id = email.getId();
        // email category is our concept/class
        this.setConcept(new BaseConcept(emailCategory));
        
        /**
         * TODO: 5.3 -- Considering more attributes as part of the EmailInstance
         * 
         *   -- Separate "subject" and "body"
         *   -- timestamp
         *   -- "from"
         *   -- "to"
         *   -- "to" cardinality
         */
        // extract top N terms from email content and subject
        String text = email.getSubject() + " " + email.getTextBody(); 
        Content content = new Content(email.getId(), text, topNTerms);
        Map<String, Integer> tfMap = content.getTFMap();
        
        attributes = new StringAttribute[1];
        
        String attrName = "Email_Text_Attribute";
        String attrValue = "";
        for(Map.Entry<String, Integer> tfEntry : tfMap.entrySet()) {
            attrValue = attrValue + " " + tfEntry.getKey();
        }
        attributes[0] = new StringAttribute(attrName, attrValue);
    }
    
    @Override
	public String toString() {
        return id;
    }
    
}
