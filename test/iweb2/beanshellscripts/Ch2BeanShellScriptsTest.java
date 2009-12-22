package iweb2.beanshellscripts;

import junit.framework.TestCase;

public class Ch2BeanShellScriptsTest extends TestCase {

    public Ch2BeanShellScriptsTest(String name) {
        super(name);
    }
    
    public void test_EvalCh2Scripts() throws Exception {
        ScriptEvalUtils.runScripts("ch2");
    }
}
