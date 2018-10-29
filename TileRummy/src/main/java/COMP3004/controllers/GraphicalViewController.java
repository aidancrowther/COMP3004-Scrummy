/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * This UI implementation should use JavaFX to create a window and graphically interface with the user - allowing for gameplay
 */

package COMP3004.controllers;

import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.views.GraphicalView;

public class GraphicalViewController extends GameInteractionController
{
    private GraphicalView gameView;

    public GraphicalViewController() {}

    //If press finish
    public Table play(Meld hand){
        return this.getTable();
    }

    //CHECKING UPDATE CALLS ACTUAL INSTANCE
    public void update(Table table){
        System.out.println("This is graphical game and the table was updated.");
        this.setTable(table);
    }

    public void setGameView(GraphicalView gameView) { this.gameView = gameView; }
    public GraphicalView getGameView(){
        return this.gameView;
    }
}