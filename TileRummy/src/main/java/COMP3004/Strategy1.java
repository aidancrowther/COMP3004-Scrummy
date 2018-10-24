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
import java.util.HashMap;


public class Strategy1 extends ArtificialIntelligence
{
    protected Table table = null;
    protected Meld hand = null;
    protected int score = 0;


    public Strategy1(){

    }


    /*
     * Terminal View: Loops through the prompts until player enters text to complete their turn, then returns this.table.
     * GUI View: Allows the user to interact with the table until the user presses complete turn button. When button is pressed end the user’s turn, and the method returns this.table.
     * AI: Strategies 1 - 4
     */
    public Table play(){

        /*  

            -> HashMap<Meld, int> handResults = searchHand();
            -> HashMap<Meld, int> tableResults = searchTable(table);

            -> ArrayList<ArrayList<Meld>> results

            -> results contains every combination of the Melds within handResults and tableResults
                that do not use the same elements from hand
            -> Pick the largest ArrayList. Remove every tile from hand that is there.
            -> From the largest ArrayList of melds, search for the keyvalue of each meld within its
                original hashmap, and append said meld to the meld specified in the Hashmap
            -> If the specified meld is from the hand, add the entire meld. Otherwise, append by tile

            -> Return the brand new table :)

        */

        HashMap<Meld, Integer> handResults = null;
        HashMap<Meld, Integer> tableResults = null;

        ArrayList<ArrayList<Meld>> results = new ArrayList<>();

        return null;
    }




    
    






}