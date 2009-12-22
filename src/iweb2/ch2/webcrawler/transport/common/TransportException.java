package iweb2.ch2.webcrawler.transport.common;


public class TransportException extends Exception {

    private static final long serialVersionUID = 8286186263433948253L;

    public TransportException(String message, Throwable t) {
        super(message, t);
    }
    
    public TransportException(String message) {
        super(message);
    }
}
