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

    public void drawTile(){
        
    }

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile){
        if(inMeld.getTiles().contains(tile)){
            outMeld.add(tile);
            inMeld.remove(tile);
        }
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

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(Meld hand){
        this.hand = hand;
    }



    /*
    public HashMap<Meld, int> searchHand() {
        HashMap<Meld, int> handMelds = new HashMap<Meld, int>();

        -> Check the hand for runs:
            -> Iterate over hand, starting at tile 0, and see how many tiles following it are in numerical sequence
            -> If there are none, move onto the next element
            -> Otherwise, check if the sequence is >= 3
            -> Add the entire list as a meld to handMelds, setting keyvalue to 0
            -> Otherwise, move onto the tile immeditely following the last tile in the sequence
            -> Continue until the entire hand has been hecked for runs
        -> Check the hand for sets:
            -> Iterate over hand, looking for duplicate values with different colours
            -> If >=3 are found, add the set as a meld to handMelds, setting keyvalue to 0
            -> Otherwise, move onto the next element
        
        -> Return the hashmap of melds 
    }


    public HashMap<Meld, int> searchTable(Table t) {
        HashMap<Meld, int> tableMelds = new HashMap<Meld, int>();

        -> Retrieve t.getMelds()
        -> For all melds in t.getMelds():
            -> If the meld is a run:
                -> Iterate over hand
                -> If the tile in hand can be added to the front or end of the sequence, add(Tile, Meld) it.
            -> If the meld is a set:
                -> Iterate over hand
                -> If the tile in hand is the same number and a colour not in the meld, add(Tile, Meld) it

        -> Add all of t.getMelds() to tableMelds, using ArrayList index as key value
        -> Return the hashmap of melds

        -> NOTE: This does not currently support preexisting melds.
    }









    */

}