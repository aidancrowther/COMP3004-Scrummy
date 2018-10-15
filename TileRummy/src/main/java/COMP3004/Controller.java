/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * The controller class manages any of the observer interactions between UI and Scrummy or view and model respectively
 */

package COMP3004;

public class Controller
{
    private Scrummy scrummy;
    private View view;

    public Controller(){
        this.view = new TerminalUI(); //TODO: make this an option
        this.scrummy = new Scrummy();
        this.scrummy.registerObserver(this.view);
    }

    public void run(){
        /* Set view's player to current player in scrummy
        * Set vies table to table in scrummy
        * If table equals scrummy table,
        *   add a card to the players hand
        * else
        *   have scrummy evaluate the table and update if valid
        * */
        Table playedTable = this.view.play();
    }

    public View getView(){
        return this.view;
    }

    public Scrummy getScrummy(){
        return this.scrummy;
    }
}