package iweb2.ch2.webcrawler.db;

import iweb2.ch2.webcrawler.model.KnownUrlEntry;
import iweb2.ch2.webcrawler.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnownUrlDB {
    
    private static final String DB_FILENAME = "knownurlsdb.dat";    
    
    private Map<String, KnownUrlEntry> processedURLs = 
        new HashMap<String, KnownUrlEntry>();

    private Map<String, KnownUrlEntry> unprocessedURLs = 
        new HashMap<String, KnownUrlEntry>();
    
    private File rootDir = null;
    private File dbFile = null;

    
    public KnownUrlDB(File f) {
        this.rootDir = f;

    }

    public void delete() {
        FileUtils.deleteDir(rootDir);
    }
    
    public void init() {
        rootDir.mkdirs();
        
        this.dbFile = new File(rootDir, DB_FILENAME);
        try {
            
        	// creates a new file if the file doesn't exist
            dbFile.createNewFile();
            
        } catch(IOException e) {
            throw new RuntimeException("Can't create db file: '" + 
                    dbFile.getAbsolutePath() + "'.", e);
        }
        
        load();        
    }
    
    public void save() {
        try {
            OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(dbFile), "UTF-8");
            BufferedWriter bw = new BufferedWriter(w);
            for(KnownUrlEntry r : unprocessedURLs.values() ) {
                writeRecord(bw, r);
            }
            for(KnownUrlEntry r : processedURLs.values() ) {
                writeRecord(bw, r);
            }
            bw.flush();
            bw.close();
        }
        catch(IOException e) {
            throw new RuntimeException("Failed to save data: ", e);
        }
    }
    
    private static final String FIELD_DELIMITER = "|";
    
    private void writeRecord(BufferedWriter w, KnownUrlEntry ku) 
        throws IOException {
        
        w.write(ku.getStatus() + FIELD_DELIMITER +
                String.valueOf(ku.getDepth()) + FIELD_DELIMITER + 
                ku.getUrl() 
                );
        w.newLine();
        
    }
    
    private void load() {
        try {
            FileInputStream fis = new FileInputStream(dbFile);
            InputStreamReader r = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(r);
            String line = null;
            while( (line = br.readLine()) != null ) {
                int delimiterIndex = line.indexOf(FIELD_DELIMITER);
                String status = line.substring(0, delimiterIndex);
                int secondDelimiterIndex = line.indexOf(
                        FIELD_DELIMITER, delimiterIndex + 1);
                int depth = Integer.valueOf(line.substring(
                        delimiterIndex + FIELD_DELIMITER.length(), 
                        secondDelimiterIndex) );
                String url = line.substring(
                        secondDelimiterIndex + FIELD_DELIMITER.length());
                loadUrl(url, status, depth);
            }
            br.close();
        }
        catch(IOException e) {
            throw new RuntimeException("Failed to load data: ", e);
        }
    }
    
    public boolean inUnprocessedUrl(String url) {
        return unprocessedURLs.containsKey(url);
    }
    
    public boolean inProcessedUrl(String url) {
        return processedURLs.containsKey(url);
    }
    
    public boolean isSuccessfullyProcessed(String url) {
        KnownUrlEntry r = processedURLs.get(url);
        if( r != null && 
                KnownUrlEntry.STATUS_PROCESSED_SUCCESS
                    .equalsIgnoreCase(r.getStatus()) ) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isKnownUrl(String url) {
        return processedURLs.containsKey(url) || 
               unprocessedURLs.containsKey(url);
    }
    
    private void loadUrl(String url, String status, int depth) {
        if( isKnownUrl(url) == false ) {
            KnownUrlEntry r = new KnownUrlEntry();
            r.setUrl(url);
            r.setStatus( status );
            r.setDepth(depth);
            if( KnownUrlEntry.STATUS_PROCESSED_SUCCESS.equalsIgnoreCase(status) ||
                KnownUrlEntry.STATUS_PROCESSED_ERROR.equalsIgnoreCase(status) ) {
                processedURLs.put(url, r);
            }
            else if( KnownUrlEntry.STATUS_UNPROCESSED.equalsIgnoreCase(status) ) {
                unprocessedURLs.put(url, r);                
            }
            else {
                throw new RuntimeException("Unsupported status value: '" + status + "', url: '" + url + "'.");
            }
        }
        else {
            throw new RuntimeException("Duplicate url: '" + url + "'");
        }
    }
    
    
    public boolean addNewUrl(String url, int depth) {
        boolean isAdded =false;
        
        if( isKnownUrl(url) == false ) {
            
            String status = KnownUrlEntry.STATUS_UNPROCESSED;
            KnownUrlEntry r = new KnownUrlEntry();
            r.setUrl(url);
            r.setStatus( status );
            r.setDepth(depth);
            unprocessedURLs.put(url, r);                
            isAdded = true;
        }
        else {
            isAdded = false;
        }
        
        return isAdded;
    }
    
    public void updateUrlStatus(String url, String status) {
        if( KnownUrlEntry.STATUS_PROCESSED_SUCCESS.equalsIgnoreCase(status) ||
            KnownUrlEntry.STATUS_PROCESSED_ERROR.equalsIgnoreCase(status) ) {
            KnownUrlEntry r = unprocessedURLs.remove(url);
            if( r != null ) {
                r.setStatus(status);
            }
            else {
                throw new RuntimeException("Unknown url: '" + url);
            }
            processedURLs.put(url, r);            
        }
        else if( KnownUrlEntry.STATUS_UNPROCESSED.equalsIgnoreCase(status) ) {
            KnownUrlEntry r = processedURLs.remove(url);
            if( r != null ) {
                r.setStatus(status);
            }
            else {
                throw new RuntimeException("Unknown url: '" + url);                
            }
            unprocessedURLs.put(url, r);
        }
    }
    

    public List<String> findAllKnownUrls() {
        List<String> allUrls = new ArrayList<String>();
        allUrls.addAll(unprocessedURLs.keySet());
        allUrls.addAll(processedURLs.keySet());
        return allUrls;
    }
    
    public int getTotalUrlCount() {
        return unprocessedURLs.size() + processedURLs.size();
    }

    public List<String> findProcessedUrls(String status) {
        ArrayList<String> selectedUrls = new ArrayList<String>();
        for(Map.Entry<String, KnownUrlEntry> mapEntry : processedURLs.entrySet() ) {
            KnownUrlEntry urlEntry = mapEntry.getValue();
            if( status.equalsIgnoreCase(urlEntry.getStatus()) ) {
                selectedUrls.add(urlEntry.getUrl());
            }
        }
        return selectedUrls;
    }
    
    public List<String> findUnprocessedUrls() {
        return new ArrayList<String>(unprocessedURLs.keySet());
    }
    
    /**
     * @deprecated will be removed. Use method with depth instead.
     * 
     * @param maxDocs
     * @return
     */
    @Deprecated
	public List<String> findUnprocessedUrls(int maxDocs) {
        return findUnprocessedUrls(maxDocs, 0);
    }
    
    public List<String> findUnprocessedUrls(int maxDocs, int depth) {
        List<String> selectedUrls = new ArrayList<String>();
        
        for(Map.Entry<String, KnownUrlEntry> e : unprocessedURLs.entrySet() ) {
            if( selectedUrls.size() >= maxDocs ) {
                break;
            }
            KnownUrlEntry ku = e.getValue();
            if( ku.getDepth() == depth ) {
                selectedUrls.add(ku.getUrl());
            }
        }
        
        return selectedUrls;
    }
    
}
