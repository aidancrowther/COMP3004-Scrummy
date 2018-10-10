package COMP3004;

import java.util.*;



public class Meld {
    private ArrayList<Tile> tiles = new ArrayList<>();


    //constructor
    public Meld() {
    }

    //gets meld contents
    public ArrayList<Tile> getTiles() { return tiles; }


    /*
        Adds a tile to the current meld 
    */
    public void add(Tile t) { 
        this.tiles.add(t);
        sort();
    }

    /*
        Removes a tile from the meld
        Walks through the arraylist until it finds an identical tile, then removes it.
    */
    public void remove(Tile t) { 
        for (int i=0; i<tiles.size(); i++) {
            if (tiles.get(i).getColour() == t.getColour() && tiles.get(i).getValue() == t.getValue()) {
                tiles.remove(i);   
                break;
            }
        }
    }

    /*
        Sorts the meld by colour and by number
        Differentiates between a run and a meld (to save time), then overrides default collections
        comparator 
    */
    public void sort() {
        if (tiles.size() > 1) { //will only sort a meld with any tiles in it
            //If two tiles have the same colour, assume it's a run.
            if (tiles.get(0).getColour() == tiles.get(1).getColour()) {
                //Override default comparator to compare by tile value (ints)
                Collections.sort(tiles, new Comparator<Tile>() {
                    @Override 
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
                //Override default comparator to compare by tile colour (chars)
                Collections.sort(tiles, new Comparator<Tile>() {
                    @Override 
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

    /* 
        Returns whether or not the meld is valid (aka if it follows the rules)
        Checks size, then tests either a run or a set
        Both runs and sets can be incorrect either by color or number; isValid() tests both
        Return: boolean
    */
    public boolean isValid() {
        //valid melds must have 3+ tiles
        if (tiles.size() < 3) {
            return false;
        }
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