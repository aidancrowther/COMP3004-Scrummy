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
    private ArrayList<TableObserver> observers = new ArrayList<>();

    //TODO: Brittny
    public void registerObserver(TableObserver t){
        this.observers.add(t);
    }

    public void removeObserver(TableObserver t){

    }

    public void notifyObservers(){
    }

    public ArrayList<TableObserver> getObservers(){
        return this.observers;
    }

    public static void main (String[] args) {

    }
}