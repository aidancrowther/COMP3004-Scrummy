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
package COMP3004;
import java.util.ArrayList;

public class Scrummy implements Subject
{
    private Table table = new Table();
    private Deck deck = new Deck();
    private Player[] players = new Player[4];

    private ArrayList<TableObserver> observers = new ArrayList<>();

    public Scrummy(){

    }

    public void registerObserver(TableObserver t){
        this.observers.add(t);
    }

    public void removeObserver(TableObserver t){
        this.observers.remove(t);
    }

    public void notifyObservers(){
        for(TableObserver observer : this.observers) {
            observer.update(this.table);
        }
    }

    public void validatePlayerMove(Table playedTable) {
        /*
        * If valid table then update game table and notify observers
        * Else
        *   keep table as is and notify observers
        *   also add the tiles the user played back into
        *   their hand
        * */
    }

    public ArrayList<TableObserver> getObservers(){
        return this.observers;
    }

    public Table getTable(){
        return this.table;
    }

    public void setTable(Table table) { this.table = table; }
}