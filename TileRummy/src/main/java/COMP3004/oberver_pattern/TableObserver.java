package COMP3004.oberver_pattern;

import COMP3004.models.Table;

public abstract class TableObserver implements Observer {

    /*
    * These attributes save the player state as they make moves.
    * If all of the moves were valid, then the game state will
    * also be updated.
    * */
    protected Table table;

    public TableObserver() {
        table = new Table();
    }

    /*
     * The view classes observe the model. This function will update their local table to the game board after every turn.
     * This should set the table to match the table in scrummy, therefore we do not need setTable
     */
    public void update(Table table){
        this.table = table;
    }

    public Table getTable() {
        return this.table;
    }
    public void setTable(Table t) {
        this.table = t;
    }
}