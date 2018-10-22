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

        return null;
    }

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(Meld hand){
        this.hand = hand;
    }



    
    public HashMap<Meld, Integer> searchHand() {
        HashMap<Meld, Integer> handMelds = new HashMap<Meld, Integer>();
        int n, i;
        ArrayList<Character> a = new ArrayList<Character>(); 
        Meld m = new Meld();
        ArrayList<Tile> h = hand.getTiles();

        //runs
        n=0;
        i=0;
        while (i<h.size()-2) {
            m.clear();
            m.add(h.get(i));
            m.add(h.get(i+1));
            m.add(h.get(i+2));
            if (m.isValid()) {
                for (int j=i+3; j<h.size()-2; j++) {
                    m.add(h.get(j));
                    if (!m.isValid()) {
                        m.remove(h.get(j));
                        break;
                    }
                }
                handMelds.put(m, n);
                n++;
                i += m.getTiles().size();
            }
            else {
                i++;
            }
        }
        //sets\
        for (i=0; i<h.size(); i++) {
            a.clear();
            m.clear();
            m.add(h.get(i));
            a.add(h.get(i).getColour());
            for (int j=0; j<h.size(); j++) {
                if (h.get(j).getValue() == h.get(i).getValue() &&
                    !a.contains(h.get(j).getColour())) {
                        m.add(h.get(j));
                        a.add(h.get(j).getColour()); //no duplicate colours
                    }
            }
            if (m.isValid()) {
                handMelds.put(m, n);
                n++;
            }
            //find a way to combine and return best option
        }
        
        return handMelds;
    }


    /*public HashMap<Meld, Integer> searchTable(Table t) {
        HashMap<Meld, Integer> tableMelds = new HashMap<Meld, Integer>();

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