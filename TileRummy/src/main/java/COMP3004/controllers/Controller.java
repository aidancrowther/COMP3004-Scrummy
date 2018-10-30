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
import COMP3004.models.Player;
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
        * copy players hand, pass in players actual hand
        * Set views table to table in scrummy (done by observer)
        * If table equals scrummy table,
        *   add a card to the players hand
        * else
        *   have scrummy evaluate the table and update if valid reset player hand if not
        * */
        boolean play = true;
        int winnerIndex = -1;
        while(play){
            Meld playerHandCopy = new Meld();
            for(Tile t: this.scrummy.getCurrentPlayer().getHand().getTiles()){
                playerHandCopy.add(t);
            }

            //Check current player and respond appropriately here
            Table playedTable = null;
            switch(this.getScrummy().getCurrentPlayerIndex()){
                case 0 : {  // HUMAN
                    playedTable = this.gameInteractionController.play(this.scrummy.getCurrentPlayer().getHand());
                }
                case 1 :  { // TODO: STRAT 1

                }
                case 2 : { // TODO: STRAT 2

                }
                case 3 : { // TODO: STRAT 3

                }
                default: {
                    break;
                }
            }

            if(playedTable!= null){
                // CHECK WHAT PLAYER DID
                if(playedTable.equals(this.getScrummy().getTable())) { // PLAYER NOT MOVE
                    this.getScrummy().getCurrentPlayer().getHand().add(this.getScrummy().getDeck().pop());
                } else {
                    this.getScrummy().validatePlayerMove(playedTable);
                    if(!playedTable.isValid()){
                        this.scrummy.getCurrentPlayer().setHand(playerHandCopy);
                    }
                }

                // CHECK FOR WIN
                for(int i = 0; i < this.getScrummy().getPlayers().length; i++) {
                    if(this.getScrummy().getPlayers()[i].getHand().getTiles().size() == 0){
                        play = false;
                        winnerIndex = i;
                        break;
                    }
                }
            }

            // SET NEXT PLAYER
            if(this.getScrummy().getCurrentPlayerIndex() < this.scrummy.getPlayers().length - 1){
                this.scrummy.setCurrentPlayerIndex(this.getScrummy().getCurrentPlayerIndex() + 1);
            } else {
                this.scrummy.setCurrentPlayerIndex(0);
            }
        }

        //print winner
        if(winnerIndex >= 0 && winnerIndex < this.scrummy.getPlayers().length){
            Player current = this.scrummy.getPlayers()[winnerIndex];
            this.gameInteractionController.displayWinner(current.getName());
        }
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