package org.example.Pattern;

// Importing necessary classes from the Spoon library and other utility classes.
import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.visitor.filter.TypeFilter;



/**
 * A custom static analysis rule that checks for empty method clauses in Java source code.
 * It extends Spoon's AbstractPattern to process CtMethod elements in the Abstract Syntax Tree (AST).
 */
public class FileNotClosed extends AbstractPattern {

    // Log message that will be used for the File Not Closed blocks.
    String logMessage = "File Not Closed";

    // The CtModel object that contains the entire abstract syntax tree of the parsed Java code.
    CtModel model;

    // Constructor that takes a ModelUtil object and initializes the CtModel field.
    public FileNotClosed(ModelUtil model){
        this.model = model.getModel(); // Retrieves the CtModel from the ModelUtil.
    }

    /**
     * Processes the CtModel to find File Not Closed and add them to the 'elements' list.
     */
    @Override
    public void process() {
        for (CtLocalVariable<?> variable :
                model.getElements(new TypeFilter<>(CtLocalVariable.class))) {

            if (variable.getAssignment() instanceof CtConstructorCall<?> constructorCall) {

                String typeName = constructorCall.getType().getSimpleName();

                if (typeName.equals("FileReader")
                        || typeName.equals("BufferedReader")
                        || typeName.equals("FileInputStream")) {

                    String variableName = variable.getSimpleName();

                    boolean closed = false;

                    for (CtInvocation<?> invocation :
                            model.getElements(new TypeFilter<>(CtInvocation.class))) {

                        String invocationText = invocation.toString();

                        if (invocationText.contains(variableName + ".close()")) {
                            closed = true;
                        }
                    }

                    if (!closed) {

                        elements.add(new elementSchema(
                                logMessage,
                                variable.getPosition().getFile().getName(),
                                variable.getPosition().getLine(),
                                variable.getPosition().getEndLine(),
                                variable.toString()));
                    }
                }
            }
        }
    }
}