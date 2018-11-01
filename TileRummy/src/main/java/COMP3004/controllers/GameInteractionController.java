package COMP3004.controllers;

import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.oberver_pattern.TableObserverInterface;

public class GameInteractionController implements GameInteractionInterface {
    public Meld hand;
    protected Table table;

    public GameInteractionController(){
        this.hand = new Meld();
        this.table = new Table();
    }

    public boolean playing = true;

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile) {
        if (inMeld.getTiles().contains(tile)) {
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }

    public Table play(Meld hand) {
        return null;
    }

    public Table play(Meld hand, String message) {
        return null;
    }

    public void displayWinner(String playerName) { }

    public Meld getHand() { return this.hand; }

    public void setHand(Meld hand) {
        this.hand = hand;
    }

    public void setViewControllerInstance(GameInteractionController g) { }

    // OBSERVER PATTERN CODE
    public void update(Table table) {
        this.table = table;
    }
    public Table getTable() { return this.table; }
    public void setTable(Table table) { this.table = table; }
}
