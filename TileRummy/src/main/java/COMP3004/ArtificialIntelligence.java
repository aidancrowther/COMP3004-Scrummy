/* Carleton University
 * Fall 2018
 * 
 * COMP 3004
 * JP Coriveau
 * 
 * Group 6
 * David N. Zilio
 * 
 * This abstract class is a way to abstract out the Strategies of the AI such that it won't be janky in the controller
 */

package COMP3004;

import java.util.HashMap;
import java.util.ArrayList;


public abstract class ArtificialIntelligence extends TableObserver implements PlayerInterface
{
    protected Table table = null;
    protected Meld hand = null;
    

    public ArtificialIntelligence() {

    }
    
    public void drawTile(){
        
    }

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile){
        if(inMeld.getTiles().contains(tile)){
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(Meld hand){
        this.hand = hand;
    }

    public HashMap<Meld, Integer> searchHand() {
        HashMap<Meld, Integer> handMelds = new HashMap<Meld, Integer>();
        int n = 0;
        ArrayList<Tile> h = hand.getTiles();

        //runs
        for (int i=0; i<h.size()-3; i++) {
            Meld m = new Meld();
            m.add(h.get(i));
            m.add(h.get(i+1));
            m.add(h.get(i+2));
            if (m.isValid()) {
                handMelds.put(m.copy(), n);
                n++;
                if (i+2<h.size()-1) {
                    for (int j=i+3; j<h.size(); j++) {
                        m.add(h.get(j));
                        if (m.isValid()) {
                            handMelds.put(m.copy(), n);
                            n++;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        //sets
        for (int i=0; i<h.size(); i++) {
            ArrayList<Character> a = new ArrayList<Character>(); 
            Meld m = new Meld();
            m.add(h.get(i));
            a.add(h.get(i).getColour());
            for (int j=i; j<h.size(); j++) {
                if (h.get(j).getValue() == h.get(i).getValue() &&
                    !a.contains(h.get(j).getColour())) {
                    m.add(h.get(j));
                    a.add(h.get(j).getColour()); //no duplicate colours
                    if (m.isValid()) {
                            handMelds.put(m.copy(), n);
                            n++;
                    }
                }
            }
        }

        return handMelds;
    }


    public HashMap<Meld, Integer> searchTable(Table t) {
        HashMap<Meld, Integer> tMelds = new HashMap<Meld, Integer>();
        ArrayList<Tile> h = hand.getTiles();
        
        tMelds.put(new Meld(), 0);
        for (int i=1; i<t.getMelds().size(); i++) {
            Meld m = t.getMelds().get(i).copy();
            
            for (int j=0; j<h.size(); j++) {
                m.add(h.get(j));
                if (!m.isValid()) {
                    m.remove(h.get(j));
                }
            }

            tMelds.put(m, i); //puts t into the HashMap, keeping track of Table location
        }

        return tMelds;
    }



    
}