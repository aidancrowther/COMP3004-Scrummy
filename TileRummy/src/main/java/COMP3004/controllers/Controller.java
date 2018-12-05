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
import COMP3004.memento_pattern.CareTaker;
import COMP3004.memento_pattern.GameState;
import COMP3004.memento_pattern.Originator;
import COMP3004.models.Scrummy;
import COMP3004.models.Table;
import COMP3004.models.Meld;
import COMP3004.models.Tile;
import COMP3004.models.Player;
import COMP3004.player_factory_pattern.PlayerFactory;
import COMP3004.synchronus_reactor_pattern.NetworkedController;
import COMP3004.terminal.TerminalView;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Controller
{
    //TODO: add if deck empty and each player has not played in a row, end game
    private Scrummy scrummy;
    private TerminalView view;
    //private GameInteractionController[] playerControllers;
    private Originator originator = new Originator();
    private CareTaker careTaker = new CareTaker();

    private ArrayList<GameInteractionController> playerControllers;

    protected GraphicalView graphicalView;
    protected Table table;

    protected int currentPlayerIndex = 0;

    protected int winner = -1;

    protected ArrayList<GameInteractionController> playersWhoHaventMoved = new ArrayList<>();

    String gameType;

    NetworkedController networkController;

    // Add player method

    public Controller(){
        this.view = new TerminalView();
        this.scrummy = new Scrummy();
        this.playerControllers = new ArrayList<GameInteractionController>();
    }

    public Controller(boolean AIOnly){
        this.view = new TerminalView();
        this.scrummy = new Scrummy(AIOnly);
        this.playerControllers = new ArrayList<GameInteractionController>();
        this.addPlayer(0);
        this.addPlayer(1);
        this.addPlayer(2);
        this.addPlayer(3);
        this.addPlayer(4);
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

    //NETWORKING METHODS
    public void setupHostedGame(){
        this.networkController = new NetworkedController(this);
    }





    // FOR GUI GAME FLOW
    public void finishTurn(){
        System.out.println("b4 : " + this.graphicalView.getTableBefore());
        this.saveState(this.graphicalView.getTableBefore(), this.graphicalView.getHandBefore());
        this.graphicalView.draw();

        if(!(this.isPlayerHuman())) {
            this.playerControllers.get(this.currentPlayerIndex).setTable(this.scrummy.getTable());
            Table playedTable = this.playerControllers.get(this.currentPlayerIndex).play(this.playerControllers.get(this.currentPlayerIndex).getPlayer().getHand());
            this.scrummy.setTable(playedTable);
        }

        if(this.graphicalView.getTableBefore() != null){
            this.graphicalView.setTableDiff(this.scrummy.getTable().getDiff(this.getState().getTable()));
            System.out.println("diff : " + this.graphicalView.getTableDiff());
        }

        this.graphicalView.draw();
        System.out.print("after: " + scrummy.getTable());

        this.winner = this.checkPlayerMoveGUI();
        this.graphicalView.draw();

        if(this.winner >= 0){
            System.out.println(getPlayerController(this.winner).getPlayer().getName() + " won!!");
            this.graphicalView.displayWin();
        } else if(this.winner == -1)  {
            int prevPlayerIndex = this.currentPlayerIndex;

            if(this.currentPlayerIndex == this.playerControllers.size()-1){
                this.currentPlayerIndex = 0;
            } else {
                this.currentPlayerIndex++;
            }

            this.scrummy.setCurrentPlayerIndex(this.currentPlayerIndex);

            //Only do this for local games
            if(this.networkController == null && !(this.isPlayerHuman())) {
                this.graphicalView.startAILoop();
            }
            this.graphicalView.setCurrentPlayerIndex(this.currentPlayerIndex);
            this.graphicalView.draw();
        } else {
            //a draw
            this.graphicalView.displayDraw();
        }

        if(this.networkController != null){
            this.networkController.sendGameState();
        }
    }

    public void saveState(Table saveTable, Meld saveMeld){
        originator.setState(new GameState(saveTable, saveMeld));
        careTaker.add(originator.saveStateToMemento());
        System.out.println("Current State: " + originator.getState());
    }

    public GameState getState(){
        if(careTaker.isEmpty()){
            return null;
        }
        return careTaker.getLastSave().getState();
    }

    public int checkPlayerMoveGUI(){
        int winnerIndex = -1;
        // CHECK WHAT PLAYER DID
        GameState stateBeforeTurn = this.getState();
        if(stateBeforeTurn != null){
            /* Instead check if all tiles in both tables melds are equal...
             * */
            if(this.playerControllers.get(this.currentPlayerIndex).getScore() < 30 // PLAYER DIDN'T HAVE ENOUGH POINTS
                    || this.scrummy.getTable().isEquivalent(stateBeforeTurn.getTable())) { // PLAYER NOT MOVE
                if(this.checkForEmptyDeckCase()){
                    winnerIndex = -2;
                }
                this.scrummy.setTable(stateBeforeTurn.getTable()); //IN CASE PLAYER JUST MMOVED A TILE
                scrummy.getCurrentPlayer().setHand(stateBeforeTurn.getPlayerHand()); // IN CASE PLAYER HAD TENTATIVE MELD
                this.popTileToCurrentUserHand();
            } else
            if(!scrummy.getTable().isValid()){
                scrummy.setTable(stateBeforeTurn.getTable());
                scrummy.getPlayers().get(this.currentPlayerIndex).setHand(stateBeforeTurn.getPlayerHand());
                this.popTileToCurrentUserHand();
                scrummy.notifyObservers();
            }
        }

        // CHECK FOR WIN
        System.out.println( "playe rhand size: " + this.playerControllers.get(this.currentPlayerIndex).getPlayer().getHand() );
        if(this.playerControllers.get(this.currentPlayerIndex).getPlayer().getHand().getTiles().size() == 0){
            winnerIndex = this.currentPlayerIndex;
        }

        return winnerIndex;
    }


    public boolean checkForEmptyDeckCase(){
        if(scrummy.getDeck().isEmpty()){
            System.out.print("a");
            if(playersWhoHaventMoved.contains(this.playerControllers.get(this.currentPlayerIndex))){
                System.out.print("b");
                if(this.playerControllers.get(this.currentPlayerIndex) == this.playersWhoHaventMoved.get(0)){
                    //full circle
                    System.out.print("Full circle");
                    return true;
                }
            } else {
                System.out.print("c");
                playersWhoHaventMoved.add(this.playerControllers.get(this.currentPlayerIndex));
            }
        }
        System.out.print("end");
        return false;
    }

    public void popTileToCurrentUserHand(){
        if(this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles() != null){
            System.out.println(this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles());
            this.popFromDeckRigged();
        } else {
            this.popFromDeck();
        }
    }

    public void popFromDeck(){
        Tile t = scrummy.getDeck().pop();
        if(t != null){
            scrummy.getPlayers().get(this.currentPlayerIndex).getHand().add(t);
        }
    }

    public void popFromDeckRigged(){
        //When user is popping a card off the stack, if they have a rigged stack,
        //tell them to pop/push the cards off the deck until the curr rigged index (first tile in array) tile is found
        //delete first tile in push all popped tiles onto deck.

        //for(Tile currRiggedTile : this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles()){
        System.out.println("Rigged: " + this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles().toArray());
        if(!this.scrummy.getDeck().isEmpty()){

            if(this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles().size() > 0){
                ArrayList<Tile> poppedtiles = new ArrayList<Tile>();
                Tile currRiggedTile = this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles().get(0);

                while(true) {
                    Tile deckTile = this.scrummy.getDeck().pop();
                    System.out.println("dt: " + deckTile);
                    if (deckTile != null) {
                        if (currRiggedTile.getColour() == deckTile.getColour()
                                && currRiggedTile.getValue() == deckTile.getValue()) {
                            this.scrummy.getPlayers().get(this.currentPlayerIndex).getHand().add(deckTile);
                            this.playerControllers.get(this.currentPlayerIndex).getRiggedTiles().remove(0);
                            break;
                        } else {
                            poppedtiles.add(deckTile); //oldest to newest arraylist
                        }
                    } else {
                        break;
                    }
                }

                Collections.reverse(poppedtiles); //add back in original order
                for(Tile t : poppedtiles){
                    this.scrummy.getDeck().push(t);
                }
            } else {
                //Tile t = scrummy.getDeck().pop();
                //if(t != null){

                ArrayList<Tile> poppedtiles = new ArrayList<Tile>();
                while(true) {
                    Tile deckTile = this.scrummy.getDeck().pop();
                    System.out.println("dt: " + deckTile);
                    if (deckTile != null) {
                        boolean found = false;
                        for(GameInteractionController playerController: this.playerControllers){
                            for(Tile currRiggedTile : playerController.getRiggedTiles()){
                                if (currRiggedTile.getColour() == deckTile.getColour()
                                        && currRiggedTile.getValue() == deckTile.getValue()) {
                                    found = true;
                                }
                            }
                        }
                        if(!found){
                            scrummy.getPlayers().get(this.currentPlayerIndex).getHand().add(deckTile);
                            break;
                        } else {
                            poppedtiles.add(deckTile);
                        }
                    } else {
                        break;
                    }
                }
                Collections.reverse(poppedtiles); //add back in original order
                for(Tile t : poppedtiles){
                    this.scrummy.getDeck().push(t);
                }
                //}
            }

        }
    }


    public void setupGameByType(){
        if(this.gameType != null){
            PlayerFactory factory = new PlayerFactory();
            if(this.gameType.equals("Scenario 1")){
                Player p1 = factory.FactoryMethod(0);
                Player p2 = factory.FactoryMethod(1);


                Strategy1 s = new Strategy1();
                this.playerControllers.add(s);
                p1.setName("player a");
                s.setPlayer(p1);
                s.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p1);
                this.scrummy.registerTableObserver(s);

                Strategy1 s2 = new Strategy1();
                this.playerControllers.add(s2);
                p2.setName("player b");
                s2.setPlayer(p2);
                s2.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p2);
                this.scrummy.registerTableObserver(s2);

            } else if(this.gameType.equals("Scenario 2")){
                Player p1 = factory.FactoryMethod(2);
                Player p2 = factory.FactoryMethod(3);


                Strategy1 s = new Strategy1();
                this.playerControllers.add(s);
                p1.setName("player a");
                s.setPlayer(p1);
                s.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p1);
                this.scrummy.registerTableObserver(s);

                Strategy2 s2 = new Strategy2();
                this.playerControllers.add(s2);
                p2.setName("player b");
                s2.setPlayer(p2);
                s2.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p2);
                this.scrummy.registerTableObserver(s2);
            } else {
                Player p1 = factory.FactoryMethod(4);
                Player p2 = factory.FactoryMethod(5);
                Player p3 = factory.FactoryMethod(6);
                Player p4 = factory.FactoryMethod(7);


                Strategy1 s = new Strategy1();
                this.playerControllers.add(s);
                p1.setName("player a");
                s.setPlayer(p1);
                s.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p1);
                this.scrummy.registerTableObserver(s);

                Strategy1 s2 = new Strategy1();
                this.playerControllers.add(s2);
                p2.setName("player b");
                s2.setPlayer(p2);
                s2.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p2);
                this.scrummy.registerTableObserver(s2);

                Strategy2 s3 = new Strategy2();
                this.playerControllers.add(s3);
                p3.setName("player c");
                s3.setPlayer(p3);
                s3.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p3);
                this.scrummy.registerTableObserver(s3);

                Strategy3 s4 = new Strategy3();
                this.playerControllers.add(s4);
                p4.setName("player d");
                s4.setPlayer(p4);
                s4.setGUI(this.graphicalView);
                this.scrummy.getPlayers().add(p4);
                this.scrummy.registerTableObserver(s4);
            }
        }
    }


    public void addPlayer(int type){
        if(type == 0) {
            PlayerInteractionController p = new PlayerInteractionController();
            Player player = this.scrummy.addNewPlayer("Human");
            p.setPlayer(player);
            if(this.graphicalView != null){
                p.setGUI(this.graphicalView);
            } else {
                p.setTerminalView(this.view);
            }
            this.playerControllers.add(p);
            this.scrummy.registerTableObserver(p);
        } else if(type == 1) {
            Strategy1 s = new Strategy1();
            this.playerControllers.add(s);
            if(this.graphicalView != null){
                s.setGUI(this.graphicalView);
            } else {
                s.setTerminalView(this.view);
            }
            Player player = this.scrummy.addNewPlayer("Strategy1");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        } else if (type == 2) {
            Strategy2 s = new Strategy2();
            if(this.graphicalView != null){
                s.setGUI(this.graphicalView);
            } else {
                s.setTerminalView(this.view);
            }
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy2");
            s.setPlayer(player);
            this.scrummy.registerTableObserver(s);
        } else if (type == 3) {
            Strategy3 s = new Strategy3();
            if(this.graphicalView != null){
                s.setGUI(this.graphicalView);
            } else {
                s.setTerminalView(this.view);
            }
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy3");
            s.setPlayer(player);
            s.setPlayerHandSizes(this.scrummy.getPlayers());
            this.scrummy.registerPlayerHandObserver(s);
            this.scrummy.registerTableObserver(s);
        } else {
            Strategy4 s = new Strategy4();
            if(this.graphicalView != null){
                s.setGUI(this.graphicalView);
            } else {
                s.setTerminalView(this.view);
            }
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
            p.setGUI(this.graphicalView);
            this.playerControllers.add(p);
            this.scrummy.registerTableObserver(p);
        } else if(type.equals("AI Strategy 1")) {
            Strategy1 s = new Strategy1();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy1");
            s.setPlayer(player);
            s.setGUI(this.graphicalView);
            this.scrummy.registerTableObserver(s);
        } else if (type.equals("AI Strategy 2")) {
            Strategy2 s = new Strategy2();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy2");
            s.setPlayer(player);
            s.setGUI(this.graphicalView);
            this.scrummy.registerTableObserver(s);
        } else if (type.equals("AI Strategy 3")) {
            Strategy3 s = new Strategy3();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy3");
            s.setPlayer(player);
            s.setGUI(this.graphicalView);
            s.setPlayerHandSizes(this.scrummy.getPlayers());
            this.scrummy.registerPlayerHandObserver(s);
            this.scrummy.registerTableObserver(s);
        } else {
            Strategy4 s = new Strategy4();
            this.playerControllers.add(s);
            Player player = this.scrummy.addNewPlayer("Strategy4");
            s.setPlayer(player);
            s.setGUI(this.graphicalView);
            this.scrummy.registerTableObserver(s);
        }
        this.scrummy.notifyObservers();
    }

    public Scrummy getScrummy(){
        return this.scrummy;
    }

    public GameInteractionController getPlayerController(int id) {
        if (id >= this.playerControllers.size() || id < 0)
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
        System.out.println("SET INDEX: " + index);
        System.out.println("NUM PLAYERS: " + this.playerControllers.size());
        this.currentPlayerIndex = index;
        this.scrummy.setCurrentPlayerIndex(this.currentPlayerIndex);
        //this.graphicalView.setCurrentPlayerIndex(this.currentPlayerIndex);
    }

    //FOR TERMINAL VIEW ONLY
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

            this.saveState(playedTable, playerHandCopy);
            winnerIndex = this.checkPlayerMove();

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

    public int checkPlayerMove(){
        System.out.println("s: " + this.scrummy.getTable());
        int winnerIndex = -1;
        // CHECK WHAT PLAYER DID
        GameState stateAfterTurn = this.getState();
        if(stateAfterTurn != null){
            /*
             * Instead check if all tiles in both tables melds are equal...
             * */
            System.out.println("a: " + stateAfterTurn.getTable());
            if(stateAfterTurn.getTable().isEquivalent(this.scrummy.getTable())) { // PLAYER NOT MOVE
                System.out.println("NO move");
                if(this.checkForEmptyDeckCase()){
                    winnerIndex = -2;
                }
                scrummy.getCurrentPlayer().setHand(stateAfterTurn.getPlayerHand()); // IN CASE PLAYER HAD TENTATIVE MELD
                this.popTileToCurrentUserHand();
            } else {
                scrummy.validatePlayerMove(stateAfterTurn.getTable());
                if(!stateAfterTurn.getTable().isValid()){
                    System.out.println("NOT VALID");
                    scrummy.getPlayers().get(this.currentPlayerIndex).setHand(stateAfterTurn.getPlayerHand());
                    this.popTileToCurrentUserHand();
                }
            }
        }

        // CHECK FOR WIN
        System.out.println( "playe rhand size: " + this.playerControllers.get(this.currentPlayerIndex).getPlayer().getHand() );
        if(this.playerControllers.get(this.currentPlayerIndex).getPlayer().getHand().getTiles().size() == 0){
            winnerIndex = this.currentPlayerIndex;
        }

        return winnerIndex;
    }

    public boolean isPlayerHuman(){
        return (this.playerControllers.get(this.currentPlayerIndex) instanceof PlayerInteractionController);
    }

    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    public int getWinner() {
        return winner;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
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