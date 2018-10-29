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

public class TerminalViewController extends GameInteractionController
{
    private TerminalView terminalView;
    public TerminalViewController(){
        terminalView = new TerminalView();
    }

    public Table play(Meld hand){
        this.terminalView.printTable(this.getTable());
        this.terminalView.printActivePlayerHand(this.hand);
        this.terminalView.printMessage("\nDo you want to make a move? (y/n)");
        if(this.terminalView.readPlayerInput().equals("y")){
            while(this.move()){}
        }
        return this.getTable(); //will be the same if player doesn't move
    }

    //Used if the player made an invalid move
    public Table play(Meld hand, String message) {
        this.terminalView.printMessage(message);
        return this.getTable();
    }


    private boolean move(){
        //SELECT AN ACTIVE MELD (OR HAND)
        Meld fromMeld = this.hand;
        if(this.getTable().getMelds().size() > 1){
            this.terminalView.printMessage("Do you want to move a tile on the table, or play from your hand (t/h)?");
            String response = this.terminalView.readPlayerInput();

            if(response.equals("t")) {
                fromMeld = this.selectMeldFromTable("Which meld would you like select the tile from? (Enter meld #)");
            }
        }

        //SELECT TILE
        Tile selectedTile = null;
        while(selectedTile == null){
            this.terminalView.printMessage("Which tile do you want to select? (Enter tile position # starting from 1)");
            int index = Integer.parseInt(this.terminalView.readPlayerInput()) - 1;
            if(fromMeld.getTiles().size() >= index && index >= 0) {
                selectedTile = fromMeld.getTiles().get(index);
            } else {
                this.terminalView.printMessage("You've entered an incorrect index.");
            }
        }

        //SELECT MELD TO MOVE TILE
        if(this.getTable().getMelds().size() > 1){
            Meld toMeld = this.selectMeldFromTable("Which meld would you like to move the tile to? (Enter meld # or 0 to create add to a tentative meld)");
            this.selectTile(fromMeld, toMeld, selectedTile);
        } else {
            this.getTable().add(selectedTile);
            this.hand.remove(selectedTile);
        }

        this.terminalView.printPlayerAction("\nThe tile has been moved.");
        this.terminalView.printTable(this.getTable());
        this.terminalView.printActivePlayerHand(this.hand);
        this.terminalView.printMessage("\nDo you want to make a move? (y/n)");
        return this.terminalView.readPlayerInput().equals("y");
    }

    public Meld selectMeldFromTable(int index){
        Meld selectedMeld = null;
        System.out.println(this.getTable().getMelds().size());
        while(selectedMeld == null){
            if(this.getTable().getMelds().size() > index && index >= 0){
                selectedMeld = this.getTable().getMelds().get(index);
            }
        }
        return selectedMeld;
    }

    public Meld selectMeldFromTable(String message){
        Meld selectedMeld = null;
        while(selectedMeld == null){
            this.terminalView.printMessage(message);//"Which meld would you like to move the tile to? (Enter meld # or 0 to create a new meld)");
            int index = Integer.parseInt(this.terminalView.readPlayerInput());
            if(this.getTable().getMelds().size() > index && index >= 0){
                selectedMeld = this.getTable().getMelds().get(index);
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
}