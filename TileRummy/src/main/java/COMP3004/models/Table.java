package COMP3004.models;

import java.util.*;

public class Table {
    private ArrayList<Meld> melds = new ArrayList<>();

    //constructor
    public Table() { 
        melds.add(new Meld()); //index 0 meld
    }


    public ArrayList<Meld> getMelds() { return melds; }

    /*
        Adds a complete meld to the table
    */
    public void add(Meld m) {
        melds.add(m);
    }

    /*
        Adds tiles one by one to create a new meld.
        melds.get(0) is always 
        Once a valid meld is detected, the meld is moved to the end of melds
    */
    public void add(Tile t) {
        melds.get(0).add(t);

        if (melds.get(0).isValid()) {
            add(melds.get(0).copy());
            melds.get(0).clear();
        }
    }

    /*
        Add tiles to a preexisting meld
    */
    public void add(Tile t, int i) {
        melds.get(i).add(t);
    }

    public Meld remove(int i) {
        if (i==0) {
            return null;
        } else { 
            return melds.remove(i);
        }
    }

    public Meld remove(Meld m) {
        for (int i=1; i<melds.size(); i++) {
            if (melds.get(i).compare(m)) {
                return melds.remove(i);
            }
        }
        return null;
    }

    /*
        See if the table is valid
        If there is an incomplete meld in melds.get(0), or any completed melds are invalid, return false
        Else return true
    */
    public boolean isValid() {
        if (melds.get(0).getTiles().size() != 0) {
            return false;
        }
        for (int i=1; i<melds.size(); i++) {
            if (!melds.get(i).isValid()) {
                return false;
            }
        }
        return true;
    }



}