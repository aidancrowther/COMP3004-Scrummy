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

import COMP3004.artificial_intelligence.*;
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
    private GameInteractionController[] playerControllers;

    public Controller(){
        this.scrummy = new Scrummy();
        this.playerControllers = new GameInteractionController[this.scrummy.getPlayers().length];
        int[] handSizes = new int[this.scrummy.getPlayers().length-1];

        this.playerControllers[0] = new TerminalViewController(); //PLAYER
        this.playerControllers[1] = new Strategy1();
        this.playerControllers[2] = new Strategy2();

        //Special for S3 bc needs to count hands
        Strategy3 s3 = new Strategy3();
        this.playerControllers[3] = s3;

        this.playerControllers[4] = new Strategy4();

        int index = 0;
        for(int i = 0; i < this.scrummy.getPlayers().length; i++) {
            this.playerControllers[i].setPlayer(this.scrummy.getPlayers()[i]);
            if(i != 3){ //no for s3
                handSizes[index] = this.playerControllers[i].getPlayer().getHand().size();
                index++;
            }
            this.scrummy.registerTableObserver(this.playerControllers[i]);
        }

        s3.setPlayerHandsArray(handSizes);
        this.scrummy.registerPlayerHandObserver(s3);
        this.scrummy.notifyObservers();
    }

    public void run(boolean AIOnly){
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
            if(AIOnly && scrummy.getCurrentPlayerIndex() == 0) {
                this.scrummy.setCurrentPlayerIndex(this.getScrummy().getCurrentPlayerIndex() + 1);
                continue;
            }

            Meld playerHandCopy = new Meld();
            for(Tile t: this.scrummy.getCurrentPlayer().getHand().getTiles())
                playerHandCopy.add(t);

            Table playedTable = this.playerControllers[scrummy.getCurrentPlayerIndex()].play(scrummy.getCurrentPlayer().getHand());
            winnerIndex = this.checkPlayerMove(playedTable, playerHandCopy);
            if(!playedTable.isValid()) break;


            //print winner
            if(winnerIndex >= 0 && winnerIndex < this.scrummy.getPlayers().length){
                Player current = this.scrummy.getPlayers()[winnerIndex];
                this.playerControllers[0].displayWinner(current.getName());
                //TODO: ask if want to play again
                break;
            }

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
        if(playedTable != null){
            if(playedTable.equals(scrummy.getTable())) { // PLAYER NOT MOVE
                System.out.println("Player no move");
                scrummy.getCurrentPlayer().setHand(playerHandCopy); // IN CASE PLAYER HAD TENTATIVE MELD
                Tile t = scrummy.getDeck().pop();
                if(t != null)
                    scrummy.getCurrentPlayer().getHand().add(t);
            } else {
                System.out.println("Player move");
                scrummy.validatePlayerMove(playedTable);
                if(!playedTable.isValid()){
                    System.out.println("Player invalid");
                    scrummy.getCurrentPlayer().setHand(playerHandCopy);
                }
            }
        }

        // CHECK FOR WIN
        if(scrummy.getCurrentPlayer().getHand().getTiles().size() == 0){
            winnerIndex = scrummy.getCurrentPlayerIndex();
        }

        return winnerIndex;
    }

    public Scrummy getScrummy(){
        return this.scrummy;
    }

    public GameInteractionController getPlayerController(int id) {
        if (id > 4 || id < 0)
            if (this.playerControllers[id].getClass().isInstance(TerminalView.class))
                return null;
        return this.playerControllers[id];
    }
}