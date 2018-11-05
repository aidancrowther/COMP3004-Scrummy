/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * 
 */

 package COMP3004;

 import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;
import java.io.FileReader;
import java.io.ByteArrayOutputStream;

public class FileTest {
    @Test
    public void test1() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream p = new PrintStream(outStream);

        System.setOut(p);

        String[] arg = new String[2];
        arg[0] = "-f";
        arg[1] = "TestFile1.txt";
        Main.main(arg);

        String s = outStream.toString();
        
        assertTrue(s.contains("Hand before moves:\n{B1, R1, R2")); //This shows that it's sorting colour and number
        assertTrue(s.contains("Player's hand: ") && s.contains("AI 1's hand: ") && s.contains("AI 2's hand: ") && s.contains("AI 3's hand: "));
    }

    
 }