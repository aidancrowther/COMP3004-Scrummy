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

import COMP3004.models.Table;
import COMP3004.views.GraphicalView;

public class GraphicalViewController extends GameInteractionController
{
    private GraphicalView gameView;

    public GraphicalViewController() {
        this.gameView = new GraphicalView();
        this.gameView.launch(GraphicalView.class);
    }

    public Table play(){
        return null;
    }

    public GraphicalView getGameView(){
        return this.gameView;
    }
}