/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * This UI implemenation should link into std.io and allow the user to play the game from there
 */

package COMP3004;

import java.util.Scanner;

public class TerminalUI extends View
{
    private Scanner scanner;

    //Colours for printing to terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_GREY = "\u001B[90m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public TerminalUI(){
        this.scanner = new Scanner(System.in);
    }

    public Table play(){
        this.printWelcomeMessage();
        this.printMessage("\nDo you want to make a move? (y/n)");
        if(true){
            while(this.move()){}
        }
        return this.table;
    }

    //Used if the player made an invalid move
    public Table play(String message) {
        this.printMessage(message);
        return this.table;
    }


    private boolean move(){
        //SELECT AN ACTIVE MELD (OR HAND)
        //SELECT TILE
        //SELECT MELT TO MOVE TILE
        //MOVE TILE
        this.printMessage("\nDo you want to make a move? (y/n)");
        return false;
    }



    public String generateTileString(Tile tile) {
        String tileColour = ANSI_BLUE;
        if(tile.getColour() == 'R'){
            tileColour = ANSI_RED;
        } else if(tile.getColour() == 'G') {
            tileColour = ANSI_GREEN;

        } else if (tile.getColour() == 'O') {
            tileColour = ANSI_YELLOW; //best i can do
        }

        return " |" + tileColour + tile.getValue() + ANSI_RESET + (tile.getValue() < 10 ? " " : "") + "| ";
    }

    private void printActivePlayerName(String name) {
        this.printPlayerAction("Current Player: " + name);
    }

    private void printActivePlayerHand(Meld hand) {
        String row = "";
        for(Tile tile : hand.getTiles()){
            row += this.generateTileString(tile);
        }
        this.printMessagePlain(row);
    }

    private void printTable() {
        int currMeld = 1;
        for(Meld meld : this.table.getMelds()) {
            String row = "Meld #" + currMeld + ": ";
            for(Tile tile : meld.getTiles()){
                row += this.generateTileString(tile);
            }
            this.printMessagePlain(row);
            currMeld++;
        }
    }

    private String readPlayerInput(){
        return this.scanner.nextLine();
    }

    private void printPlayerAction(String message) {
        System.out.println(ANSI_CYAN + message + ANSI_RESET);
    }

    private void printAIAction(String message) {
        System.out.println(ANSI_PURPLE + message + ANSI_RESET);
    }

    private void printMessage(String message) {
        System.out.println(ANSI_BRIGHT_YELLOW + message + ANSI_RESET);
    }

    private void printMessagePlain(String message) {
        System.out.println(message);
    }

    private void printWelcomeMessage(){
        this.printMessage("Let's play Scrummy!");
        this.printPlayerAction("A player move looks like this.");
        this.printAIAction("An AI action looks like this.");

        this.printMessage("Tiles look like:");
        Tile testTile = new Tile('R', 1);
        this.printMessagePlain(this.generateTileString(testTile));
        testTile = new Tile('G', 6);
        this.printMessagePlain(this.generateTileString(testTile));
        testTile = new Tile('B', 12);
        this.printMessagePlain(this.generateTileString(testTile));
        testTile = new Tile('O', 4);
        this.printMessagePlain(this.generateTileString(testTile));
    }


}