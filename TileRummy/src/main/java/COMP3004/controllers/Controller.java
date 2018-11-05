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

        //Make all of the "people" playing
        this.playerControllers[0] = new PlayerInteractionController(); //PLAYER
        this.playerControllers[1] = new Strategy1();
        this.playerControllers[2] = new Strategy2();
        //Special for S3 bc needs to count hands
        Strategy3 s3 = new Strategy3();
        this.playerControllers[3] = s3;
        this.playerControllers[4] = new Strategy4();
        
        for (int i = 0; i <= 4; i++){//Register everyone
            this.playerControllers[i].setPlayer(this.scrummy.getPlayers()[i]);
            this.scrummy.registerTableObserver(this.playerControllers[i]);
        }

        s3.setPlayerHandSizes(this.scrummy.getPlayers());
        this.scrummy.registerPlayerHandObserver(s3);
        this.scrummy.notifyObservers();
    }

    public Controller(String[] args){
        char viewType = args[0].charAt(0);
        this.scrummy = new Scrummy();
        this.playerControllers = new GameInteractionController[this.scrummy.getPlayers().length];

        //Make all of the "people" playing
        switch (viewType) {//Specify which view the user is using
            case 'f':
                if (args.length == 2)
                    this.playerControllers[0] = new FileInputController(args[1]); //PLAYER
                else
                this.playerControllers[0] = new FileInputController(args[1], args[3]); //PLAYER
                scrummy = new Scrummy(((FileInputController)this.playerControllers[0]).getDeck());
                break;
            case 'g':
                this.playerControllers[0] = new GraphicalViewController(); //PLAYER
                break;
            case 't':
                this.playerControllers[0] = new PlayerInteractionController(); //PLAYER
                break;
        }
        this.playerControllers[1] = new Strategy1();
        this.playerControllers[2] = new Strategy2();
        //Special for S3 bc needs to count hands
        Strategy3 s3 = new Strategy3();
        this.playerControllers[3] = s3;
        this.playerControllers[4] = new Strategy4();
        
        for (int i = 0; i <= 4; i++){//Register everyone
            this.playerControllers[i].setPlayer(this.scrummy.getPlayers()[i]);
            this.scrummy.registerTableObserver(this.playerControllers[i]);
        }

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

        boolean[] hasSkippedAfterEmpty = new boolean[this.playerControllers.length];
        for(int i = 0; i < hasSkippedAfterEmpty.length; i++){
            hasSkippedAfterEmpty[i] = false;
        }

        while(play){
            //System.out.println("Current Player: " + this.scrummy.getCurrentPlayer().getName());

            Meld playerHandCopy = new Meld();
            for(Tile t: this.scrummy.getCurrentPlayer().getHand().getTiles())
                playerHandCopy.add(t);

            //System.out.println("One");
            Table playedTable = this.playerControllers[scrummy.getCurrentPlayerIndex()].play(scrummy.getCurrentPlayer().getHand());
            //System.out.println("One - b");
            if(scrummy.getDeck().isEmpty() && playedTable.isEquivalent(this.scrummy.getTable())){
                /*
                 * Keep track of if everyone skips a turn, if so then no one will ever win and break
                 * */

                //System.out.println("two");
                System.out.println(this.getScrummy().getCurrentPlayer().getName() + " has no more moves!");
                hasSkippedAfterEmpty[this.getScrummy().getCurrentPlayerIndex()] = true;

                int skippedNum = 0;
                for(int i = 0; i < hasSkippedAfterEmpty.length; i++){
                    if(hasSkippedAfterEmpty[i]){
                        skippedNum++;
                    }
                }


                if(skippedNum == hasSkippedAfterEmpty.length){
                    //TODO: ask if want to play again
                    System.out.println("GAME OVER - DECK EMPTY AND NO VALID MOVES.");
                    break;
                }
            }

            //System.out.println("three");

            winnerIndex = this.checkPlayerMove(playedTable, playerHandCopy);
            //if(!playedTable.isValid()) break;


            //System.out.println("four");
            //print winner
            if(winnerIndex >= 0 && winnerIndex < this.scrummy.getPlayers().length){
                Player current = this.scrummy.getPlayers()[winnerIndex];
                this.playerControllers[0].displayWinner(current.getName());
                //TODO: ask if want to play again
                System.out.println("five");
                break;
            }


            System.out.println("\n\n");

            // SET NEXT PLAYER
            if(this.getScrummy().getCurrentPlayerIndex() < this.scrummy.getPlayers().length - 1){
                this.scrummy.setCurrentPlayerIndex(this.getScrummy().getCurrentPlayerIndex() + 1);

            }
            else{
                this.scrummy.setCurrentPlayerIndex(0);
            }
        }
    }

    public int checkPlayerMove(Table playedTable, Meld playerHandCopy){
        int winnerIndex = -1;
        // CHECK WHAT PLAYER DID
        if(playedTable != null){
            /*
            * Instead check if all tiles in both tables melds are equal...
            * */
            /*System.out.println("--- CHECK TABLE REFS: ---");
            System.out.println(this.scrummy.getCurrentPlayer().getName() + " Table: ");
            System.out.println("ref: " + playedTable);
            System.out.println(playedTable.prettyString());
            System.out.println("\n");
            System.out.println("Scrummy Table: ");
            System.out.println("ref: " + this.scrummy.getTable());
            System.out.println(this.scrummy.getTable().prettyString());
            System.out.println("--- ");
            System.out.println("Are tables equivalent? " + playedTable.isEquivalent(this.scrummy.getTable()));
            System.out.println("--- ");*/
            if(playedTable.isEquivalent(this.scrummy.getTable())) { // PLAYER NOT MOVE
                scrummy.getCurrentPlayer().setHand(playerHandCopy); // IN CASE PLAYER HAD TENTATIVE MELD
                //System.out.println(this.scrummy.getCurrentPlayer().getName() + " did not move. ");
                Tile t = scrummy.getDeck().pop();
                //System.out.println(this.scrummy.getCurrentPlayer().getName() + " hand b4: ");
                //System.out.println(this.playerControllers[(scrummy.getCurrentPlayerIndex())].getPlayer().getHand().toString());
                if(t != null){
                    //System.out.println(this.scrummy.getCurrentPlayer().getName() + " drew Tile: " + t.toString());
                    scrummy.getCurrentPlayer().getHand().add(t);
                    //System.out.println(this.scrummy.getCurrentPlayer().getName() + " hand in controller: ");
                    //System.out.println(this.playerControllers[(scrummy.getCurrentPlayerIndex())].getPlayer().getHand().toString());
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