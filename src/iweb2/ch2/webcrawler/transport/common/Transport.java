package iweb2.ch2.webcrawler.transport.common;

import iweb2.ch2.webcrawler.model.FetchedDocument;

public interface Transport {
    public FetchedDocument fetch(String url) throws TransportException;
    public void init();
    public void clear();
    public boolean pauseRequired();
}
