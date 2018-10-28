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
    public Meld hand = null;
    protected int score = 0;

    public ArtificialIntelligence() {
        
    }

    public Table play(){return null;}

    public Meld getHand() { return this.hand; }

    public int getScore() { return this.score; }

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(Meld hand){
        this.hand = hand;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void drawTile(){

    }

    public void selectTile(Meld inMeld, Meld outMeld, Tile tile){
        if(inMeld.getTiles().contains(tile)){
            outMeld.add(tile);
            inMeld.remove(tile);
        }
    }


    public HashMap<Meld, Integer> searchHand() {
        HashMap<Meld, Integer> handMelds = new HashMap<Meld, Integer>();
        int n = 0;
        ArrayList<Tile> h = hand.getTiles();

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


    public HashMap<Meld, Integer> searchTable(Table t) {
        HashMap<Meld, Integer> tMelds = new HashMap<Meld, Integer>();
        ArrayList<Tile> h = hand.getTiles();

        tMelds.put(new Meld(), 0);
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

    public HashMap<Meld, HashMap<ArrayList<Meld>, Integer>> searchSplit(Table t) {
        HashMap<Meld, HashMap<ArrayList<Meld>, Integer>> tableSplits = new HashMap<Meld, HashMap<ArrayList<Meld>, Integer>>();
        HashMap<ArrayList<Meld>, Integer> meldSplits = new HashMap<ArrayList<Meld>, Integer>(); //all splits and corresponding table locations
        Meld hTiles = new Meld();                               //this will contain all of hand's tiles to be used in splits

        if (t == null || t.getMelds().size() == 1) {
            return tableSplits;
        }

        for (int i=1; i<table.getMelds().size(); i++) {         //for every meld in table
            ArrayList<Meld> aList = new ArrayList<Meld>();       //arraylist of melds created from a split
            Meld m = table.getMelds().get(i).copy();            //the meld about to be split
            ArrayList<Tile> h = hand.copy().getTiles();
            
            for (int j=0; j<m.size(); j++) {                    //for every tile in meld i
                Meld shortM = new Meld();
                if (m.isRun()) {                                //splitting a run
                    for (int k=j; k<m.size(); k++) {            //break it up
                        if (shortM.size() + 1 != m.size()) {    //do not make the actual meld; searchTable does this part 
                            shortM.add(m.getTiles().get(k));    //travel through every combination of cards in the run
                            
                            for (int p=0; p<h.size(); p++) {    //iterate through hand
                                
                                if ((h.get(p).getColour() == shortM.getTiles().get(0).getColour() &&
                                    (shortM.getTiles().get(0).getValue() - h.get(p).getValue() == 1 || 
                                     h.get(p).getValue() - shortM.getTiles().get(shortM.size()-1).getValue() == 1)) ||
                                     (h.get(p).getColour() != shortM.getTiles().get(0).getColour() &&
                                     shortM.getTiles().get(0).getValue() == h.get(p).getValue())) { //if it can be added

                                    shortM.add(h.get(p));
                                    hTiles.add(h.get(p));
                                } 
                                if (shortM.size() > 2 && !shortM.isValid()) {  //in case the wrong type was added
                                    shortM.remove(h.get(p));
                                    hTiles.remove(h.get(p));
                                }
                            }                              
                        }
                        if (shortM.isValid()) {
                            for (int q=0; q<shortM.size(); q++) {
                                h.remove(shortM.getTiles().get(q)); //dont reuse tiles
                                m.remove(shortM.getTiles().get(q)); //dont reuse meld tiles
                            }
                            aList.add(shortM.copy());
                            break;
                        }
                    }
                
                } else { //splitting a set

                }


            }

            /*REMOVE TILES FROM M WHEN THEY GET USED */         
            if (m.getTiles().isEmpty()) {
                meldSplits.put(aList, i);
            }
            
        }

        tableSplits.put(hTiles, meldSplits);
        return tableSplits;
    }




    //Return if two tiles have the same colour and value
    public Boolean isEquivalent(Tile a, Tile b){
        Boolean result = true;
        result &= a.getColour() == b.getColour();
        result &= a.getValue() == b.getValue();
        return result;
    }

    //Return an arrayList of indeces for melds that do not share tiles
    public ArrayList<Integer> findUnique(Meld m, ArrayList<Meld> a, HashMap<Tile, Integer> inHand){

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
                        if(hand.get(t1) != null) if(hand.get(t1) > 1 && isEquivalent(t1, t2) && !containsDuplicate){
                            containsDuplicate = false;
                            hand.put(t1, hand.get(t1)-1);
                        }
                        else if(hand.get(t2) != null) if(hand.get(t2) > 1 && isEquivalent(t1, t2) && !containsDuplicate){
                            containsDuplicate = false;
                            hand.put(t2, hand.get(t2)-1);
                        }
                        else containsDuplicate |= isEquivalent(t1, t2);
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
    public ArrayList<Meld> sortByLength(ArrayList<Meld> a){

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

}