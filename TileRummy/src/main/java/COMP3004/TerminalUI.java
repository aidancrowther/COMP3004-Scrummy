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

import javafx.beans.binding.IntegerBinding;

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
        //this.printWelcomeMessage();
        this.printTable();
        this.printActivePlayerHand(this.hand);
        this.printMessage("\nDo you want to make a move? (y/n)");
        if(this.readPlayerInput().equals("y")){
            while(this.move()){}
        }
        return this.table; //will be the same if player doesn't move
    }

    //Used if the player made an invalid move
    public Table play(String message) {
        this.printMessage(message);
        return this.table;
    }


    private boolean move(){
        //SELECT AN ACTIVE MELD (OR HAND)
        Meld fromMeld = this.hand;
        if(this.table.getMelds().size() > 1){
            this.printMessage("Do you want to move a tile on the table, or play from your hand (t/h)?");
            String response = this.readPlayerInput();

            //SELECT TILE
            if(response.equals("t")) {
                fromMeld = this.selectMeldFromTable("Which meld would you like select the tile from? (Enter meld #)");
            }
        }

        Tile selectedTile = null;
        while(selectedTile == null){
            this.printMessage("Which tile do you want to select? (Enter tile position # starting from 1)");
            int index = Integer.parseInt(this.readPlayerInput()) - 1;
            if(fromMeld.getTiles().size() >= index && index >= 0) {
                selectedTile = fromMeld.getTiles().get(index);
            } else {
                this.printMessage("You've entered an incorrect index.");
            }
        }

        //SELECT MELD TO MOVE TILE
        if(this.table.getMelds().size() > 1){
            Meld toMeld = this.selectMeldFromTable("Which meld would you like to move the tile to? (Enter meld # or 0 to create add to a tentative meld)");
            this.selectTile(fromMeld, toMeld, selectedTile);
        } else {
            this.table.add(selectedTile);
            this.hand.remove(selectedTile);
        }

        this.printPlayerAction("\nThe tile has been moved.");
        this.printTable();
        this.printActivePlayerHand(this.hand);
        this.printMessage("\nDo you want to make a move? (y/n)");
        return this.readPlayerInput().equals("y");
    }

    public Meld selectMeldFromTable(int index){
        Meld selectedMeld = null;
        System.out.println(this.table.getMelds().size());
        while(selectedMeld == null){
            if(this.table.getMelds().size() > index && index >= 0){
                selectedMeld = this.table.getMelds().get(index);
            }
        }
        return selectedMeld;
    }

    public Meld selectMeldFromTable(String message){
        Meld selectedMeld = null;
        while(selectedMeld == null){
            this.printMessage(message);//"Which meld would you like to move the tile to? (Enter meld # or 0 to create a new meld)");
            int index = Integer.parseInt(this.readPlayerInput());
            if(this.table.getMelds().size() > index && index >= 0){
                selectedMeld = this.table.getMelds().get(index);
            }
        }
        return selectedMeld;
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
        String row = "\nPLAYER HAND:\n";
        for(Tile tile : hand.getTiles()){
            row += this.generateTileString(tile);
        }
        this.printMessagePlain(row);
    }

    private void printTable() {
        this.printMessage("\nGAME TABLE");
        int currMeld = 0;
        for(Meld meld : this.table.getMelds()) {
            String row = "";
            if(currMeld == 0){
                if(meld.getTiles().size() > 0){
                    row = "Tentative Meld: ";
                    for(Tile tile : meld.getTiles()){
                        row += this.generateTileString(tile);
                    }
                }
            } else {
                row = "Meld #" + currMeld + ": ";
                for(Tile tile : meld.getTiles()){
                    row += this.generateTileString(tile);
                }
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