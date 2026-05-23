package org.example.Pattern;

// Importing necessary classes from the Spoon library and other utility classes.
import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;


/**
 * A custom static analysis rule that checks for empty method clauses in Java source code.
 * It extends Spoon's AbstractPattern to process CtMethod elements in the Abstract Syntax Tree (AST).
 */
public class EmptyMethod extends AbstractPattern {

    // Log message that will be used for the empty method blocks.
    String logMessage = "Empty Method Block";

    // The CtModel object that contains the entire abstract syntax tree of the parsed Java code.
    CtModel model;

    // Constructor that takes a ModelUtil object and initializes the CtModel field.
    public EmptyMethod(ModelUtil model){
        this.model = model.getModel(); // Retrieves the CtModel from the ModelUtil.
    }

    /**
     * Processes the CtModel to find empty method blocks and add them to the 'elements' list.
     */
    @Override
    public void process() {
        // Get all the method blocks (CtMethod) in the CtModel.
        List<CtMethod> methods = model.getElements(new TypeFilter<>(CtMethod.class));

        // Iterate through each method block found.
        for (CtMethod m : methods) {
            // Check if the method block body is empty (no statements inside).
             if (m.getBody() != null && m.getBody().getStatements().isEmpty()) {
                elements.add(new elementSchema(
                        logMessage,                         // Message indicating the issue.
                        m.getPosition().getFile().getName(), // File name where the empty method is located.
                        m.getPosition().getLine(),          // Start line of the method block.
                        m.getPosition().getEndLine(),      // End line of the catch block.
                        m.toString()                       // String representation of the method block (or relevant code).
                ));
            }
        }
    }
}
