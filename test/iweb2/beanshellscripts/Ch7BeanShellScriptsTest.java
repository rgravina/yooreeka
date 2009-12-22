package iweb2.beanshellscripts;

import junit.framework.TestCase;

public class Ch7BeanShellScriptsTest extends TestCase {

    public Ch7BeanShellScriptsTest(String name) {
        super(name);
    }
    
    public void test_evalCh7Scripts() throws Exception {
        ScriptEvalUtils.runScripts("ch7");
    }
}
