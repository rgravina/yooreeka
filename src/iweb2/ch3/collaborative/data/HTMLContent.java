package iweb2.ch3.collaborative.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.lucene.demo.html.HTMLParser;

import iweb2.ch3.collaborative.model.Content;

public class HTMLContent extends Content {

    /**
	 * SVUID
	 */
	private static final long serialVersionUID = -354667863913509004L;

    public HTMLContent(String id, File htmlDocFile) {
        super(id, extractContentFromHtmlDoc(htmlDocFile)); 
    }

    public HTMLContent(String id, File htmlDocFile, int topNTerms) {
        super(id, extractContentFromHtmlDoc(htmlDocFile), topNTerms); 
    }
    
	public HTMLContent(String id, String htmlDocFilename) {
        super(id, extractContentFromHtmlDoc(new File(htmlDocFilename))); 
    }

	public HTMLContent(String id, String htmlDocFilename, int topNTerms) {
        super(id, extractContentFromHtmlDoc(new File(htmlDocFilename)), topNTerms); 
    }

	
	
    private static String extractContentFromHtmlDoc(File htmlFile) {
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(htmlFile);
            Reader reader = new InputStreamReader(new BufferedInputStream(fis));
            HTMLParser htmlParser = new HTMLParser(reader);
            Reader r = htmlParser.getReader();
            BufferedReader br = new BufferedReader(r);
            String line = null;
            StringWriter sw = new StringWriter();
            while((line = br.readLine()) != null) {
                sw.write(line);
                sw.write(" ");
            }
            return sw.toString();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if( fis != null ) {
                try {
                    fis.close();
                }
                catch(IOException e) { 
                    e.printStackTrace();
                }
            }
        }
    }
    
}
