package COMP3004.oberver_pattern;

public interface PlayerHandObserverInterface {
    /*
     * The view classes observe the model. This function will update their local table to the game board after every turn.
     * This should set the table to match the table in scrummy, therefore we do not need setTable
     */
    public void update(int value, int index);
    public int getPlayerHandSize(int index);
    public void setPlayerHandSize(int value, int index);
}