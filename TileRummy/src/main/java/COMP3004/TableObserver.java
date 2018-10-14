package COMP3004;

public abstract class TableObserver implements Observer {

    /*
    * These attributes save the player state as they make moves.
    * If all of the moves were valid, then the game state will
    * also be updated.
    * */
    Table table;
    Meld hand;

    public TableObserver() {
        table = new Table();
        hand = new Meld();
    }

    /*
     * The view classes observe the model. This function will update their local table to the game board after every turn.
     * This should set the table to match the table in scrummy, therefore we do not need setTable
     */
    public void update(Table table){
        this.table = table;
    }
}
