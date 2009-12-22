package iweb2.ch2.webcrawler.parser.html;

import iweb2.ch2.webcrawler.parser.common.DocumentParserException;

public class HTMLDocumentParserException extends DocumentParserException {

    private static final long serialVersionUID = -7886897172434304778L;

    public HTMLDocumentParserException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public HTMLDocumentParserException(String msg) {
        super(msg);
    }
}
