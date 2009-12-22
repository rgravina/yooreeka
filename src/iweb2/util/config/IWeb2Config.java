package iweb2.util.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Central place to access to application properties. 
 */
public class IWeb2Config {

    public static final String DATA_DIR = "iweb2.data.dir";
    public static final String TEMP_DIR = "iweb2.temp.dir";
    public static final String MOVIELENS_DATA_DIR = "iweb2.movielens.data.dir";
    public static final String MOVIELENSTEST_DATA_DIR = "iweb2.ch3.movielenstest.data.dir";

    /*
     * System property name that can be used to override default properties file.
     */
    private static String systemPropertyName = "iweb2.configuration";

	public static String getHome() {
       return System.getProperty("iweb2.home", "C:/iWeb2");
   }
    
    /*
     * Default resource name that will be used to load properties.
     */
    private static String defaultResourceName = "/iweb2.properties";
    
    private static Properties props = new Properties(); 
    
    static {
//        logger.debug("Initializing application properties...");
        String resourceName =  System.getProperty(systemPropertyName);
        if( resourceName == null ) {
            resourceName = defaultResourceName;            
//            logger.debug("System property '" + systemPropertyName + 
//                    "' not found. Loading configuration from default resource: '" + 
//                    defaultResourceName + "'.");
        }
        else {
        	System.out.println("Loading configuration from resource defined through system property: " + 
                 systemPropertyName + "=" + resourceName);
        }

        try {
            InputStream inStream = IWeb2Config.class.getResourceAsStream(resourceName);
            assert(inStream != null);
            props.load(inStream);
        }
        catch(Exception e) {
            String message = "Failed to load properties from resource: '" + resourceName + "'.";
            System.out.println("ERROR:\n"+message+"\n"+ e.getMessage());
            throw new RuntimeException(message, e);
        }
    }
    
    /** 
     * First checks if there is a system property with the same key. Then attempts to load
     * property from the configuration file.
     * 
     * @return null if property not found.
     */
    public static String getProperty(String key) {
        // allow to override property using -D<property name>=<property value>
        return System.getProperty(key, props.getProperty(key));
    }
    
    /**
     * First checks if there is a system property with the same key. Then attempts to load
     * property from the configuration file.
     * 
     * @param key identifies property.
     * @param defaultValue default value that will be used if property is not found.
     * @return property value or default value.
     */
    public static String getProperty(String key, String defaultValue) {
        // allow to override property using -D<property name>=<property value>
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }
    
    
}
