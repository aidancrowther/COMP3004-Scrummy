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
    Scrummy scrummy;
    View view;

    public Controller(Scrummy scrummy, View view){
        this.scrummy = scrummy;
        this.view = view;
    }

    public void startGame(){
        this.view.play();
    }
}