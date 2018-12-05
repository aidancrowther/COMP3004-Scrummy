package COMP3004.models;

import java.util.*;



public class Meld {
    private ArrayList<Tile> tiles = new ArrayList<>();


    //constructor
    public Meld() {
    }

    //gets meld contents
    public ArrayList<Tile> getTiles() { return tiles; }

    public int size() { return tiles.size(); }

    public void add(Tile t) {
        if(t != null){
            if (t.isJoker()) {
                this.addJoker(t);
            }
            this.tiles.add(t);
            sort();
        }
    }

    //for recreating jokers within sort()
	public void addInSort(Tile t) {
		this.addJoker(t);
		this.tiles.add(t);
	}

    public void clear() {
        tiles.clear();
    }

    public Meld copy() {
        Meld m = new Meld();
        for (int i=0; i<this.getTiles().size(); i++) {
            if(!(this.getTiles().get(i).isJoker())){
                Tile t = new Tile(this.getTiles().get(i).getColour(), this.getTiles().get(i).getValue());
                m.add(t);
            }  else {
                Joker j = new Joker();
                j.setColour(this.getTiles().get(i).getColour());
                j.setValue(this.getTiles().get(i).getValue());
                m.add(j);
            }

        }
        m.sort();
        return m;
    }


    public Tile remove(Tile t) {
        for (int i=0; i<tiles.size(); i++) {
            if (tiles.get(i).equals(t)) {
                return tiles.remove(i);   
            }
        }
        return null;
    }

    public Tile get(Tile t) {
        for (int i=0; i<tiles.size(); i++) {
            if (tiles.get(i).equals(t)) {
                return tiles.get(i);   
            }
        }
        return null;
    }

    public Tile get(int id) {
     return tiles.get(id);
    }

    public int getScore() {
        int score = 0;
        for (int i=0; i<this.size(); i++) {
            if (this.get(i).isJoker()) {
                score += 30;
            }
            else {
                score += this.get(i).getValue();
            }
        }
        return score;
    }

    /*special function designed to add a joker to a meld by inspecting the meld and
    determine what value and colour the meld should take on.
    This is particularly important for sets.
    */
    public void addJoker(Tile joker) {
        joker.setColour('J');
		joker.setValue(0);
        /*
        -> inspect the meld
        -> if the meld is a set and the set is less than size 4:
                ->add the joker and set the joker's # to the # of the set
        -> if the meld is a run, look for holes in the continuity of the numbers. 
                -> if a hole exists, put it there
                -> otherwise, add it to the back
                -> if there is no back, add it to the front
        */

        if (this.size() == 1) {
            joker.setValue(this.get(0).getValue()); //make a set
        }

        else if (this.size() > 1) {
            //isRun() only works for whole melds so check manually here
            if (tiles.get(0).getValue() == tiles.get(1).getValue()) { //adding a joker to a set
                joker.setValue(this.get(0).getValue());
            }
            else if (tiles.get(0).getColour() == tiles.get(1).getColour()) { //adding a joker to a run
                joker.setColour(tiles.get(0).getColour());

                for (int i=0; i<this.size()-1; i++) {
                    if (this.get(i+1).getValue() - this.get(i).getValue() != 1) {
                        joker.setValue(this.get(i).getValue() + 1);
                    }
                }
                if (joker.getValue() == 0) { //if a slot for joker has not been found
                    if (tiles.get(0).getValue() != 1) {
                        joker.setValue(tiles.get(0).getValue() - 1);
                    }
                    else if (tiles.get(this.size()-1).getValue() != 13) {
                        joker.setValue(tiles.get(tiles.size()-1).getValue() + 1);
                    }
					else {
						joker.setColour('J');
					}
                }
            }
        }
        this.sort();
    }

    public void checkJokerValues(){
        ArrayList<Character> seenColours = new ArrayList<>();
        ArrayList<Integer> seenNumbers = new ArrayList<>();
        for(Tile t : this.tiles){
            if(!t.isJoker()){
                if(!seenColours.contains(t.getColour())){ //RUN OR SET
                    seenColours.add(t.getColour());
                }
                seenNumbers.add(t.getValue());
            }
        }


        if(seenColours.size() == 1){ //run
            ArrayList<Integer> required = new ArrayList<>();
            Collections.sort(seenNumbers);
            int last = -1;
            for(int curr : seenNumbers){
                if(last != -1 && curr-last > 1){
                    required.add(curr-last);
                }
                last = curr;
            }

            int jokerIndex = 0;
            for(Tile t : this.tiles){
                if(t.isJoker()){
                    t.setValue(required.get(jokerIndex));
                    t.setColour(seenColours.get(0));
                    jokerIndex++;
                }
            }
        } else { //set
            ArrayList<Character> requiredColours = new ArrayList<>();
            char[] allColours = {'R', 'G', 'B', '0'};
            for(char colour : allColours){
                if(!seenColours.contains(colour)){
                    requiredColours.add(colour);
                }
            }

            int colourIndex = 0;
            for(Tile t : this.tiles){
                if(t.isJoker()){
                    t.setValue(this.tiles.get(0).getValue());
                    t.setColour(requiredColours.get(colourIndex));
                    colourIndex++;
                }
            }
        }

        /*else if (this.size() > 1) {
            //isRun() only works for whole melds so check manually here
            if (tiles.get(0).getValue() == tiles.get(1).getValue()) { //adding a joker to a set
                joker.setValue(this.get(0).getValue());
            }
            else if (tiles.get(0).getColour() == tiles.get(1).getColour()) { //adding a joker to a run
                joker.setColour(tiles.get(0).getColour());
                for (int i=0; i<this.size()-1; i++) {
                    if (this.get(i+1).getValue() - this.get(i).getValue() != 1) {
                        joker.setValue(this.get(i).getValue() + 1);
                    }
                }
                if (joker.getValue() == 0) { //if a slot for joker has not been found
                    if (tiles.get(0).getValue() != 1) {
                        joker.setValue(tiles.get(0).getValue() - 1);
                    }
                    else {
                        joker.setValue(tiles.get(tiles.size()-1).getValue() + 1);
                    }
                }
            }
        }*/
    }

