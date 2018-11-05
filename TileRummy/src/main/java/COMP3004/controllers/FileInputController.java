/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * The first line of the command file MUST BE the filename of the deck you wish to load.
 * All files for testing (currently) must be in a 
 * 
 * DeckFile: This thing should be a list of exactly how the cards are formatted with a letter to annotate colour and a number to annotate the number value eg. R13, O4, B2, G10
 *              every new tile should be on a new line.
 * CommandFile: This thing takes commands in the same way that you enter them into the terminal, immagine it asks you "do you want to play a card (y/n)" you enter y or n and hit enter. same thing here
 *              you can walk through the questions its asking you in the comments of the play funciton
 */
package COMP3004.controllers;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.Stack;

import COMP3004.models.Tile;
import COMP3004.models.Deck;
import COMP3004.models.Meld;
import COMP3004.models.Table;

public class FileInputController extends GameInteractionController {
    LinkedList<String> commands;
    Deck deck;
    int lineNum = 2;
    String testDirectory = "./fileInput/";

    public FileInputController (String fileName) {
        File userInput = new File(testDirectory + fileName);
        File deckFile;

        commands = load(userInput);
        
        deckFile = new File(testDirectory + commands.poll());
        deck = loadDeck(load(deckFile));
    }
    public FileInputController (String fileName, String dir) {
        testDirectory = dir;
        if (testDirectory.charAt(testDirectory.length()) != '/')
            testDirectory += '/';
        File userInput = new File(testDirectory + fileName);
        File deckFile;

        commands = load(userInput);
        
        deckFile = new File(testDirectory + commands.poll());
        deck = loadDeck(load(deckFile));
    }

    public Deck getDeck() { return deck; }

    public Table play(Meld hand) {
        System.out.println(
            "Hand before moves:\n" +
            hand.toString() +
            "\nTable before moves\n" +
            table.prettyString() +
            "\n\n"
        );

        String cmd;
        while (!commands.isEmpty()) {
            cmd = nextCommand();
            if (cmd.charAt(0) == 'y')//Do you want to make a move? (y/n)
            {//yes play
                cmd = nextCommand();
                if (cmd.equals("t"))//Do you want to move a tile on the table, or play from your hand (t/h)?
                {//table
                    cmd = nextCommand();//Which meld would you like select the tile from? (Enter meld #)
                    int meldFromNum = 0;
                    try {meldFromNum = Integer.parseInt(cmd);}
                    catch (Exception err) {invalidCommand("Command was supposed to be a table meld number, recieved: " + cmd + '\n' + err.getMessage());}
                    
                    cmd = nextCommand();//Which tile do you want to select? (Enter tile position # as shown above the tiles.)
                    int tileNum = 0;
                    try {tileNum = Integer.parseInt(cmd);}
                    catch (Exception err) {invalidCommand("Command was supposed to be a tile number for table meld " + meldFromNum + ", recieved: " + cmd + '\n' + err.getMessage());}

                    cmd = nextCommand(); //Which meld would you like to move the tile to? (Enter meld # or 0 to create add to a tentative meld)
                    int meldToNum = 0;
                    try {tileNum = Integer.parseInt(cmd);}
                    catch (Exception err) {invalidCommand("Command was supposed to be a table meld number, recieved: " + cmd + '\n' + err.getMessage());}
                    if (meldToNum == 0){
                        Tile tileBeingMoved = table.getMelds().get(meldFromNum).getTiles().get(tileNum);
                        Meld fromMeld = table.getMelds().get(meldFromNum);
                        Meld ToMeld = table.getMelds().get(meldToNum);
                        selectTile(fromMeld, ToMeld, tileBeingMoved);//Move is done here
                        System.out.println("Moved from table meld " + meldFromNum + " tile " + tileBeingMoved.toString() + " to meld " + meldToNum);
                    }
                }
                else if (cmd.equals("h")){//hand
                    cmd = nextCommand();//Which tile do you want to select? (Enter tile position # as shown above the tiles.)
                    int tileNum = 0;
                    try {tileNum = Integer.parseInt(cmd);}
                    catch (Exception err) {invalidCommand("Command was supposed to be a tile from player's hand, recieved: " + cmd + '\n' + err.getMessage());}
                    
                    cmd = nextCommand();//Which meld would you like to move the tile to? (Enter meld # or 0 to create add to a tentative meld)
                    int meldToNum = 0;
                    try {meldToNum = Integer.parseInt(cmd);}
                    catch (Exception err) {invalidCommand("Command was supposed to be a meld on the table, recieved: " + cmd + '\n' + err.getMessage());}

                    Tile tileMoved = hand.getTiles().get(tileNum);
                    Meld toMeld = table.getMelds().get(meldToNum);
                    selectTile(hand, toMeld, tileMoved);
                    System.out.println("Moved from hand " + tileMoved.toString() + " to meld " + meldToNum);
                }
                else
                    invalidCommand("Expected t/h selection recieved: " + cmd);
                table.checkMeldZeroValidAndAppend();
            }
            else if (cmd.equals("n"))
                break;
            else
                invalidCommand("File expected a y/n recieved:" + cmd);
        }
        System.out.println(
            "Hand after moves:\n" +
            hand.toString() +
            "\nTable after moves\n" +
            table.prettyString() +
            "\n\n"
        );
        return table;
    }
    private String nextCommand(){//keeps track of line numbers for errors
        if (commands.isEmpty())
            invalidCommand("Ran out of inputs");
        String rtnCmd = commands.poll();
        lineNum++;
        while ((rtnCmd.length() > 0) ? (rtnCmd.charAt(0) == '/') : true){//line can't be empty, and allow for commenting
            if (commands.isEmpty())
                invalidCommand("Ran out of inputs");
            rtnCmd = commands.poll();
            lineNum++;
        }
        return rtnCmd;
    }

    private void invalidCommand(String err) {
        System.out.println("Command on fileInput line number " + lineNum + " was invalid because: \"" + err + '"');
        System.exit(2);
    }

    private Deck loadDeck (LinkedList<String> tiles) {
        Deck d = new Deck(true);
        Stack<Tile> reversingStack = new Stack<Tile>();
        int n;
        for (String s : tiles) {
            n = Integer.parseInt(s.substring(1));
            reversingStack.add(new Tile(s.charAt(0), n));
        }
        // for (Tile t : reversingStack) //The second loop is needed so that I can use the FIFO queue load method and not have to enter into the file in reverse
        while (!reversingStack.isEmpty())
            d.push(reversingStack.pop());
        return d;
    }
    private LinkedList<String> load(File inputFile){
        LinkedList<String> input = new LinkedList<String>();
        BufferedReader reader;
        FileReader fReader;

        try {
            fReader = new FileReader(inputFile);

            reader = new BufferedReader(fReader);

            String line;
            while ((line = reader.readLine()) != null)
                input.add(line);

            reader.close();
        }
        catch (Exception e){
            System.out.println("Issue loading file ''" + inputFile.getName() + "' blew an error with message:\n" + e.getMessage());
            System.exit(2); // TODO: make this thing loop into asking the user for a filename but for now she's just gonna exit
        }

        return input;
    }
}