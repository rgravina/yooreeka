package iweb2.ch2.webcrawler;

import iweb2.ch2.webcrawler.model.FetchedDocument;

public class DocumentFilter {

    /*
     * Supposed to detect if we've already processed document with the same
     * content through some other url.
     */
    public boolean duplicateContentExists(FetchedDocument doc) {
        return false;
    }
}
