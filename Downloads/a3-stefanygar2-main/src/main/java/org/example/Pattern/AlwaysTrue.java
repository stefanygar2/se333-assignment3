package org.example.Pattern;

// Importing necessary classes from the Spoon library and other utility classes.
import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtIf;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.code.CtLiteral;
import java.util.List;

/**
 * A custom static analysis rule that checks for empty method clauses in Java source code.
 * It extends Spoon's AbstractPattern to process CtMethod elements in the Abstract Syntax Tree (AST).
 */
public class AlwaysTrue extends AbstractPattern {

    // Log message that will be used for the Division By Zero blocks.
    String logMessage = "Always Evaluate to TrueAlways Evaluate to True";

    // The CtModel object that contains the entire abstract syntax tree of the parsed Java code.
    CtModel model;

    // Constructor that takes a ModelUtil object and initializes the CtModel field.
    public AlwaysTrue(ModelUtil model){
        this.model = model.getModel(); // Retrieves the CtModel from the ModelUtil.
    }

    /**
     * Processes the CtModel to find always evalutes to true and add them to the 'elements' list.
     */
    @Override
    public void process(){
        for (CtIf ifStatement : model.getElements(new TypeFilter<>(CtIf.class))) {
            if (ifStatement.getCondition() instanceof CtLiteral<?> literal) {
                if (Boolean.TRUE.equals(literal.getValue())) {
                    elements.add(new elementSchema(
                            logMessage,
                            ifStatement.getPosition().getFile().getName(),
                            ifStatement.getPosition().getLine(),
                            ifStatement.getPosition().getEndLine(),
                            ifStatement.toString()
                    ));
                }
            }

        }
    }
}
