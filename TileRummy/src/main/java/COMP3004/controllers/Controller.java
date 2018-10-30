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

import COMP3004.artificial_intelligence.ArtificialIntelligence;
import COMP3004.artificial_intelligence.Strategy1;
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

    private ArtificialIntelligence[] AIs;
    private boolean[] isPlayer;

    public Controller(){
        this.scrummy = new Scrummy();
        AIs = new ArtificialIntelligence[4];
        isPlayer = new boolean[4];
        isPlayer[0] = true;
        AIs[0] = null;
        for (byte i = 1; i < 4; i++) {
            isPlayer[i] = false;
            AIs[i] = new Strategy1();
            this.scrummy.registerObserver(AIs[i]);
        }
        this.scrummy.notifyObservers();
    }

    public void run(boolean enableHumanPlayer){
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
            for(Tile t: this.scrummy.getCurrentPlayer().getHand().getTiles())
                playerHandCopy.add(t);

            //Check current player and respond appropriately here
            Table playedTable = null;
            if(isPlayer[scrummy.getCurrentPlayerIndex()]) {
                if (enableHumanPlayer) {
                    playedTable = this.gameInteractionController.play(scrummy.getCurrentPlayer().getHand());
                    winnerIndex = this.checkPlayerMove(playedTable, playerHandCopy);
                }
            }
            else{
                playedTable = this.AIs[scrummy.getCurrentPlayerIndex()].play(this.scrummy.getCurrentPlayer().getHand());
                winnerIndex = this.checkPlayerMove(playedTable, playerHandCopy);
            }

            //print winner
            if(winnerIndex >= 0 && winnerIndex < this.scrummy.getPlayers().length){
                Player current = this.scrummy.getPlayers()[winnerIndex];
                this.gameInteractionController.displayWinner(current.getName());
                //TODO: ask if want to play again
                break;
            }

            if(!enableHumanPlayer)
                play = false;

            // SET NEXT PLAYER
            if(this.getScrummy().getCurrentPlayerIndex() < this.scrummy.getPlayers().length - 1)
                this.scrummy.setCurrentPlayerIndex(this.getScrummy().getCurrentPlayerIndex() + 1);
            else
                this.scrummy.setCurrentPlayerIndex(0);
        }
    }

    public int checkPlayerMove(Table playedTable, Meld playerHandCopy){
        int winnerIndex = -1;
        // CHECK WHAT PLAYER DID
        if(playedTable.equals(scrummy.getTable())) { // PLAYER NOT MOVE
            Tile t = scrummy.getDeck().pop();
            if(t != null)
                scrummy.getCurrentPlayer().getHand().add(t);
        } else {
            scrummy.validatePlayerMove(playedTable);
            if(!playedTable.isValid())
                scrummy.getCurrentPlayer().setHand(playerHandCopy);
        }

        // CHECK FOR WIN
        if(scrummy.getCurrentPlayer().getHand().getTiles().size() == 0){
            winnerIndex = scrummy.getCurrentPlayerIndex();
        }

        return winnerIndex;
    }

    // FOR TESTING
    /*public void run(String message){
        Table playedTable = this.strategy1.play(this.scrummy.getCurrentPlayer().getHand());
    }*/

    // TODO: clean up if possible
    public void setInteractionType(char selection){
        if(selection == 't') {
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
        GraphicalView.setController(graphicalInteractionController);
        this.graphicalView = new GraphicalView();
        this.graphicalInteractionController.setGameView(graphicalView);
        this.graphicalView.initInterface(args);
    }

    public GameInteractionController getInteractionController(){
        return this.gameInteractionController;
    }

    public Scrummy getScrummy(){
        return this.scrummy;
    }

    public ArtificialIntelligence getAI(int id) {
        if (id > 4 || id < 0)
            if (isPlayer[id])
                return null;
        return AIs[id];
    }
}