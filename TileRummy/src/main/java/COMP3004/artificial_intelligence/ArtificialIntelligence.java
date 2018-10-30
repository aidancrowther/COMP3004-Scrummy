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

package COMP3004.artificial_intelligence;

import COMP3004.controllers.GameInteractionInterface;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.oberver_pattern.TableObserver;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;

public abstract class ArtificialIntelligence extends TableObserver implements GameInteractionInterface
{
    protected Meld hand = null;
    protected void name() {
        
    } int score = 0;



    protected ArtificialIntelligence() {
        
    }

    protected Table play(){return null;}

    public Meld getHand() { return this.hand; }

    public int getScore() { return this.score; }

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(Meld hand){
        this.hand = hand;
    }

    public void setScore(int score){
        this.score = score;
    }
    
    ////////////////////
    //Helper Functions//
    ////////////////////
    public void selectTile(Meld inMeld, Meld outMeld, Tile tile){
        if(inMeld.getTiles().contains(tile)){
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }


    protected HashMap<Meld, Integer> searchHand() {
        HashMap<Meld, Integer> handMelds = new HashMap<Meld, Integer>();
        int n = 0;
        ArrayList<Tile> h = this.hand.getTiles();

        //runs
        for (int i=0; i<h.size()-2; i++) {
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


    protected HashMap<Meld, Integer> searchTable(Table t) {
        HashMap<Meld, Integer> tMelds = new HashMap<Meld, Integer>();
        ArrayList<Tile> h = hand.getTiles();

        if (t == null) {
            return tMelds;
        }

        for (int i=1; i<t.getMelds().size(); i++) {
            Meld m = t.getMelds().get(i).copy();
            Meld toAdd = new Meld();

            for (int j=0; j<h.size(); j++) {
                m.add(h.get(j));
                toAdd.add(h.get(j));
                if (m.isValid()) {
                    tMelds.put(toAdd.copy(), i);
                } else {
                    m.remove(h.get(j));
                    toAdd.remove(h.get(j));
                }
            }
        }

        return tMelds;
    }

    protected void addingForSplitting(Meld shortM, Meld hTiles, ArrayList<Tile> h, int p) {
        Meld copyM = shortM.copy();
        Meld copyH = hTiles.copy();
		if (shortM.size() == 1) {
            //try to make a run
            if (h.get(p).getColour() == copyM.getTiles().get(0).getColour()) {
                //try to add one above and one below it; if they do not exist, you know
                //you can't make a run using just one tile from the tabl
                //Just adding one tile at a time is causing the program to miss
                //possible melds.

            //try to make a set
            } else {
                //try to add 2 tiles; if they do not exist, you know
                //you can't make a set using just one tile from the table
                //Just adding one tile at a time is causing the program to miss
                //possible melds.
            }

            //I'm not entirely sure this is working. Make sure it is.
            if (copyM.size() > 2 && copyM.isValid()) { 
                shortM = copyM.copy();
                hTiles = copyH.copy();
            }
		} else {
            shortM.add(h.get(p));
            hTiles.add(h.get(p));
            if (!shortM.isValid()) {
                shortM.remove(h.get(p));
                hTiles.remove(h.get(p));
            }																			
        }
    }

    public HashMap<Meld, HashMap<ArrayList<Meld>, Integer>> searchSplit(Table t) {
        HashMap<Meld, HashMap<ArrayList<Meld>, Integer>> tableSplits = new HashMap<Meld, HashMap<ArrayList<Meld>, Integer>>();
        HashMap<ArrayList<Meld>, Integer> meldSplits = new HashMap<ArrayList<Meld>, Integer>(); //all splits and corresponding table locations
        Meld hTiles = new Meld();                               //this will contain all of hand's tiles to be used in splits

        if (t == null) {
            return tableSplits;
        }

        for (int i=1; i<t.getMelds().size(); i++) {             //for every meld in table
            ArrayList<Meld> aList = new ArrayList<Meld>();      //arraylist of melds created from a split
            Meld m = t.getMelds().get(i).copy();                //the meld about to be split
            ArrayList<Tile> h = hand.copy().getTiles();         //Copy of the hand
            
            for (int j=0; j<m.size(); j++) {                    //for every tile in meld i
                Meld shortM = new Meld();
                for (int k=j; k<m.size(); k++) {
                    //splitting either a run or a set of 3 (aka anywhere the tiles will just be checked linearly)
                    if (t.getMelds().get(i).meldType() == 1 ||
                        t.getMelds().get(i).meldType() == 0 && t.getMelds().get(i).size() == 3) {
                        shortM.add(m.getTiles().get(k));                    //travel through every combination of cards in the run
						for (int p=0; p<h.size(); p++) {                    //iterate through hand                                
                            addingForSplitting(shortM, hTiles, h, p);
                        }	
                    //splitting a set of 4						
                    } else { 
                        //Whereas a set of 3 only has 6 total splits (including individual tiles),
                        //a set of four (if i recall correctly) has 14. Every combination of tiles,
                        //excluding the entire meld and including individual tiles, has to be
                        //parsed through. addingForSplitting should be called normally once that is
                        //established.
                            
                    }

                    if (shortM.isValid()) {
						System.out.println(shortM.toString() + " is a success!");
                        for (int q=0; q<shortM.size(); q++) {
                            if (h.contains(shortM.getTiles().get(q))) {
                                h.remove(shortM.getTiles().get(q));
                            }
							if (m.getTiles().contains(shortM.getTiles().get(q))) {
                                m.remove(shortM.getTiles().get(q));
                            }
                        }
                        //After a meld is made, best to make sure nothing else from the table can be
                        //added. This is an edge case.
                        /*for (int p=0; p<m.size(); p++) {    //iterate through hand                                
                            addingForSplitting(shortM, hTiles, m.getTiles(), p);
                        }*/
                        j--;
                        aList.add(shortM.copy());
                        k=999;
                    }
                }
            }     
            if (m.getTiles().isEmpty()) {               //all tiles have been used
                meldSplits.put(aList, i);
            } else {
                //
            }   
        }
        tableSplits.put(hTiles, meldSplits);
        return tableSplits;
    }




    //Return if two tiles have the same colour and value
    protected Boolean isEquivalent(Tile a, Tile b){
        Boolean result = true;
        result &= a.getColour() == b.getColour();
        result &= a.getValue() == b.getValue();
        return result;
    }

    //Return an arrayList of indeces for melds that do not share tiles
    protected ArrayList<Integer> findUnique(Meld m, ArrayList<Meld> a, HashMap<Tile, Integer> inHand){

        ArrayList<Integer> results = new ArrayList<>();

        //Iterate over the set to compare against
        for(int i=0; i<a.size(); i++){
            //Initialize tracking variables
            Boolean containsDuplicate = false;
            HashMap<Tile, Integer> hand = inHand;
            //Iterate over both tile sets
            if(a.get(i) != m){
                for(Tile t1 : a.get(i).getTiles()){
                    for(Tile t2 : m.getTiles()){
                        //If they are the same, and there is no duplicate this set contains reused tiles
                        if(hand.get(t1) != null) if(hand.get(t1) > 1 && t1.equals(t2) && !containsDuplicate){
                            containsDuplicate = false;
                            hand.put(t1, hand.get(t1)-1);
                        }
                        else if(hand.get(t2) != null) if(hand.get(t2) > 1 && t1.equals(t2) && !containsDuplicate){
                            containsDuplicate = false;
                            hand.put(t2, hand.get(t2)-1);
                        }
                        else containsDuplicate |= t1.equals(t2);
                    }
                }
            }
            //If there are no reused tiles, add to the list of indeces
            if(!containsDuplicate) results.add(i);
        }

        //Return the list of indeces
        return results;
    }

    //Sort an arraylist of melds to put the longest ones first
    protected ArrayList<Meld> sortByLength(ArrayList<Meld> a){

        //Create a comparator
        Comparator<Meld> meldLengthComparator = new Comparator<Meld>(){
            @Override
            public int compare(Meld m1, Meld m2){
                return Integer.compare(m1.size(), m2.size());
            }
        };

        //Sort in descending order, then reverse
        Collections.sort(a, meldLengthComparator);
        Collections.reverse(a);

        return a;
    }


    public int listScore(ArrayList<Meld> a) {
        int output = 0;
        for (int i=0; i<a.size(); i++) {
            for (int j=0; j<a.get(i).size(); j++) {
                output += a.get(i).getTiles().get(j).getValue();
            }
        }

        return output;
    }

    //Get the index of the first entry for a tile in a meld by value
    protected int indexOf(Meld m, Tile t){
        for (int i=0; i<m.getTiles().size(); i++)
            if (m.getTiles().get(i).equals(t))
                return i;
        
        return -1;
    }
}