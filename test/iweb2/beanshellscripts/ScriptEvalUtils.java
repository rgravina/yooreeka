package iweb2.beanshellscripts;

import iweb2.util.config.IWeb2Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bsh.Interpreter;

class ScriptEvalUtils {
    
    public static String BEANSHELL_SCRIPTS_DIR = IWeb2Config.getHome()+"/docs/BeanShell-Notes";
    
    public static List<String> getScripts(String filter) {
        return getScripts(BEANSHELL_SCRIPTS_DIR, filter);
    }
    
    public static void runScripts(String chapter) {
        List<String> scripts = ScriptEvalUtils.getScripts(chapter);
        for(String script : scripts) {
            ScriptEvalUtils.runScript(script);
        }
    }

    public static void runDependentScripts(String[] scripts) {
        Interpreter i = new Interpreter();
        try {
            i.eval("import *;");
        }
        catch(Exception e) {
            throw new RuntimeException("Failed to execute 'import *' : ", e);
        }
        
        for(String name : scripts) {
            String script = getScript(name);
            try {
                i.source(script);
            }
            catch(Exception e) {
                throw new RuntimeException("Failed to run script: " + script, e);
            }
                
        }
    }
    
    public static void runIndependentScripts(String[] scripts) {
        
        for(String name : scripts) {
            String script = getScript(name);            
            runScript(script);
        }
    }
    
    
    public static String getScript(String scriptName) {
        File scriptFile = new File(BEANSHELL_SCRIPTS_DIR, scriptName);
        if( !scriptFile.exists() ) {
            throw new RuntimeException("Script file doesn't exists: '" + 
                    scriptFile.getAbsolutePath() + "'");
        }
        return scriptFile.getAbsolutePath();
    }
    
    
    public static List<String> getScripts(String dir, String filter) {
        List<String> allScripts = new ArrayList<String>();
        File scriptsDir = new File(dir);

        for(File f : scriptsDir.listFiles()) {
            if( f.isFile() && f.getName().contains(filter) ) {
                String script = f.getAbsolutePath();
                allScripts.add(script);
                System.out.println("Added script: " + script);
            }
        }
        return allScripts;
    }

    public static void runScript(String script) {
        Interpreter i = new Interpreter();
        try {
        i.eval("import *;");
        i.source(script);
        }
        catch(Exception e) {
            throw new RuntimeException("Failed to run script: " + script, e);
        }
        
    }
    
}
