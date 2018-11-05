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
    private TerminalView view;
    private GameInteractionController[] playerControllers;

    public Controller(){
        this.view = new TerminalView();
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
        
        for (int i = 0; i < this.scrummy.getPlayers().length; i++){//Register everyone
            this.playerControllers[i].setPlayer(this.scrummy.getPlayers()[i]);
            this.playerControllers[i].setTerminalView(this.view);
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

        int[] hasSkippedAfterEmpty = new int[this.playerControllers.length];
        for(int i = 0; i < hasSkippedAfterEmpty.length; i++){
            hasSkippedAfterEmpty[i] = 0;
        }

        // for (GameInteractionController g : playerControllers)
        for (Player p : scrummy.getPlayers())//broadcast hands
            playerControllers[0].getTerminalView().printMessagePlain(p.getName() + "'s hand: " + p.getHand().toString());

        while(play){
            this.view.printTable(this.scrummy.getTable());

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

            System.out.println("\n");

            if(scrummy.getDeck().isEmpty() && playedTable.isEquivalent(this.scrummy.getTable())){
                System.out.println(this.getScrummy().getCurrentPlayer().getName() + " has no more moves!");
                hasSkippedAfterEmpty[this.getScrummy().getCurrentPlayerIndex()] += 1;

                int skippedNum = 0;
                for(int i = 0; i < hasSkippedAfterEmpty.length; i++){
                    if(hasSkippedAfterEmpty[i] == 2){
                        skippedNum++;
                    }
                }
                if(skippedNum == hasSkippedAfterEmpty.length){
                    //TODO: ask if want to play again
                    System.out.println("GAME OVER - DECK EMPTY AND NO VALID MOVES.");
                    break;
                }
            }


            this.view.printLine();
            this.view.printLine();
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
            if(playedTable.isEquivalent(this.scrummy.getTable())) { // PLAYER NOT MOVE
                scrummy.getCurrentPlayer().setHand(playerHandCopy); // IN CASE PLAYER HAD TENTATIVE MELD
                Tile t = scrummy.getDeck().pop();
                if(t != null){
                    scrummy.getCurrentPlayer().getHand().add(t);
                    this.view.printMessagePlain(scrummy.getCurrentPlayer().getName() + " has drawn tile " + this.view.generateTileString(t));
                } else {
                    this.view.printMessage("Out of tiles to be drawn.");
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