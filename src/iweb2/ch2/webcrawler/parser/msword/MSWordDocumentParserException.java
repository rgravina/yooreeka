package iweb2.ch2.webcrawler.parser.msword;

import iweb2.ch2.webcrawler.parser.common.DocumentParserException;

public class MSWordDocumentParserException extends DocumentParserException {

    private static final long serialVersionUID = -7886897172434304778L;

    public MSWordDocumentParserException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public MSWordDocumentParserException(String msg) {
        super(msg);
    }
}
