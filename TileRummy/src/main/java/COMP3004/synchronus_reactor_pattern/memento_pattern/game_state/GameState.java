package COMP3004.synchronus_reactor_pattern.memento_pattern.game_state;

import COMP3004.controllers.GameInteractionController;
import COMP3004.models.Table;

import java.util.ArrayList;

public class GameState implements java.io.Serializable {
    protected Table table;
    protected ArrayList<GameInteractionController> playerControllers;
    protected int currentPlayer;

    public GameState() {

    }

    public GameState(Table table, ArrayList<GameInteractionController> players, int currentPlayer){
        this.table = table;
        this.playerControllers = players;
        this.currentPlayer = currentPlayer;
    }

    public Table getTable() {
        return table;
    }

    public ArrayList<GameInteractionController> getPlayerControllers() {
        return playerControllers;
    }

    public int getCurrentPlayerIndex() { return this.currentPlayer; }
}
