package iweb2.ch5.classification.rules;

import iweb2.ch5.usecase.email.data.Email;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.rule.Package;

public class RuleEngine {

	private RuleBase rules;

	public RuleEngine(String rulesFile) throws RuleEngineException {

        try {
            Reader source = new InputStreamReader(
                    new BufferedInputStream(new FileInputStream(rulesFile)));

            // switch to JANINO compiler 
            Properties properties = new Properties();
            properties.setProperty( "drools.dialect.java.compiler",
                                    "JANINO" );
            PackageBuilderConfiguration cfg = 
                new PackageBuilderConfiguration( properties );          
            
            // build a rule package
            PackageBuilder builder = new PackageBuilder(cfg);

            // parse and compile rules
            builder.addPackageFromDrl(source);

            Package pkg = builder.getPackage();

            rules = RuleBaseFactory.newRuleBase();
            rules.addPackage(pkg);

        } catch (Exception e) {
            throw new RuleEngineException(
                    "Could not load/compile rules from file: '" + rulesFile 
                        + "' ", e);
        }
	}
	
	public void executeRules(ClassificationResult classificationResult, Email email ) {
	    WorkingMemory workingMemory = rules.newStatefulSession();
		
	    workingMemory.setGlobal("classificationResult", classificationResult);
        workingMemory.insert(email);
		workingMemory.fireAllRules();
	}
}
