package iweb2.beanshellscripts;

import junit.framework.TestCase;

public class Ch5BeanShellScriptsTest extends TestCase {

    public Ch5BeanShellScriptsTest(String name) {
        super(name);
    }
    
    public void test_evalCh5Scripts() throws Exception {
        ScriptEvalUtils.runScripts("ch5");
    }
}
