package org.example;

import org.example.Pattern.*;
import org.example.Pattern.EmptyMethod;
import org.example.Pattern.EmptyCatch;
import org.example.Pattern.AlwaysTrue;
import org.example.Pattern.IncorrectStringComparison;
import org.example.Pattern.FileNotClosed;
import org.example.Pattern.DivisionByZero;
import org.example.Util.ModelUtil;



import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Main class to demonstrate the usage of the ModelUtil utility for static analysis of Java source code.
 * It parses a specific Java file and applies custom analysis rules (processors) to the parsed Abstract Syntax Tree (AST).
 */
public class Main {

    /**
     * The entry point of the application.
     * This method demonstrates how to use the ModelUtil class to analyze a Java file
     * and apply a static analysis rule.
     *
     * @param args Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {

        // Define the path to the Java file to be analyzed -  it's under resources folder
        Path projectRoot = Paths.get("").toAbsolutePath();
        Path path = Paths.get(
                "/Users/stefanygarcia/Downloads/commons-lang/src/main/java"
        );
        System.out.println("Analyzing path: " + path.toAbsolutePath());

        // Initialize the ModelUtil instance with the specified Java file
        ModelUtil model = new ModelUtil(path);

        // Create a custom static analysis rule (EmptyCatch in this case)
        final EmptyCatch rule1 = new EmptyCatch(model);
        final EmptyMethod rule2 = new EmptyMethod(model);
        final AlwaysTrue rule3= new AlwaysTrue(model);
        final IncorrectStringComparison rule4= new IncorrectStringComparison(model);
        final FileNotClosed rule5= new FileNotClosed(model);
        final DivisionByZero rule6= new DivisionByZero(model);

        // Apply the rule to the parsed Java file
        model.addRuleToAnalyze(rule1);
        model.addRuleToAnalyze(rule2);
        model.addRuleToAnalyze(rule3);
        model.addRuleToAnalyze(rule4);
        model.addRuleToAnalyze(rule5);
        model.addRuleToAnalyze(rule6);



        // Apply analysis
        model.runAnalysis();

        // Print results
        model.print();
    }
}
