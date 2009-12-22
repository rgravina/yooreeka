package iweb2.beanshellscripts;

import junit.framework.TestCase;

public class Ch6BeanShellScriptsTest extends TestCase {

    public Ch6BeanShellScriptsTest(String name) {
        super(name);
    }
    
    public void test_evalCh6DependentScripts() throws Exception {
        ScriptEvalUtils.runDependentScripts(
                new String[] {
                    "ch6_2_CreditScoreNB.txt",
                    "ch6_3_CreditScoreDT.txt",
                    "ch6_4_CreditScoreNN.txt",
                    "ch6_5_Comparisons.txt"
                });
    }
    
    public void test_evalCh6IndependentScripts() throws Exception {
        ScriptEvalUtils.runIndependentScripts(
                new String[] {
                "ch6_1_CreditScoreData.txt",                        
                "ch6_6_Bagging.txt",
                "ch6_6_Bagging_DT.txt",
                "ch6_6_Bagging_NB.txt",
                "ch6_6_Bagging_NN.txt",
                "ch6_6_DT-Bagging.txt"
        } );
    }
    
}
