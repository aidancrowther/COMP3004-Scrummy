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

package COMP3004.controllers;

import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.views.TerminalView;

public class PlayerInteractionController extends GameInteractionController
{
    private int score = 0;
    private boolean enableTableInteraction = false;
    private Table playedTable = new Table();
    public PlayerInteractionController(){
    }

    public Table play(Meld hand){
        this.playedTable = this.getTableCopy(this.table);

        this.terminalView.printMessage("Current Player: " + this.player.getName());
        this.player.setHand(hand);

        this.terminalView.printTable(this.getTable());
        this.terminalView.printActivePlayerHand(hand);
        this.terminalView.printMessage("\nDo you want to make a move? (y/n)");
        if(this.terminalView.readPlayerInput().equals("y")){
            while(this.move(hand)){}
        }

        if(score >= 30){ //Only let the player move if the score >= 30.
            this.terminalView.printMessage("You are done your turn. The game table now looks like:");
            this.terminalView.printTable(this.playedTable);
            this.terminalView.printLine();
            return this.playedTable;
        }

        this.terminalView.printMessage("You chose not to move.\n");
        this.terminalView.printLine();
        this.terminalView.printLine();
        return this.table; //will be the same if player doesn't move
    }

    //Used if the player made an invalid move
    public Table play(Meld hand, String message) {
        this.terminalView.printMessage(message);
        return this.getTable();
    }

    private boolean move(Meld hand){
        //SELECT AN ACTIVE MELD (OR HAND)
        Meld fromMeld = hand;
        if(enableTableInteraction && this.playedTable.getMelds().size() > 1){
            this.terminalView.printMessage("Do you want to move a tile on the table, or play from your hand (t/h)?");
            String response = this.terminalView.readPlayerInput();

            if(response.equals("t")) {
                fromMeld = this.selectMeldFromTable("Which meld would you like select the tile from? (Enter meld #)");
            }
        } else {
            this.terminalView.printMessage("You must play melds of value >= 30 before interacting with melds on the table. Choose tiles from your hand to play. ");
        }

        //SELECT TILE
        Tile selectedTile = null;
        while(selectedTile == null){
            this.terminalView.printMessage("Which tile do you want to select? (Enter tile position # as shown above the tiles.)");
            int index = Integer.parseInt(this.terminalView.readPlayerInput()) - 1;
            if(fromMeld.getTiles().size() >= index && index >= 0) {
                selectedTile = fromMeld.getTiles().get(index);
            } else {
                this.terminalView.printMessage("You've entered an incorrect position number.");
            }
        }

        //SELECT MELD TO MOVE TILE
        if(enableTableInteraction && this.playedTable.getMelds().size() > 1){
            Meld toMeld = this.selectMeldFromTable("Which meld would you like to move the tile to? (Enter meld # or 0 to create add to a tentative meld)");
            this.selectTile(fromMeld, toMeld, selectedTile);
            if(fromMeld.getTiles().size() == 0){
                this.playedTable.remove(fromMeld);
            }
            if(toMeld.getTiles().size() == 0){
                this.playedTable.remove(toMeld);
            }
            this.playedTable.checkMeldZeroValidAndAppend();
        } else {
            this.score += selectedTile.getValue();
            this.playedTable.add(selectedTile);
            hand.remove(selectedTile);
        }

        if(score >= 30){
            enableTableInteraction = true;
            this.terminalView.printMessage("\nYour score has reached >= 30! You may now interact with the table.");
        }

        this.terminalView.printPlayerAction("\nThe tile has been moved.");
        this.terminalView.printTable(this.playedTable);
        this.terminalView.printActivePlayerHand(hand);
        this.terminalView.printMessage("\nDo you want to make a move? (y/n)");
        return this.terminalView.readPlayerInput().equals("y");
    }

    public Meld selectMeldFromTable(int index){
        Meld selectedMeld = null;
        while(selectedMeld == null){
            if(this.playedTable.getMelds().size() > index && index >= 0){
                selectedMeld = this.playedTable.getMelds().get(index);
            }
        }
        return selectedMeld;
    }

    public Meld selectMeldFromTable(String message){
        Meld selectedMeld = null;
        while(selectedMeld == null){
            this.terminalView.printMessage(message);//"Which meld would you like to move the tile to? (Enter meld # or 0 to create a new meld)");
            int index = Integer.parseInt(this.terminalView.readPlayerInput());
            if(this.playedTable.getMelds().size() > index && index >= 0){
                selectedMeld = this.playedTable.getMelds().get(index);
            }
        }
        return selectedMeld;
    }

    public void displayWinner(String playerName) {
        this.terminalView.printMessage(playerName + " has won the game!");
    }

    public TerminalView getGameView(){
        return this.terminalView;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}