package iweb2.ch2.webcrawler.parser.msword;


import iweb2.ch2.webcrawler.model.FetchedDocument;
import iweb2.ch2.webcrawler.model.ProcessedDocument;
import iweb2.ch2.webcrawler.parser.common.DocumentParser;
import iweb2.ch2.webcrawler.parser.common.DocumentParserException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.textmining.extraction.TextExtractor;
import org.textmining.extraction.word.WordTextExtractorFactory;

public class MSWordDocumentParser implements DocumentParser {

    public ProcessedDocument parse(FetchedDocument doc)
            throws DocumentParserException {

        ProcessedDocument wordDoc = new ProcessedDocument();
        wordDoc.setDocumentType(ProcessedDocument.DOCUMENT_TYPE_MSWORD);
        wordDoc.setDocumentId(doc.getDocumentId());
        wordDoc.setDocumentURL(doc.getDocumentURL());
        
        InputStream contentData = new ByteArrayInputStream(doc.getDocumentContent());
        WordTextExtractorFactory wteFactory = new WordTextExtractorFactory();        

        try {
            TextExtractor txtExtractor = wteFactory.textExtractor(contentData);
            String text = txtExtractor.getText();
            wordDoc.setText(text);
            // using the same value as text
            wordDoc.setContent(text);
            wordDoc.setDocumentTitle(getTitle(text));
        }
        catch(Exception e) {
            throw new MSWordDocumentParserException("MSWord Document parsing error: ", e);
        }
        return wordDoc;
    }
    
    /*
     * Finds the first non-empty line in the document.
     */
    private String getTitle(String text) throws IOException {
        if( text == null ) {
            return null;
        }
        String title = "";
        
        StringReader sr = new StringReader(text);
        BufferedReader r = new BufferedReader(sr);
        String line = null;
        while( (line = r.readLine()) != null ) {
            if( line.trim().length() > 0) {
                title = line.trim();
                break;
            }
        }
        
        return title;
    }
}
