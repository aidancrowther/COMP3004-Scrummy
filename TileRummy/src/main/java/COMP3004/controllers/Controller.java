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

import COMP3004.GUI.GraphicalView;
import COMP3004.artificial_intelligence.*;
import COMP3004.models.Scrummy;
import COMP3004.models.Table;
import COMP3004.models.Meld;
import COMP3004.models.Tile;
import COMP3004.models.Player;
import COMP3004.terminal.TerminalView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Controller
{
    private Scrummy scrummy;
    private TerminalView view;
    //private GameInteractionController[] playerControllers;

    private ArrayList<GameInteractionController> playerControllers;

    protected GraphicalView graphicalView;
    protected Table table;

    protected int currentPlayerIndex = 0;

    // Add player method

    public Controller(){
        this.view = new TerminalView();
        this.scrummy = new Scrummy();
        this.playerControllers = new ArrayList<GameInteractionController>();
    }

    public Controller(String[] args){
        char viewType = args[0].charAt(0);
        this.scrummy = new Scrummy();
        this.playerControllers = new ArrayList<GameInteractionController>();

        //Make all of the "people" playing
        switch (viewType) {//Specify which view the user is using
            case 'f':
                if (args.length == 2)
                    this.playerControllers.add(new FileInputController(args[1])); //PLAYER
                else
                    this.playerControllers.add(new FileInputController(args[1], args[3])); //PLAYER
                scrummy = new Scrummy(((FileInputController)this.playerControllers.get(0)).getDeck());
                break;
            case 'g':
                //this.playerControllers[0] = new GraphicalViewController(); //PLAYER
                break;
            case 't':
                this.playerControllers.add(new PlayerInteractionController()); //PLAYER
                break;
        }
        this.playerControllers.add(new Strategy1());
        this.playerControllers.add(new Strategy2());
        Strategy3 s3 = new Strategy3();
        this.playerControllers.add(s3);
        this.playerControllers.add(new Strategy4());

        for (int i = 0; i <= 4; i++){//Register everyone
            this.playerControllers.get(i).setPlayer(this.scrummy.getPlayers().get(i));
            this.scrummy.registerTableObserver(this.playerControllers.get(i));
        }

        s3.setPlayerHandSizes(this.scrummy.getPlayers());
        this.scrummy.registerPlayerHandObserver(s3);
        this.scrummy.notifyObservers();
    }


    public void finishTurn(){
        int winnerIndex = -1;
        if(this.playerControllers.get(this.currentPlayerIndex) instanceof PlayerInteractionController){
            winnerIndex = this.checkPlayerMoveGUI(this.graphicalView.getTableBefore(), this.graphicalView.getHandBefore());
            //System.out.println("Hand: " + this.graphicalView.getHandBefore());
            //System.out.println(this.graphicalView.getTableBefore());
            if(winnerIndex != -1){
                System.out.println(getPlayerController(winnerIndex).getPlayer().getName() + " won!!");
            } else {
                //System.out.println(this.currentPlayerIndex + " No win next player.");
                if(currentPlayerIndex == this.playerControllers.size()-1){
                    this.currentPlayerIndex = 0;
                } else {
                    this.currentPlayerIndex++;
                }
                //System.out.println(this.currentPlayerIndex);
                this.graphicalView.setCurrentPlayerIndex(this.currentPlayerIndex);
                this.scrummy.setCurrentPlayerIndex(this.currentPlayerIndex);
            }
        } else {
            Meld playerHandCopy = new Meld();
            for(Tile t: this.scrummy.getCurrentPlayer().getHand().getTiles())
                playerHandCopy.add(t);

            Table playedTable = this.playerControllers.get(this.currentPlayerIndex).play(this.playerControllers.get(this.currentPlayerIndex).getPlayer().getHand());
            //System.out.println("AI TABLE: " + playedTable);
            winnerIndex = this.checkPlayerMove(playedTable, playerHandCopy);

            //print winner
            if(winnerIndex >= 0 && winnerIndex < this.scrummy.getPlayers().size()){
                Player current = this.scrummy.getPlayers().get(winnerIndex);
                this.playerControllers.get(0).displayWinner(current.getName());
            } else {
                //System.out.println(this.currentPlayerIndex + " No win next player.");
                if(currentPlayerIndex == this.playerControllers.size()-1){
                    this.currentPlayerIndex = 0;
                } else {
                    this.currentPlayerIndex++;
                }
                //System.out.println(this.currentPlayerIndex);
                this.graphicalView.setCurrentPlayerIndex(this.currentPlayerIndex);
                this.scrummy.setCurrentPlayerIndex(this.currentPlayerIndex);
            }

            System.out.println("\n");
        }
    }

    public int checkPlayerMoveGUI(Table tableBefore, Meld handBefore){
        int winnerIndex = -1;
        //System.out.println("Scrummy table bf set: " + this.scrummy.getTable().toString());
        //System.out.println("saved table: " + tableBefore.toString());
        // CHECK WHAT PLAYER DID
        if(tableBefore != null){
            /* Instead check if all tiles in both tables melds are equal...
             * */
            if(!scrummy.getTable().isValid()){
                scrummy.setTable(tableBefore);
                scrummy.getPlayers().get(this.currentPlayerIndex).setHand(handBefore);
                /*Tile t = scrummy.getDeck().pop();
                scrummy.getPlayers().get(this.currentPlayerIndex).getHand().add(t);*/
                this.popTileToCurrentUserHand();
                scrummy.notifyObservers();
            }
            else if(tableBefore.isEquivalent(this.scrummy.getTable())) { // PLAYER NOT MOVE
                scrummy.getCurrentPlayer().setHand(handBefore); // IN CASE PLAYER HAD TENTATIVE MELD
                this.popTileToCurrentUserHand();
                /*Tile t = scrummy.getDeck().pop();
                if(t != null){
                    scrummy.getCurrentPlayer().getHand().add(t);
                    playerControllers.get(0).getTerminalView().printMessagePlain(playerControllers.get(scrummy.getCurrentPlayerIndex()).getPlayer().getName() + " drew from the deck tile: " + t.toString());
                } else {
                    if(this.view != null) {
                        this.view.printMessage("Out of tiles to be drawn.");
                    }
                }*/
            }
        }

        // CHECK FOR WIN
        if(scrummy.getCurrentPlayer().getHand().getTiles().size() == 0){
            winnerIndex = scrummy.getCurrentPlayerIndex();
        }
        //System.out.println("Scrummy table aft set: " + this.scrummy.getTable().toString());

        return winnerIndex;
    }



    public int checkPlayerMove(Table playedTable, Meld playerHandCopy){
        int winnerIndex = -1;
        // CHECK WHAT PLAYER DID
        if(playedTable != null){
            /*
             * Instead check if all tiles in both tables melds are equal...
             * */
            if(playedTable.isEquivalent(this.scrummy.getTable())) { // PLAYER NOT MOVE
                //System.out.println("Equivalent");
                scrummy.getCurrentPlayer().setHand(playerHandCopy); // IN CASE PLAYER HAD TENTATIVE MELD
                this.popTileToCurrentUserHand();
                /*Tile t = scrummy.getDeck().pop();
                if(t != null){
                    scrummy.getPlayers().get(this.currentPlayerIndex).getHand().add(t);
                    //playerControllers.get(0).getTerminalView().printMessagePlain(playerControllers.get(scrummy.getCurrentPlayerIndex()).getPlayer().getName() + " drew from the deck tile: " + t.toString());
                    //System.out.println(this.scrummy.getCurrentPlayer().getName() + " hand in controller: ");
                    //System.out.println(this.playerControllers[(scrummy.getCurrentPlayerIndex())].getPlayer().getHand().toString());
                    if(this.view != null) {
                        this.view.printMessagePlain(scrummy.getCurrentPlayer().getName() + " has drawn tile " + this.view.generateTileString(t));
                    }
                } else {
                    if(this.view != null) {
                        this.view.printMessage("Out of tiles to be drawn.");
                    }
                }*/

            } else {
                scrummy.validatePlayerMove(playedTable);
                //System.out.println("vallid");
                if(!playedTable.isValid()){
                    scrummy.getPlayers().get(this.currentPlayerIndex).setHand(playerHandCopy);
                    //scrummy.getCurrentPlayer().setHand(playerHandCopy);
                    this.popTileToCurrentUserHand();
                }
            }
        }

        // CHECK FOR WIN
        if(scrummy.getCurrentPlayer().getHand().getTiles().size() == 0){
            winnerIndex = scrummy.getCurrentPlayerIndex();
        }

        return winnerIndex;
    }

    public void popTileToCurrentUserHand(){
        if(this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles() != null){
            this.popFromDeckRigged();
        } else {
            Tile t = scrummy.getDeck().pop();
            if(t != null){
                scrummy.getPlayers().get(this.currentPlayerIndex).getHand().add(t);
            }
        }
    }

    public void popFromDeckRigged(){
        //When user is popping a card off the stack, if they have a rigged stack,
        //tell them to pop/push the cards off the deck until the curr rigged index (first tile in array) tile is found
        //delete first tile in push all popped tiles onto deck.
        ArrayList<Tile> poppedtiles = new ArrayList<Tile>();
        for(Tile currRiggedTile : this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles()){
            Tile deckTile = this.scrummy.getDeck().pop();
            if(deckTile != null){
                if(currRiggedTile.getColour() == deckTile.getColour()
                        && currRiggedTile.getValue() == deckTile.getValue()){
                    this.scrummy.getPlayers().get(this.currentPlayerIndex).getHand().add(deckTile);
                    break;
                } else {
                    poppedtiles.add(deckTile); //oldest to newest arraylist
                }
            }
        }

        Collections.reverse(poppedtiles); //add back in original order
        for(Tile t : poppedtiles){
            this.scrummy.getDeck().push(t);
        }
    }


    /*//Make all of the "people" playing
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
    this.scrummy.notifyObservers();*/

    public void addPlayer(int type){
        if(type == 0) {
            PlayerInteractionController p = new PlayerInteractionController();
            Player player = this.scrummy.addNewPlayer("Human");
            p.setPlayer(player);
            this.playerControllers.add(p);
            this.scrummy.registerTableObserver(p);
        } else if(type == 1) {
            Strategy1 s = new Strategy1();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy1");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        } else if (type == 2) {
            Strategy2 s = new Strategy2();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy2");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        } else if (type == 3) {
            Strategy3 s = new Strategy3();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy3");
            s.setPlayer(player);
            s.setPlayerHandSizes(this.scrummy.getPlayers());
            this.scrummy.registerPlayerHandObserver(s);
            this.scrummy.registerTableObserver(s);
        } else {
            Strategy4 s = new Strategy4();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy4");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        }
        this.scrummy.notifyObservers();
    }

    public void addPlayer(String type){
        if(type.equals("Human")) {
            PlayerInteractionController p = new PlayerInteractionController();
            Player player = this.scrummy.addNewPlayer("Human");
            p.setPlayer(player);
            this.playerControllers.add(p);
            this.scrummy.registerTableObserver(p);
        } else if(type.equals("AI Strategy 1")) {
            Strategy1 s = new Strategy1();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy1");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        } else if (type.equals("AI Strategy 2")) {
            Strategy2 s = new Strategy2();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy2");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        } else if (type.equals("AI Strategy 3")) {
            Strategy3 s = new Strategy3();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy3");
            s.setPlayer(player);
            s.setPlayerHandSizes(this.scrummy.getPlayers());
            this.scrummy.registerPlayerHandObserver(s);
            this.scrummy.registerTableObserver(s);
        } else {
            Strategy4 s = new Strategy4();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy4");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        }
        this.scrummy.notifyObservers();
    }

    public Scrummy getScrummy(){
        return this.scrummy;
    }

    public GameInteractionController getPlayerController(int id) {
        if (id >= this.playerControllers.size() || id < 0)//if (this.playerControllers.get(id).getClass().isInstance(TerminalView.class))
            return null;
        return this.playerControllers.get(id);
    }
    public ArrayList<GameInteractionController> getPlayerControllers() {
        return this.playerControllers;
    }

    public GraphicalView getGraphicalView() {
        return graphicalView;
    }

    public void setGraphicalView(GraphicalView graphicalView) {
        this.graphicalView = graphicalView;
    }

    public void setCurrentPlayerIndex(int index){
        this.currentPlayerIndex = index;
        this.scrummy.setCurrentPlayerIndex(this.currentPlayerIndex);
        this.graphicalView.setCurrentPlayerIndex(this.currentPlayerIndex);
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

        int[] hasSkippedAfterEmpty = new int[this.playerControllers.size()];
        for(int i = 0; i < hasSkippedAfterEmpty.length; i++){
            hasSkippedAfterEmpty[i] = 0;
        }

        // for (GameInteractionController g : playerControllers)
        for (Player p : scrummy.getPlayers())//broadcast hands
            playerControllers.get(0).getTerminalView().printMessagePlain(p.getName() + "'s hand: " + p.getHand().toString());

        while(play){
            if(this.view != null && this.scrummy.getTable() != null){
                this.view.printTable(this.scrummy.getTable());
            }

            Meld playerHandCopy = new Meld();
            for(Tile t: this.scrummy.getCurrentPlayer().getHand().getTiles())
                playerHandCopy.add(t);

            Table playedTable = this.playerControllers.get(scrummy.getCurrentPlayerIndex()).play(scrummy.getCurrentPlayer().getHand());
            winnerIndex = this.checkPlayerMove(playedTable, playerHandCopy);

            //print winner
            if(winnerIndex >= 0 && winnerIndex < this.scrummy.getPlayers().size()){
                Player current = this.scrummy.getPlayers().get(winnerIndex);
                this.playerControllers.get(0).displayWinner(current.getName());
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

            if(this.view != null){
                this.view.printLine();
                this.view.printLine();
            }
            // SET NEXT PLAYER
            if(this.getScrummy().getCurrentPlayerIndex() < this.scrummy.getPlayers().size() - 1)
                this.scrummy.setCurrentPlayerIndex(this.getScrummy().getCurrentPlayerIndex() + 1);
            else
                this.scrummy.setCurrentPlayerIndex(0);
        }
    }

}
    /*public Controller(boolean AIOnly){
        this.view = new TerminalView();
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
    }*/