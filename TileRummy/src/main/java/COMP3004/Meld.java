package COMP3004;

import java.util.*;



public class Meld {
    private ArrayList<Tile> tiles = new ArrayList<>();


    //constructor
    public Meld() {
    }

    //gets meld contents
    public ArrayList<Tile> getTiles() { return tiles; }


    public void add(Tile t) { 
        this.tiles.add(t);
        //sort();
    }

    public void remove(Tile t) { 
        for (int i=0; i<tiles.size(); i++) {
            if (tiles.get(i).getColour() == t.getColour() && tiles.get(i).getValue() == t.getValue()) {
                tiles.remove(i);
                break;
            }
        }
    }

    public void sort() {
        if (tiles.size() > 0) {
            if (tiles.get(0).getColour() == tiles.get(1).getColour()) { //sort a run
                Collections.sort(tiles, new Comparator<Tile>() {
                    @Override //override default comparator
                    public int compare(Tile t1, Tile t2) {  
                        if (t1.getValue() > t2.getValue()) { 
                            return 1;
                        } else if (t1.getValue() < t2.getValue()) {
                            return -1;
                        }
                        return 0;
                    }
                });
            } else { //sort a set
                Collections.sort(tiles, new Comparator<Tile>() {
                    @Override //override default comparator
                    public int compare(Tile t1, Tile t2) {  
                        if (t1.getColour() > t2.getColour()) { 
                            return 1;
                        } else if (t1.getColour() < t2.getColour()) {
                            return -1;
                        } 
                        return 0;
                    }
                });
            }
        }

    }

    public boolean isValid() {
        if (tiles.size() < 3) {
            return false;
        }
        //sort(); //just in case
        if (tiles.get(0).getColour() == tiles.get(1).getColour()) { //test a run
            for (int i=1; i<tiles.size(); i++) {
                if (tiles.get(i).getColour() != tiles.get(0).getColour()) { //make sure all are same colour
                    return false;
                }
                if (tiles.get(i).getValue() != (tiles.get(0).getValue() + i)) { //make sure all values make a run 
                    return false;
                }
            }
        } else { //test a set
            Set<Character> colours = new HashSet<Character>();
            for (int i=0; i<tiles.size(); i++) {
                if (tiles.get(i).getValue() != tiles.get(0).getValue()) { //all are same value
                    return false;
                }
                if (colours.contains(tiles.get(i).getColour())) { //check for duplicate colours
                    return false;
                } else {
                    colours.add(tiles.get(i).getColour()); //keep track of all the colours this set has
                }
            }
        }

        return true; //reached the end!
    }









}