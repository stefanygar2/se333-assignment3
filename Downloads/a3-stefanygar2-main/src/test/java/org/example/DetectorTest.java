package org.example;

import org.example.Pattern.AlwaysTrue;
import org.example.Pattern.DivisionByZero;
import org.example.Pattern.FileNotClosed;
import org.example.Pattern.IncorrectStringComparison;
import org.example.Util.ModelUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DetectorTest {

    static ModelUtil buggyModel;

    @BeforeAll
    public static void setup() {
        Path projectRoot = Paths.get("").toAbsolutePath();

        buggyModel = new ModelUtil(projectRoot.resolve(
                Paths.get("src", "main", "java", "org", "example", "Resource", "BuggyFile.java")
        ));
    }

    @Test
    public void testAlwaysTruePositive() {
        AlwaysTrue detector = new AlwaysTrue(buggyModel);
        detector.process();
        assertFalse(detector.getDetection().isEmpty());
    }

    @Test
    public void testDivisionByZeroPositive() {
        DivisionByZero detector = new DivisionByZero(buggyModel);
        detector.process();
        assertFalse(detector.getDetection().isEmpty());
    }

    @Test
    public void testIncorrectStringComparisonPositive() {
        IncorrectStringComparison detector = new IncorrectStringComparison(buggyModel);
        detector.process();
        assertFalse(detector.getDetection().isEmpty());
    }

    @Test
    public void testFileNotClosedPositive() {
        FileNotClosed detector = new FileNotClosed(buggyModel);
        detector.process();
        assertFalse(detector.getDetection().isEmpty());
    }
}