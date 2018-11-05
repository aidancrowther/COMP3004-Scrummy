package COMP3004.controllers;

import COMP3004.models.Meld;
import COMP3004.models.Player;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.oberver_pattern.TableObserverInterface;
import COMP3004.views.TerminalView;

public class GameInteractionController implements TableObserverInterface {
    protected TerminalView terminalView;
    protected Table table;
    protected Player player;

    public GameInteractionController(){
        this.table = new Table();
        this.player = new Player();
    }

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile) {
        if (inMeld.getTiles().contains(tile)) {
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }

    public Table getTableCopy(Table t){
        Table output = new Table();//table; no set yet - want to make a copy
        int i = 0;
        for(Meld m : t.getMelds()) {
            if(i != 0){
                output.add(m);
            }
            i++;
        }
        return output;
    }

    public Table play(Meld hand) {
        return null;
    }

    public void displayWinner(String playerName) { }

    // OBSERVER PATTERN CODE
    public void update(Table table) {
        this.table = table;
    }
    public Table getTable() { return this.table; }
    public void setTable(Table table) { this.table = table; }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
