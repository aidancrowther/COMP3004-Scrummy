package COMP3004;

import java.util.*;


public class Table {
    private ArrayList<Meld> melds = new ArrayList<>();

    //constructor
    public Table() { 
        melds.add(new Meld()); //index 0 meld
    }


    public ArrayList<Meld> getMelds() { return melds; }


    public void add(Meld m) {
        melds.add(m);
    }

    public void add(Tile t) {
        melds.get(0).add(t);

        if (melds.get(0).isValid()) {
            add(new Meld());
			for (int i=0; i<melds.get(0).getTiles().size(); i++) {
				add(melds.get(0).getTiles().get(i), melds.size()-1);
			}
            melds.get(0).clear();
        }
    }

    public void add(Tile t, int i) {
        melds.get(i).add(t);
    }

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