    /*
        Sorts the meld by colour and by number
        Differentiates between a run and a meld (to save time), then overrides default collections
        comparator 
    */
    public void sort() {
        int jokers = this.getJokers();
		if (jokers > 0 && this.size() > 2) {
			ArrayList<Tile> list = new ArrayList<>();
            for (int i=0; i<this.size(); i++) {
				if (this.get(i).getColour() == 'J') {
					Tile joker = this.remove(this.get(i));
					list.add(joker);
					i--;
				}
			}
			for (Tile j : list) {
				this.addInSort(j);
			}
        }
        

        //may need something in here to accomodate a joker changing its form

        if (tiles.size() > 1) { //will only sort a meld with any tiles in it
            //Override default comparator to compare by tile value (ints)
            Collections.sort(tiles, new Comparator<Tile>() {
                @Override 
                public int compare(Tile t1, Tile t2) {  
                    if (t1.getColour() > t2.getColour()) {
                        return 1;
                    } else if (t1.getColour() < t2.getColour()) {
                        return -1;
                    }
                    if (t1.getValue() > t2.getValue()) {
                        return 1;
                    } else if (t1.getValue() < t2.getValue()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

    }

    /* 
        Returns whether or not the meld is valid (aka if it follows the rules)
        Checks size, then tests either a run or a set
        Both runs and sets can be incorrect either by color or number; isValid() tests both
        Return: boolean
    */
    public boolean isValid() {
        //valid melds must have 3+ tile
        if (tiles.size() < 3) {
            //System.out.println("fail 1");
            return false;
        }
        if (tiles.get(0).getColour() == tiles.get(1).getColour() &&
			tiles.get(0).getValue() != tiles.get(1).getValue()) { //test a run
			if (this.size() > 13) {
                //System.out.println("fail 2");
                return false;
            }
            for (int i=1; i<tiles.size(); i++) {
                if (tiles.get(i).getColour() != tiles.get(0).getColour()) { //make sure all are same colour
                    //System.out.println("fail 3");
                    return false;
                }
                if (tiles.get(i).getValue() != (tiles.get(0).getValue() + i)) { //make sure all values make a run
                    //System.out.println("fail 4");
                    return false;
                }
            }
        } else { //test a set
            Set<Character> colours = new HashSet<>();
            for (int i=0; i<tiles.size(); i++) {
                if (tiles.get(i).getValue() != tiles.get(0).getValue()) { //all are same value
                    //System.out.println("fail 5");
					return false;
                }

                if (colours.contains(tiles.get(i).getColour()) && tiles.get(i).getColour() != 'J') { //check for duplicate colours
                    //System.out.println("fail 6");
					return false;
                } else {
					colours.add(tiles.get(i).getColour()); //keep track of all the colours this set has
                }
            }
            if (this.size() > 4) { //only possible if there are 5 cards, including a joker
                //System.out.println("fail 7");
				return false;
            }
        }

        return true; 
    }

    public boolean isValid(Tile t) {
        if (t == null) {
            return false;
        }
        Meld m = this.copy();
        m.add(t);
        if (m.isValid()) {
            return true;
        }
        else {
            return false;
        }
    }


    /*

    */
    public String toString() {
        String m = "{";
        for (int i=0; i<tiles.size(); i++) {
            m += tiles.get(i).toString();
            if (i<tiles.size()-1) {
                m += ", ";
            }
        }
        m += "}";

        return m;
    }

    
    public boolean compare(Meld m) {
        if(this.size() != m.size()){
            return false;
        }
        for (int i=0; i<this.size(); i++) {
            if (this.get(i).getColour() != m.get(i).getColour() ||
                this.get(i).getValue() != m.get(i).getValue()) {
                    return false;
                }
        }
        return true;
    }

    public Tile sameValue(Tile t) {
        //finds a card of the same value
        for (int i=0; i<this.size(); i++) {
            if (this.get(i).getColour() == t.getColour() &&
                this.get(i).getValue() == t.getValue()) {
                    return this.get(i);
            }
        }
        return null;

    }

    public boolean isRun() { //used specifically for searchSplit
        if (!isValid()) {
            return false;
        }
        else {
            if (tiles.get(0).getColour() == tiles.get(1).getColour()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public int getJokers() {
        int i = 0;
        for (Tile t : this.tiles) {
            if (t.isJoker()) {
                i++;
            }
        }
        return i;
    }

}