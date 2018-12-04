package COMP3004.synchronus_reactor_pattern.memento_pattern.game_state;

import COMP3004.models.Player;
import COMP3004.models.Table;

import java.util.ArrayList;

public class GameState {
    protected Table table;
    protected ArrayList<Player> players;
    protected int currentPlayer;

    public GameState(Table table, ArrayList<Player> players, int currentPlayer){
        this.table = table;
        this.players = players;
        this.currentPlayer = currentPlayer;
    }

    public Table getTable() {
        return table;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
