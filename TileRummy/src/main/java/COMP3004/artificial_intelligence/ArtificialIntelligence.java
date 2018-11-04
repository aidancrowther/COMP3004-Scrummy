/* Carleton University
 * Fall 2018
 *
 * COMP 3004
 * JP Coriveau
 *
 * Group 6
 * David N. Zilio
 * Aidan Crowther
 * Ellis Glennie
 * Brittny Lapierre
 *
 * This abstract class is a way to abstract out the Strategies of the AI such that it won't be janky in the controller
 */

package COMP3004.artificial_intelligence;

import COMP3004.controllers.GameInteractionController;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.oberver_pattern.TableObserverInterface;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.*;
import java.util.AbstractMap;

public abstract class ArtificialIntelligence extends GameInteractionController implements TableObserverInterface
{
    /*
     * These attributes save the player state as they make moves.
     * If all of the moves were valid, then the game state will
     * also be updated.
     * */
    //protected Table table = new Table();
    //protected Meld hand = null;

    protected void name() {
        
    } int score = 0;



    protected ArtificialIntelligence() {
        
    }

    public Table play(){ return null;}

    public Meld getHand() { return this.player.getHand(); }

    public int getScore() { return this.score; }

    //The hand needs a setter since there are more than one hand in the game, making it unsuitable to observe
    public void setHand(Meld hand){
        this.player.setHand(hand);
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
        HashMap<Meld, Integer> handMelds = new HashMap<>();
        int n = 0;
        ArrayList<Tile> h = this.player.getHand().getTiles();

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
        HashMap<Meld, Integer> tMelds = new HashMap<>();
        ArrayList<Tile> h = this.player.getHand().getTiles();

        if (t == null) {
            return tMelds;
        }

        for (int i=1; i<t.getMelds().size(); i++) {
            Meld m = t.getMelds().get(i).copy();
            Meld toAdd = new Meld();

            for (int j=h.size()-1; j>=0; j--) {
                if (m.isValid(h.get(j))) {
                    m.add(h.get(j));
                    toAdd.add(h.get(j));
                }
            }

            for (int j=0; j<h.size(); j++) {
                if (m.isValid(h.get(j))) {
                    m.add(h.get(j));
                    toAdd.add(h.get(j));
                }
            }
            if (!toAdd.getTiles().isEmpty()) {
                tMelds.put(toAdd.copy(), i);
            }
        }

        return tMelds;
    }

    protected int isAddable(Tile toAdd, Tile t) {
        //returns 0 if toAdd is addable as a set to t
        //returns 1 if toAdd is addable as a run to t
        //returns -1 if toAdd is not addable to t
        if (toAdd.getColour() == t.getColour()) {
            if (toAdd.getValue() - t.getValue() == 1 || 
                t.getValue() - toAdd.getValue() == 1) {
                    return 1;
            }
        }
        else {
            if (toAdd.getValue() == t.getValue()) {
                return 0;
            }
        }
        return -1;
    }

    //tries to create a meld out of the offered cards. returns true if it is valid
    protected boolean canMeld(Tile a, Tile b, Tile c) {
        Meld m = new Meld();
        m.add(a);
        m.add(b);
        m.add(c);
        if (m.isValid()) {
            return true;
        }
        else {
            return false;
        }
    }

    //adds either 2 cards to a meld of 1, or one card to a meld of 2.
    protected void addingForSplitting(Meld shortM, ArrayList<Tile> h, int k) {
		if (shortM.size() == 1) {
            Tile t = shortM.getTiles().get(0);
            int start;
            if (isAddable(h.get(k), t) >= 0) {
                if (k != 0) {
                    start = k-1; 
                }
                else {
                    start = k;
                }
                for (int i=start; i<h.size(); i++) {
                    if (canMeld(h.get(k), h.get(i), t)) {
                        shortM.add(h.get(k));
                        shortM.add(h.get(i));
                        break;
                    }
                }
            } 
        } 
        else {
            if (shortM.isValid(h.get(k))) {
                shortM.add(h.get(k));
            }																	
        }
    }

    //adds a meld from a split to its arraylist. In doing so, it removes these cards from the 
    //local copy of the table's meld as well as the local copy of the AI's hand
    protected void addSplitToList(ArrayList<Meld> aList, Meld shortM, Meld m, Meld hTiles, ArrayList<Tile> h) {
        aList.add(shortM.copy());
	    for (int q=0; q<shortM.size(); q++) {
		    if (h.contains(shortM.getTiles().get(q))) {
			    hTiles.add(shortM.getTiles().get(q));
				h.remove(shortM.getTiles().get(q));
			}			
			else if (m.getTiles().contains(shortM.getTiles().get(q))) {
				m.remove(shortM.getTiles().get(q));
			}
		} 
    }

    //Splits a set into every possible combination of cards. Changes depending on length of set
    protected ArrayList<Meld> permute(Meld m) {
        ArrayList<Meld> output = new ArrayList<>();
        for (int i=0; i<m.size(); i++) {
            Meld m1 = new Meld();
            m1.add(m.getTiles().get(i));
            output.add(m1.copy());
        }
        if (m.size() == 4) {
            Meld m3 = new Meld();;
            for (int i=0; i<3; i++) {
                for (int j=0; j<3; j++) {
                    if (i != j) {
                        m3 = m.copy();
                        m3.getTiles().remove(i);
                        m3.getTiles().remove(j);
                        output.add(m3.copy());
                    }
                }
            }
        }
		if (m.size() > 2) {
			for (int i=m.size()-1; i>=0; i--) {
				Meld m2 = m.copy();
				m2.getTiles().remove(i);
				output.add(m2.copy());
			}
		}
		else if (m.size() == 2) {
			output.add(m.copy());
		}
        return output;
    }


