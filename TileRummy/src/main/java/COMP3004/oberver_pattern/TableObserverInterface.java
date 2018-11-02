package COMP3004.oberver_pattern;

import COMP3004.models.Table;

public interface TableObserverInterface  {
    /*
     * The view classes observe the model. This function will update their local table to the game board after every turn.
     * This should set the table to match the table in scrummy, therefore we do not need setTable
     */
    public void update(Table table); /*{
        this.table = table;
    }*/
    public Table getTable();
    public void setTable(Table t);
}
