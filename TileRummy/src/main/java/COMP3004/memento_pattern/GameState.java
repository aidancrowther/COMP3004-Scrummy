package COMP3004.memento_pattern;

import COMP3004.models.Meld;
import COMP3004.models.Table;

public class GameState {
    protected Table table;
    protected Meld playerHand;

    public GameState(Table table, Meld playerHand){
        this.table = table;
        this.playerHand = playerHand;
    }

    public Table getTable() {
        return table;
    }

    public Meld getPlayerHand() {
        return playerHand;
    }
}
