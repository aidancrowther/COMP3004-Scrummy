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
    protected Table table = null;
    protected ArrayList<Tile> hand = null;

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

        /*  Initialize an empty list of identified melds
        *   Iterate over the list, starting at element 0, and seeing how many tiles following it are in sequence
        *     - If there are none, move onto the next element
        *     - Otherwise, check if the sequence is >= 3
        *     - Add the entire list as a meld to the list of melds
        *     - Otherwise, move onto the tile immeditely following the last tile in the sequence
        *   Once we reach the last element, stop looking for runs
        *   Iterate over each element of the list, looking for duplicate values with different colour
        *     - If >=3 are found, add the set to the list of melds
        *     - Otherwise, move onto the next element
        *   For now, ignore melds on the table
        *   Check the list of melds for non duplicate uses of tiles (unless duplicate tiles exist)
        *   Combine melds that use different tiles
        *   Sort the list by number of tiles they each use
        *   Update the table, using the set of melds that uses the most tiles
        */

        return null;
    }

    /*
     * The view classes observe the model. This function will update their local table to the game board after every turn. 
     * This should set the table to match the table in scrummy, therefore we do not need setTable
     */
    public void update(Table table){
        this.table = table;
    }

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(ArrayList<Tile> hand){
        
    }
}