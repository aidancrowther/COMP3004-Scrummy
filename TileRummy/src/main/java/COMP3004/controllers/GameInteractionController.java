package COMP3004.controllers;

import COMP3004.GUI.GraphicalView;
import COMP3004.models.Meld;
import COMP3004.models.Player;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.oberver_pattern.TableObserverInterface;
import COMP3004.terminal.TerminalView;

import java.util.ArrayList;

public class GameInteractionController implements TableObserverInterface {
    protected TerminalView terminalView = new TerminalView();
    protected GraphicalView graphicalView;
    protected Table table;
    protected Player player;

    protected int score = 0;

    public GameInteractionController(){
        //this.terminalView = new TerminalView();
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
                Meld m2 = new Meld();
                for(Tile tile : m.getTiles()){
                    m2.add(tile);
                }
                output.add(m2);
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

    public void setTerminalView(TerminalView t) {
        this.terminalView = t;
    }

    public TerminalView getTerminalView() {
        return this.terminalView;
    }

    public GraphicalView getGUI() { return this.graphicalView; }
    public void setGUI(GraphicalView g) { this.graphicalView = g; }

    public ArrayList<Tile> getRiggedTiles() {
        return this.player.getRiggedTiles();
    }

    public void setRiggedTiles(ArrayList<Tile> riggedTiles) {
        this.player.setRiggedTiles(riggedTiles);
    }


    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
