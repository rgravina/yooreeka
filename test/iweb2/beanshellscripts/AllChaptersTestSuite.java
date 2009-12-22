package iweb2.beanshellscripts;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllChaptersTestSuite extends TestSuite {

    // Works for JUnit 3. 
    public static Test suite() {
        TestSuite suite = 
            new TestSuite("Tests for All BeanShell scripts");

        suite.addTestSuite(Ch2BeanShellScriptsTest.class);
        suite.addTestSuite(Ch3BeanShellScriptsTest.class);
        suite.addTestSuite(Ch4BeanShellScriptsTest.class);
        suite.addTestSuite(Ch5BeanShellScriptsTest.class);
        suite.addTestSuite(Ch6BeanShellScriptsTest.class);
        suite.addTestSuite(Ch7BeanShellScriptsTest.class);
        
        return suite;
    }
    
    
    
    public AllChaptersTestSuite() {
        
    }
    
}
