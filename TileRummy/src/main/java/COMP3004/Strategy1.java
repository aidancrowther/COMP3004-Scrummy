/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * AI Strategy1 should do exactly as the spec requires
 * --play when it can, all that it can
 */

package COMP3004;

import java.util.ArrayList;

public class Strategy1 extends ArtificialIntelligence
{
    public Strategy1(){

    }

    public void drawTile(){
        
    }

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile){

    }

    /*
     * Terminal View: Loops through the prompts until player enters text to complete their turn, then returns this.table.
     * GUI View: Allows the user to interact with the table until the user presses complete turn button. When button is pressed end the user’s turn, and the method returns this.table.
     * AI: Strategies
     */
    public Table play(){
        return null;
    }

    /*
     * The view classes observe the model. This function will update their local table to the game board after every turn. 
     * This should set the table to match the table in scrummy, therefore we do not need setTable
     */
    public void update(Table table){

    }

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(ArrayList<Tile> hand){

    }
}