    public HashMap<Meld, AbstractMap.SimpleEntry<ArrayList<Meld>, Integer>> searchSplit(Table t) {
        HashMap<Meld, AbstractMap.SimpleEntry<ArrayList<Meld>, Integer>> tableSplits = new HashMap<>();

        if (t == null) {
            return tableSplits;
        }

        for (int i=1; i<t.getMelds().size(); i++) {             //for every meld in table
            Meld hTiles = new Meld();
            ArrayList<Meld> aList = new ArrayList<Meld>();      //arraylist of melds created from a split
            Meld m = t.getMelds().get(i).copy();                //the meld about to be split
            ArrayList<Tile> h = this.player.getHand().copy().getTiles();         //Copy of the hand

            //Trying to split a run
            if (t.getMelds().get(i).isRun()) {
                for (int j=0; j<m.size(); j++) {
                    Meld shortM = new Meld();
                    //for melds where we'll iterate through linearly, rather than consider every combination
                    for (int k=j; k<m.size(); k++) {
                        shortM.add(m.getTiles().get(k));
                        for (int p=0; p<h.size(); p++) {
                            addingForSplitting(shortM, h, p);

							if (shortM.isValid()) {
								addSplitToList(aList, shortM, m, hTiles, h);
								k=999;
                                j--;
                                break;
							}
                        }
                    }
                }
            }
            //trying to split a set
            else if (!t.getMelds().get(i).isRun())  {
                ArrayList<Meld> perms = permute(m);
                for (int j=0; j<perms.size(); j++) {
                    if (perms.size() == 0) {
                        break;
                    }
                    Meld shortM = perms.get(j);
                    for (int k=0; k<h.size(); k++) {
                        addingForSplitting(shortM, h, k);
                        if (shortM.isValid()) {
                            addSplitToList(aList, shortM, m, hTiles, h);
                            perms = permute(m);
                            j = -1;
                            break;
                        }
                    }          
                }
            }

            //edge cases
            if (!aList.isEmpty() && !m.getTiles().isEmpty()) {
                for (Meld a : aList) {
				    for (int k=0; k<m.size(); k++) {
					    if (a.isValid(m.getTiles().get(k))) {
						    a.add(m.getTiles().remove(k));
					    }
			    	}
				    for (int k=0; k<h.size(); k++) {
					    if (a.isValid(h.get(k))) {
						    a.add(h.get(k));
						    hTiles.add(h.remove(k));
					    }
                    }
                }
                if (!m.equals(t.getMelds().get(i)) && m.isValid()) {
				    addSplitToList(aList, m.copy(), m, hTiles, h);
                }
            }

            //if a meld is successfully split, add it to the output
            if (m.getTiles().isEmpty() && aList.size() > 1 && !hTiles.getTiles().isEmpty()) {
                AbstractMap.SimpleEntry<ArrayList<Meld>, Integer> meldSplits = new AbstractMap.SimpleEntry<>(aList, i);
                tableSplits.put(hTiles.copy(), meldSplits);
            }
        }
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
    protected ArrayList<Meld> findUnique(Meld m, HashMap<Meld, Integer> h, HashMap<Tile, Integer> inHand){

        ArrayList<Meld> results = new ArrayList<>();

        //Iterate over the set to compare against
        for(Map.Entry<Meld, Integer> entry : h.entrySet()){
            //Initialize tracking variables
            Boolean containsDuplicate = false;
            HashMap<Tile, Integer> hand = inHand;
            ArrayList<Integer> usedMelds = new ArrayList<>();
            //Iterate over both tile sets
            if(entry.getKey() != m){
                Boolean cont = true;
                //Make sure we aren't reusing a split
                if(h.get(entry.getKey()) != 0){
                    if(!usedMelds.contains(h.get(entry.getKey()))) cont = false;
                    else usedMelds.add(h.get(entry.getKey()));
                }
                //Check all tiles for duplicates
                if(cont){
                    for(Tile t1 : entry.getKey().getTiles()){
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
            }
            //If there are no reused tiles, add to the list of indeces
            if(!containsDuplicate) results.add(entry.getKey());
        }

        results = shortByShortest(results);

        //Return the list of indeces
        return results;
    }

    //Sort an arraylist of melds to put the longest ones first
    protected HashMap<Meld, Integer> sortByLength(HashMap<Meld, Integer> h){

        HashMap<Meld, Integer> result = new HashMap<>();

        //Create a comparator
        Comparator<Meld> meldLengthComparator = new Comparator<Meld>(){
            @Override
            public int compare(Meld m1, Meld m2){
                return Integer.compare(m1.size(), m2.size());
            }
        };

        List<Meld> list = new ArrayList<>();
        list.addAll(h.keySet());

        //Sort in descending order, then reverse
        Collections.sort(list, meldLengthComparator);
        Collections.reverse(list);

        list.forEach( m -> result.put(m, h.get(m)));

        return result;
    }

    protected ArrayList<Meld> shortByShortest(ArrayList<Meld> a){
        //Create a comparator
        Comparator<Meld> meldLengthComparator = new Comparator<Meld>(){
            @Override
            public int compare(Meld m1, Meld m2){
                return Integer.compare(m1.size(), m2.size());
            }
        };

        Collections.sort(a, meldLengthComparator);
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

    // OBSERVER PATTERN CODE
    public void update(Table table) {
        this.table = table;
    }
    public Table getTable() { return this.table; }
    public void setTable(Table table) { this.table = table; }
}