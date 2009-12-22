package iweb2.ch5.usecase.email.data;

import iweb2.ch5.classification.core.TrainingSet;
import iweb2.ch5.usecase.email.EmailInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailDataset {
    
    private Map<String, Email> emails;
    
    // By default we set up  an email dataset for binary classification
    private boolean isBinary=true;
    
    public EmailDataset(List<Email> emailList) {
        this.emails = new HashMap<String, Email>(emailList.size());
        for(Email e : emailList) {
            emails.put(e.getId(), e);
        }
    }

    public List<Email> getEmails() {
        return new ArrayList<Email>(emails.values());
    }
    
    public Email findEmailById(String id) {
        return emails.get(id);
    }
    
    public void printEmail(String id) {
        Email e = findEmailById(id);
        if( e != null ) {
            System.out.println(e.toString());
        }
        else {
            System.out.println("Email not found (email id: '" + id + "')");
        }
    }
    
    public void printAll() {
        for(Map.Entry<String, Email> e : emails.entrySet()) {
            Email email = e.getValue();
            System.out.println(email);
        }
    }

    public int getSize() {
        return emails.size();
    }
    
	/**
	 * @return the isBinary
	 */
	public boolean isBinary() {
		return isBinary;
	}

	/**
	 * @param isBinary the isBinary to set
	 */
	public void setBinary(boolean isBinary) {
		this.isBinary = isBinary;
	}

    
    public TrainingSet getTrainingSet(int topNTerms) {
        List<EmailInstance> allInstances = createEmailInstances(topNTerms);
        EmailInstance[] instances = 
            allInstances.toArray(new EmailInstance[allInstances.size()]); 
        return new TrainingSet(instances);
    }

    private List<EmailInstance> createEmailInstances(int topNTerms) {
        List<EmailInstance> allInstances = new ArrayList<EmailInstance>();
        for(Email email : getEmails()) {
            EmailInstance i = toEmailInstance(email, topNTerms);
            allInstances.add(i);
        }
        return allInstances;
    }

    public EmailInstance toEmailInstance(Email email, int topNTerms) {
        String emailCategory = getEmailCategory(email);
        return new EmailInstance(emailCategory, email, topNTerms);
    }
        
    private String getEmailCategory(Email email) {
        
    	if (isBinary()) {
	        if( email.getId().startsWith("spam-")) {
	            return "SPAM";
	        }
	        else {
	            return "NOT SPAM";
	        }
    	} else {
    		// relying id to have pattern: "biz-???", "world-???", ...
	        String[] parts = email.getId().split("-");
	        if(parts.length < 2) {
	            throw new RuntimeException(
	                    "Unsupported id format. Expected id format: '<catgory>-???'");
	        }
	        return parts[0].toUpperCase();
    	}
    }
}
