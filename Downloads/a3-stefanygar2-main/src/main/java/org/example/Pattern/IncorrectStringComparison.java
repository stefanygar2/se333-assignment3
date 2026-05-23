package org.example.Pattern;

// Importing necessary classes from the Spoon library and other utility classes.
import org.example.Util.ModelUtil;
import spoon.reflect.CtModel;
import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.visitor.filter.TypeFilter;


/**
 * A custom static analysis rule that checks for empty method clauses in Java source code.
 * It extends Spoon's AbstractPattern to process CtMethod elements in the Abstract Syntax Tree (AST).
 */
public class IncorrectStringComparison extends AbstractPattern {

    // Log message that will be used for the Incorrect String Comparison blocks.
    String logMessage = "Incorrect String Comparison";

    // The CtModel object that contains the entire abstract syntax tree of the parsed Java code.
    CtModel model;

    // Constructor that takes a ModelUtil object and initializes the CtModel field.
    public IncorrectStringComparison(ModelUtil model){
        this.model = model.getModel(); // Retrieves the CtModel from the ModelUtil.
    }

    /**
     * Processes the CtModel to find incorrect string comparison and add them to the 'elements' list.
     */
    @Override
    public void process() {

        for (CtBinaryOperator<?> operator :
                model.getElements(new TypeFilter<>(CtBinaryOperator.class))) {

            if (operator.getKind() == BinaryOperatorKind.EQ
                    || operator.getKind() == BinaryOperatorKind.NE) {

                String leftType = "";
                String rightType = "";

                if (operator.getLeftHandOperand().getType() != null) {
                    leftType = operator.getLeftHandOperand().getType().getQualifiedName();
                }

                if (operator.getRightHandOperand().getType() != null) {
                    rightType = operator.getRightHandOperand().getType().getQualifiedName();
                }

                if (leftType.equals("java.lang.String")
                        && rightType.equals("java.lang.String")) {

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
