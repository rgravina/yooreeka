package iweb2.ch2.webcrawler.transport.http;

import iweb2.ch2.webcrawler.transport.common.TransportException;

public class HTTPTransportException extends TransportException {

    private static final long serialVersionUID = 546574708933803471L;

    public HTTPTransportException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public HTTPTransportException(String msg) {
        super(msg);
    }
}
