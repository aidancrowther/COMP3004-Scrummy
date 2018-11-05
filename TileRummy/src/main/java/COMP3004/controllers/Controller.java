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
import COMP3004.views.TerminalView;

public class Controller
{
    private Scrummy scrummy;
    private GameInteractionController[] playerControllers;

    public Controller(){
        this.scrummy = new Scrummy();
        this.playerControllers = new GameInteractionController[this.scrummy.getPlayers().length];

        this.playerControllers[0] = new PlayerInteractionController(); //PLAYER
        this.playerControllers[0].setPlayer(this.scrummy.getPlayers()[0]);
        this.scrummy.registerTableObserver(this.playerControllers[0]);

        this.playerControllers[1] = new Strategy1();
        this.playerControllers[1].setPlayer(this.scrummy.getPlayers()[1]);
        this.scrummy.registerTableObserver(this.playerControllers[1]);

        this.playerControllers[2] = new Strategy2();
        this.playerControllers[2].setPlayer(this.scrummy.getPlayers()[2]);
        this.scrummy.registerTableObserver(this.playerControllers[2]);

        //Special for S3 bc needs to count hands
        Strategy3 s3 = new Strategy3();
        this.playerControllers[3] = s3;
        this.playerControllers[3].setPlayer(this.scrummy.getPlayers()[3]);
        this.scrummy.registerTableObserver(this.playerControllers[3]);

        this.playerControllers[4] = new Strategy4();
        this.playerControllers[4].setPlayer(this.scrummy.getPlayers()[4]);
        this.scrummy.registerTableObserver(this.playerControllers[4]);

        s3.setPlayerHandSizes(this.scrummy.getPlayers());
        this.scrummy.registerPlayerHandObserver(s3);
        this.scrummy.notifyObservers();
    }

    public Controller(boolean AIOnly){
        this.scrummy = new Scrummy(AIOnly);
        this.playerControllers = new GameInteractionController[4];

        this.playerControllers[0] = new Strategy1();
        this.playerControllers[0].setPlayer(this.scrummy.getPlayers()[0]);
        this.scrummy.registerTableObserver(this.playerControllers[0]);

        this.playerControllers[1] = new Strategy2();
        this.playerControllers[1].setPlayer(this.scrummy.getPlayers()[1]);
        this.scrummy.registerTableObserver(this.playerControllers[1]);

        //Special for S3 bc needs to count hands
        Strategy3 s3 = new Strategy3();
        this.playerControllers[2] = s3;
        this.playerControllers[2].setPlayer(this.scrummy.getPlayers()[2]);
        this.scrummy.registerTableObserver(this.playerControllers[2]);

        this.playerControllers[3] = new Strategy4();
        this.playerControllers[3].setPlayer(this.scrummy.getPlayers()[3]);
        this.scrummy.registerTableObserver(this.playerControllers[3]);

        s3.setPlayerHandSizes(this.scrummy.getPlayers());
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


            //print winner
            if(winnerIndex >= 0 && winnerIndex < this.scrummy.getPlayers().length){
                Player current = this.scrummy.getPlayers()[winnerIndex];
                this.playerControllers[0].displayWinner(current.getName());
                //TODO: ask if want to play again
                break;
            }

            if(scrummy.getDeck().isEmpty()){
                System.out.println("DECK EMPTY! :(");
                break;
            }

            System.out.println("\n\n");

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
            /*
            * Instead check if all tiles in both tables melds are equal...
            * */
            System.out.println("--- CHECK TABLE REFS: ---");
            System.out.println(this.scrummy.getCurrentPlayer().getName() + " Table: ");
            System.out.println("ref: " + playedTable);
            System.out.println(playedTable.prettyString());
            System.out.println("\n");
            System.out.println("Scrummy Table: ");
            System.out.println("ref: " + this.scrummy.getTable());
            System.out.println(this.scrummy.getTable().prettyString());
            System.out.println("--- ");
            System.out.println("Are tables equivalent? " + playedTable.isEquivalent(this.scrummy.getTable()));
            System.out.println("--- ");
            if(playedTable.isEquivalent(this.scrummy.getTable())) { // PLAYER NOT MOVE
                scrummy.getCurrentPlayer().setHand(playerHandCopy); // IN CASE PLAYER HAD TENTATIVE MELD
                System.out.println(this.scrummy.getCurrentPlayer().getName() + " did not move. ");
                Tile t = scrummy.getDeck().pop();
                System.out.println(this.scrummy.getCurrentPlayer().getName() + " hand b4: ");
                System.out.println(this.playerControllers[(scrummy.getCurrentPlayerIndex())].getPlayer().getHand().toString());
                if(t != null){
                    System.out.println(this.scrummy.getCurrentPlayer().getName() + " drew Tile: " + t.toString());
                    scrummy.getCurrentPlayer().getHand().add(t);
                    System.out.println(this.scrummy.getCurrentPlayer().getName() + " hand in controller: ");
                    System.out.println(this.playerControllers[(scrummy.getCurrentPlayerIndex())].getPlayer().getHand().toString());
                }

            } else {
                scrummy.validatePlayerMove(playedTable);
                if(!playedTable.isValid()){
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