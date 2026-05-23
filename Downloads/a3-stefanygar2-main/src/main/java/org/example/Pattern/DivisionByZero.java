package org.example.Pattern;

// Importing necessary classes from the Spoon library and other utility classes.
import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import java.util.List;

/**
 * A custom static analysis rule that checks for empty method clauses in Java source code.
 * It extends Spoon's AbstractPattern to process CtMethod elements in the Abstract Syntax Tree (AST).
 */
public class DivisionByZero extends AbstractPattern {

    // Log message that will be used for the Division By Zero blocks.
    String logMessage = "Division By Zero";

    // The CtModel object that contains the entire abstract syntax tree of the parsed Java code.
    CtModel model;

    // Constructor that takes a ModelUtil object and initializes the CtModel field.
    public DivisionByZero(ModelUtil model){
        this.model = model.getModel(); // Retrieves the CtModel from the ModelUtil.
    }

    /**
     * Processes the CtModel to find Division By Zero and add them to the 'elements' list.
     */
    @Override
    public void process() {
        for (CtBinaryOperator<?> operator :
                model.getElements(new TypeFilter<>(CtBinaryOperator.class))) {

            if (operator.getKind() == BinaryOperatorKind.DIV) {

                String rightSide = operator.getRightHandOperand().toString();

                if (rightSide.equals("0")) {

                    elements.add(new elementSchema(
                            logMessage,
                            operator.getPosition().getFile().getName(),
                            operator.getPosition().getLine(),
                            operator.getPosition().getEndLine(),
                            operator.toString()
                    ));
                }
            }
        }


    }
}
