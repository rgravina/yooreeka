package iweb2.ch2.webcrawler.parser.common;

public class DocumentParserException extends Exception {

    private static final long serialVersionUID = -813855279137688316L;

    public DocumentParserException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public DocumentParserException(String msg) {
        super(msg);
    }
}
