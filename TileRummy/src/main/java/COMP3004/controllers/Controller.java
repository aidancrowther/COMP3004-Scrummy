/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * The controller class manages any of the observer interactions between UI and Scrummy or view and model respectively
 */

package COMP3004.controllers;

import COMP3004.models.Scrummy;
import COMP3004.models.Table;
import COMP3004.models.Meld;
import COMP3004.models.Tile;
import COMP3004.views.GraphicalView;
import COMP3004.views.TerminalView;
import COMP3004.controllers.TerminalViewController;

public class Controller
{
    private Scrummy scrummy;
    private GraphicalView graphicalView;
    private GraphicalViewController graphicalInteractionController;
    private GameInteractionController gameInteractionController;

    public Controller(){
        this.scrummy = new Scrummy();
    }

    public void run(){
        /*
        * While everyone has cards in their hand...
        * Set view's hand to current players hand in scrummy
        * copy players hand, pass in players hand?
        * Set views table to table in scrummy (done by observer)
        * If table equals scrummy table,
        *   add a card to the players hand
        * else
        *   have scrummy evaluate the table and update if valid
        * */
        Table playedTable = this.gameInteractionController.play(this.scrummy.getCurrentPlayer().getHand());

    }

    // FOR TESTING
    public void run(String message){
        Table playedTable = this.gameInteractionController.play(this.scrummy.getCurrentPlayer().getHand(), message);
    }

    // TODO: clean up if possible
    public void setInteractionType(String selection){
        if(selection.equals("t")) {
            this.gameInteractionController = new TerminalViewController();
            //this.terminalInteractionController = new TerminalViewController();
            //this.gameInteractionController = this.terminalInteractionController; //to access controller generally
        } else {
            this.graphicalInteractionController = new GraphicalViewController();
            this.gameInteractionController = this.graphicalInteractionController; //to access controller generally
        }

        this.scrummy.registerObserver(this.gameInteractionController);
        this.scrummy.notifyObservers();
    }

    public void launchGraphicalView(String[] args){
        // SET UP 2 WAY COMMUNICATION THEN LAUNCH GUI
        this.graphicalView = new GraphicalView();
        this.graphicalView.setController(graphicalInteractionController);
        this.graphicalInteractionController.setGameView(graphicalView);
        this.graphicalView.initInterface(args);
    }

    public GameInteractionController getInteractionController(){
        return this.gameInteractionController;
    }

    public Scrummy getScrummy(){
        return this.scrummy;
    }
}