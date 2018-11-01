/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * This is the TileRummy game, main class which will manage the game engine and game initialization
 */
package COMP3004.models;
import COMP3004.oberver_pattern.MultiSubject;
import COMP3004.oberver_pattern.TableObserverInterface;
import COMP3004.oberver_pattern.PlayerHandObserverInterface;

import java.util.ArrayList;

public class Scrummy extends MultiSubject // Table and Players are in superclass
{
    private Deck deck = new Deck();
    private int currentPlayerIndex = 0; // USER STARTS

    public Scrummy(){
        deck.shuffle();
        // Give players hands here
        for(int i = 0; i < this.players.length; i++){
            players[i] = new Player();
            Meld hand = new Meld();
            for(int j = 0; j < 14; j++){
                hand.add(this.deck.pop());
            }
            players[i].setHand(hand);
        }

        players[0].setName("Player");
        players[1].setName("AI 1");
        players[2].setName("AI 2");
        players[3].setName("AI 3");
    }

    public void validatePlayerMove(Table playedTable) {
        /*
         * If valid table then update game table, set the player hand, and notify observers
         * and increment current player
         * Else
         *   keep table as is and notify observers
         *   reset player hand if not
         * */

        if(playedTable != null){
            if(playedTable.isValid()){
                this.table = playedTable;
            } else {
                this.notifyObservers();
            }
        }
        this.notifyObservers();
    }

    public Meld getPlayerHandByIndex(int playerIndex){
        if( playerIndex >= 0 && playerIndex < this.players.length){
            return this.players[playerIndex].getHand();
        }
        return null;
    }

    public void notifyObservers(){
        for(TableObserverInterface observer : this.tableObservers) {
            observer.update(this.table);
        }

        int index = 0;
        for(PlayerHandObserverInterface observer : this.playerHandObservers) {
            observer.update(this.getPlayerHandByIndex(index).size(), index);
            index++;
        }
    }

    public ArrayList<TableObserverInterface> getTableObservers(){
        return this.tableObservers;
    }
    public ArrayList<PlayerHandObserverInterface> getPlayerHandObservers(){
        return this.playerHandObservers;
    }

    public Table getTable(){
        return this.table;
    }
    public void setTable(Table table) { this.table = table; }

    public Player getCurrentPlayer(){ return this.players[currentPlayerIndex]; }
    public void setCurrentPlayerIndex(int index) { this.currentPlayerIndex = index; }
    public int getCurrentPlayerIndex() { return this.currentPlayerIndex; }

    public Player[] getPlayers() { return this.players; }

    public Deck getDeck() { return this.deck; }
}