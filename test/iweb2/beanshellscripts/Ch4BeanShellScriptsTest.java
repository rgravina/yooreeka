package iweb2.beanshellscripts;

import junit.framework.TestCase;

public class Ch4BeanShellScriptsTest extends TestCase {

    public Ch4BeanShellScriptsTest(String name) {
        super(name);
    }
    
    public void test_evalCh4Scripts() throws Exception {
        ScriptEvalUtils.runScripts("ch4");
    }
}
