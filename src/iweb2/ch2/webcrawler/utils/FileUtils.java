package iweb2.ch2.webcrawler.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Utility methods for files and directories. 
 */
public class FileUtils {

    /*
     * All methods are static. There should be no instances of this class.
     */
    private FileUtils() {
        // empty
    }
    
    public static void prepareDir(File dir, boolean useExisting) throws IOException {
        if( dir.exists() ) {
            if( useExisting == false ) {
                if( !FileUtils.deleteDir(dir) ) {
                    throw new IOException("Failed to delete directory: '" + dir.getAbsolutePath() + "'");
                }
            }
        }
        if( !dir.exists() ) {
            if( !dir.mkdir() ) {
                throw new IOException("Failed to create directory: '" + dir.getAbsolutePath() + "'");
            }
        }
    }
    
    /**
     * Deletes directory with its content.
     * 
     * @param dir directory to delete.
     * @return true if delete was successful.
     */
    public static boolean deleteDir(String dir) {
        File f = new File(dir);
        if( f.exists() && f.isDirectory() ) {
            return deleteDir(f);
        }
        else {
            return false;
        }
    }
    
    /**
     * Deletes directory with its content.
     * 
     * @param dir directory to delete.
     * @return true if delete was successful.
     */
    public static boolean deleteDir(java.io.File dir) {

        if( dir == null || dir.isDirectory() == false ) {
            return false;
        }
        
        for(String filename : dir.list()) {
            boolean success = false;
            File f = new File(dir, filename);
            if( f.isDirectory() ) {
                success = deleteDir(f);
            }
            else {
                success = f.delete();
            }
            if( !success ) {
                return success;
            }
        }
        
        return dir.delete();
    }
    
    /**
     * Finds files that start with specified prefix.
     * 
     * @param directory directory with files to search
     * @param filenamePrefix defines files that will be returned.
     * @return files with names that start with specified prefix. 
     */
    public static File[] findMatchingFiles(final File directory, 
            final String filenamePrefix) {
        return directory.listFiles( new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(filenamePrefix);
            } });
    }
    
}
