package iweb2.ch2.webcrawler.parser.common;

import iweb2.ch2.webcrawler.model.FetchedDocument;
import iweb2.ch2.webcrawler.model.ProcessedDocument;

/**
 * Interface for parsing document that was retrieved/fetched during
 * collection phase.  
 */
public interface DocumentParser {
    public ProcessedDocument parse(FetchedDocument doc) 
        throws DocumentParserException;
}
