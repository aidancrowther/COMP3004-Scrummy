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

public class TerminalUI extends View implements PlayerInterface
{
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

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public void drawTile() {
    }

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile) {
        if(inMeld.getTiles().contains(tile)){
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }

    public Table play(){
        while(this.playing){
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
            this.playing = false;
        }
        return null;
    }

    public void setHand(Meld hand) {
        this.hand = hand;
    }

    public String generateTileString(Tile tile) {
        //'R', 'G', 'B', 'O'
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

    public void printPlayerAction(String message) {
        System.out.println(ANSI_CYAN + message + ANSI_RESET);
    }

    public void printAIAction(String message) {
        System.out.println(ANSI_PURPLE + message + ANSI_RESET);
    }

    public void printMessage(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public void printMessagePlain(String message) {
        System.out.println(message);
    }
}