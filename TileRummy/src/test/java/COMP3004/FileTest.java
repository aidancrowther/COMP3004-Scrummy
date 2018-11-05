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
        
        assertTrue(s.contains("Player's hand: {B1, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13}") && s.contains("AI 1's hand: {B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, G1, G2}") && s.contains("AI 2's hand: {G3, G4, G5, G6, G7, G8, G9, G10, G11, G12, G13, O1, O2, O3}") && s.contains("AI 3's hand: {O4, O5, O6, O7, O8, O9, O10, O11, O12, O13, R1, R2, R3, R4}") && s.contains("AI 4's hand: {B1, B2, B3, B4, B5, R5, R6, R7, R8, R9, R10, R11, R12, R13}")); //Req 1
        assertTrue(s.contains("Moved from hand R1 to meld 0"));//Req 3

        arg[1] = "TestFile2.txt";
        Main.main(arg);
        s = outStream.toString();

        //Req 2FileTest : test1
        assertTrue(s.indexOf("Current Player: AI 1") < s.indexOf("Current Player: AI 2") && s.indexOf("Current Player: AI 2") < s.indexOf("Current Player: AI 3"));//turn order's working and it's displayed
        assertTrue(s.contains("Player drew from the deck tile: B6"));//Req 6
        p.close();
    }
 }