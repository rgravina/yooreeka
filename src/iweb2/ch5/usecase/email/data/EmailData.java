package iweb2.ch5.usecase.email.data;

import iweb2.ch2.webcrawler.model.ProcessedDocument;
import iweb2.ch2.webcrawler.parser.html.HTMLDocumentParser;
import iweb2.ch5.usecase.email.EmailClassifier;
import iweb2.util.config.IWeb2Config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class EmailData {

    
    /*
     * List of html files that we will treat as emails.
     */
    public static String[][] TRAINING_DATA = new String[][] {
        {IWeb2Config.getHome()+"/data/ch02/biz-02.html", "A@sengerhost", "1@host"},
        {IWeb2Config.getHome()+"/data/ch02/biz-03.html", "B@sengerhost", "2@host"},
        {IWeb2Config.getHome()+"/data/ch02/biz-04.html", "C@sengerhost", "3@host"},
        {IWeb2Config.getHome()+"/data/ch02/biz-05.html", "D@sengerhost", "4@host"},
        {IWeb2Config.getHome()+"/data/ch02/biz-06.html", "E@sengerhost", "5@host"},
        {IWeb2Config.getHome()+"/data/ch02/biz-07.html", "F@sengerhost", "6@host"},
        {IWeb2Config.getHome()+"/data/ch02/sport-02.html", "G@sengerhost", "7@host"},
        {IWeb2Config.getHome()+"/data/ch02/sport-03.html", "H@sengerhost", "8@host"},
        {IWeb2Config.getHome()+"/data/ch02/usa-02.html", "I@sengerhost", "9@host"},
        {IWeb2Config.getHome()+"/data/ch02/usa-03.html", "J@sengerhost", "10@host"},
        {IWeb2Config.getHome()+"/data/ch02/usa-04.html", "K@sengerhost", "11@host"},
        {IWeb2Config.getHome()+"/data/ch02/world-02.html", "L@sengerhost", "12@host"},
        {IWeb2Config.getHome()+"/data/ch02/world-03.html", "M@sengerhost", "13@host"},
        {IWeb2Config.getHome()+"/data/ch02/world-04.html", "N@sengerhost", "14@host"},
        {IWeb2Config.getHome()+"/data/ch02/world-05.html", "O@sengerhost", "15@host"},
        {IWeb2Config.getHome()+"/data/ch02/spam-biz-02.html", "P@sengerhost", "16@host"},
        {IWeb2Config.getHome()+"/data/ch02/spam-biz-03.html", "Q@sengerhost", "17@host"}
    };

    public static String[][] TEST_DATA = new String[][] {
        {IWeb2Config.getHome()+"/data/ch02/biz-01.html", "aa@senderhost", "100@host"},
        {IWeb2Config.getHome()+"/data/ch02/sport-01.html", "bb@senderhost","101@host"},
        {IWeb2Config.getHome()+"/data/ch02/usa-01.html", "cc@senderhost", "102@host"},
        {IWeb2Config.getHome()+"/data/ch02/world-01.html", "dd@senderhost", "103@host"},
        {IWeb2Config.getHome()+"/data/ch02/spam-biz-01.html", "friend@senderhost", "104@host"}
    };
    
    public static List<Email> loadEmails(String[][] allEmails) {
        
        List<Email> emailList = new ArrayList<Email>();
        for(String[] emailData : allEmails) {
            String filename = emailData[0];
            Email email = loadEmailFromHtml(filename);
            email.setFrom(emailData[1]);
            email.setTo(emailData[2]);
            // use filename as unique id
            String id = filename.substring(filename.lastIndexOf("/") + 1);
            email.setId(id);
            
            emailList.add(email);
        }
        
        return emailList;
    }
    
    public static EmailDataset createTrainingDataset() {
        List<Email> allEmails = loadEmails(TRAINING_DATA);
        return new EmailDataset(allEmails);
    }

    public static EmailDataset createTestDataset() {
        List<Email> allEmails = loadEmails(TEST_DATA);
        return new EmailDataset(allEmails);
    }
    
    public static Email loadEmailFromHtml(String htmlFile) {
        
        ProcessedDocument htmlDoc = processHtmlDoc(htmlFile);
        Email email = new Email();
        email.setSubject(htmlDoc.getDocumentTitle());
        email.setTextBody(htmlDoc.getText());
        
        return email;
    }
    
    private static ProcessedDocument processHtmlDoc(String htmlFile) {

        ProcessedDocument doc = null;
        try {
            HTMLDocumentParser htmlParser = new HTMLDocumentParser();            
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream(htmlFile));
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            doc = htmlParser.parse(reader);
        } 
        catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse html from file: " + htmlFile, e);
        }
        
        return doc;
    }
    
    
    public static void main(String[] args) {
//        // Create and train classifier
//        EmailDataset trainEmailDS = EmailData.createTrainingDataset();
//        EmailClassifier emailClassifier = new EmailClassifier(trainEmailDS, 10);
//        emailClassifier.train();
//        
//        // Let's classify some emails from training set. If we can't get them right
//        // then we are in trouble :-)
//        Email email = null;
//        email = trainEmailDS.findEmailById("biz-04.html");
//        emailClassifier.classify(email);
//        
//        email = trainEmailDS.findEmailById("usa-03.html");
//        emailClassifier.classify(email);
//        
//        // Now, let's classify previously unseen emails
//        
//        EmailDataset testEmailDS = EmailData.createTestDataset();
//        email = testEmailDS.findEmailById("biz-01.html");
//        emailClassifier.classify(email);
//        
//        email = testEmailDS.findEmailById("sport-01.html");
//        emailClassifier.classify(email);
//        
//        email = testEmailDS.findEmailById("usa-01.html");
//        emailClassifier.classify(email);
//        
//        email = testEmailDS.findEmailById("world-01.html");
//        emailClassifier.classify(email);
//        
//        email = testEmailDS.findEmailById("spam-biz-01.html");
//        emailClassifier.classify(email);
        
        
      // Create and train classifier
      EmailDataset trainEmailDS = EmailData.createTrainingDataset();
      EmailClassifier spamFilter = new EmailClassifier(trainEmailDS, 10);
      spamFilter.train();
      
      // Let's classify some emails from training set. If we can't get them right
      // then we are in trouble :-)
      Email email = null;
      email = trainEmailDS.findEmailById("biz-04.html");
      spamFilter.classify(email);
      
      email = trainEmailDS.findEmailById("usa-03.html");
      spamFilter.classify(email);
      
      // Now, let's classify previously unseen emails
      
      EmailDataset testEmailDS = EmailData.createTestDataset();
      email = testEmailDS.findEmailById("biz-01.html");
      spamFilter.classify(email);
      
      email = testEmailDS.findEmailById("sport-01.html");
      spamFilter.classify(email);
      
      email = testEmailDS.findEmailById("usa-01.html");
      spamFilter.classify(email);
      
      email = testEmailDS.findEmailById("world-01.html");
      spamFilter.classify(email);
      
      email = testEmailDS.findEmailById("spam-biz-01.html");
      spamFilter.classify(email);
        
    }
}
