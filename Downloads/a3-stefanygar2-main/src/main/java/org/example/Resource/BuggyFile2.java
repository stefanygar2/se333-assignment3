package org.example.Resource;
import java.io.FileReader;
import java.io.IOException;

public class BuggyFile2 {
    public void noAlwaysTrue() {
        int x = 5;
        if (x > 3) {
            x++;
        }
    }

    public void noDivisionByZero() {
        int x = 10;
        int y = 2;
        x = x / y;
    }
    public void noIncorrectStringComparison() {
        String s1 = "hello";
        String s2 = "world";
        boolean same = s1.equals(s2);
    }

    public void noFileNotClosed() throws IOException {
        FileReader reader = new FileReader("file.txt");
        reader.close();
    }





}
