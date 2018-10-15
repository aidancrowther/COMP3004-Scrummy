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
    }

    public void startGame(){
        this.view.play();
    }
}