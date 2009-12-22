package iweb2.ch3.collaborative.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Implementation of <code>Store</code> interface. Uses files to store objects
 * using java serialization. Each object instance is stored in a separate file.
 */
public class FileStore implements Store {

    private File dataDir;
    
    /**
     * Creates a new instance that will use specified directory to store objects.
     * 
     * @param dir directory that should be used to store/retrieve objects. 
     */
    public FileStore(String dir) {
        this(new File(dir));
    }
    
    public FileStore(File dir) {
        if( !dir.exists() ) {
            dir.mkdirs();
        }
        this.dataDir = dir;
    }
    
    public boolean exists(String key) {
        File f = getFile(key);
        return f.exists();
    }

    public Object get(String key) {
      Object o = null;
      try {
          File f = getFile(key);
          if( f.exists() ) {
              FileInputStream fInStream = new FileInputStream(f);
              BufferedInputStream bufInStream = new BufferedInputStream(fInStream);
              ObjectInputStream objInStream = new ObjectInputStream(bufInStream);
              o = objInStream.readObject();
              objInStream.close();
          }
      }
      catch(Exception e) {
          throw new RuntimeException("Error while loading data from file (dir: '" + dataDir + "', filename: '" + key +"').", e);            
      }
      return o;
    }

    public void put(String key, Object o) {
      try {
          File f = getFile(key);
          FileOutputStream foutStream = new FileOutputStream(f);
          BufferedOutputStream boutStream = new BufferedOutputStream(foutStream);
          ObjectOutputStream objOutputStream = new ObjectOutputStream(boutStream);
          objOutputStream.writeObject(o);
          objOutputStream.flush();
          boutStream.close();
      }
      catch(IOException e) {
          throw new RuntimeException("Error while saving data into file (dir: '" + dataDir + "', filename: '" + key +"').", e);         
      }
    }
    
    public void remove(String key) {
        File f = getFile(key);
        if( f.exists() ) {
            f.delete();
        }
    }

    /*
     * Derives filename from the key and returns instance of <code>File</code>
     */
    private File getFile(String key) {
        // key is used as a filename
        return new File(dataDir, key + ".tmp");
    }
}
