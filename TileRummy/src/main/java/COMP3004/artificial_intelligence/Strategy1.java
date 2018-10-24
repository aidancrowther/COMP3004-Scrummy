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

package COMP3004.artificial_intelligence;

import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Strategy1 extends ArtificialIntelligence
{

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


        HashMap<Meld, Integer> handResults = searchHand();
        HashMap<Meld, Integer> tableResults = searchTable(table);

        HashMap<Tile, Integer> inHand = new HashMap<>();
        ArrayList<ArrayList<Meld>> results = new ArrayList<>();

        for(Tile tile : hand.getTiles()){
            Boolean found = false;
            for(Map.Entry<Tile, Integer> pair : inHand.entrySet()){
                if(isEquivalent(tile, pair.getKey())){
                    inHand.put(pair.getKey(), pair.getValue()+1);
                    found = true;
                    break;
                }
            }
            if(!found) inHand.put(tile, 1);
        }

        for(Map.Entry<Tile, Integer> pair : inHand.entrySet()){
            output+=pair.getKey()+", "+pair.getValue()+"; ";
        }
        output+="\n\n";
        for(Map.Entry<Meld, Integer> pair : handResults.entrySet()){
            output+=pair.getKey().toString()+", "+pair.getValue()+"; ";
        }
        output+="\n\n";
        for(Map.Entry<Meld, Integer> pair : tableResults.entrySet()){
            output+=pair.getKey().toString()+", "+pair.getValue()+"; ";
        }

        return null;
    }

}