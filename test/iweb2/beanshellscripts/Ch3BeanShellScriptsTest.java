package iweb2.beanshellscripts;

import junit.framework.TestCase;

public class Ch3BeanShellScriptsTest extends TestCase {

    public Ch3BeanShellScriptsTest(String name) {
        super(name);
    }
    
    public void test_evalCh3Scripts() throws Exception {
        ScriptEvalUtils.runScripts("ch3");
    }
}
