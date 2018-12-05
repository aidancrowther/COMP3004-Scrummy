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

    public Meld replace(Meld toAdd, int index) {

        if (index == 0 && melds.size() <= index) {
            return null;
        }

        Meld m = melds.get(index).copy();
        set(index, toAdd);
        return m;
    }

    public Meld replace(Meld toAdd, Meld toReplace) {
        int index = 0;
        index = this.getMelds().indexOf(toReplace);
        if (index != 0) {
            set(index, toAdd);
            return toReplace;
        }
        else {
            return null;
        }
    }

    public Meld set(int id, Meld m) {
        return melds.set(id, m);
    }

    /*
        See if the table is valid
        If there is an incomplete meld in melds.get(0), or any completed melds are invalid, return false
        Else return true
    */
    public boolean isValid() {
        //System.out.println(melds.get(0).size());
        this.checkMeldZeroValidAndAppend();
        if (this.melds.get(0).getTiles().size() != 0) {
            System.out.println("meld 0 not empty: " + this.toString());
            return false;
        }
        for (int i=1; i<this.melds.size(); i++) {
            if (!this.melds.get(i).isValid()) {
                System.out.print(i + " is invalid");
                return false;
            }
        }
        return true;
    }

    public boolean checkMeldZeroValidAndAppend(){
        if (melds.size() > 0 && melds.get(0).isValid()) {
            add(melds.get(0).copy());
            melds.get(0).clear();
            return true;
        }
        return false;
    }

    public boolean isEquivalent(Table other) {
        if(other.getMelds().size() != this.melds.size()){
            return false;
        }
        //System.out.println(other.getMelds().size());
        //System.out.println(this.melds.size());
        if(other.getMelds().size() == 1 && this.melds.size() == 1){ //Only blank melds
            return true;
        }

        //CHECK IF NO TILES WERE ADDED EVENT IF MOVED
        ArrayList<Tile> allOthersTiles = new ArrayList<>();
        for(Meld m : other.getMelds()){
            allOthersTiles.addAll(m.getTiles());
        }
        ArrayList<Tile> allThisTiles = new ArrayList<>();
        for(Meld m : this.getMelds()){
            allThisTiles.addAll(m.getTiles());
        }
        System.out.println( allOthersTiles.size() + " OTHER SIZE " + allThisTiles.size());
        if(allOthersTiles.size() == allThisTiles.size()){
            return true;
        }


        int otherSize = other.getMelds().size();
        //MINUS ONE DO NOT CHECK FIRST MELDS
        boolean[] isMeldPresent = new boolean[this.melds.size()-1];
        for(int i = 0; i < isMeldPresent.length-1; i++){
            isMeldPresent[i] = false;
        }

        //FOR ALL MELDS IN THIS TABLE
        for(int i = 1; i < this.melds.size(); i++){
            if(isMeldPresent[i-1] ){ //already found it in other
               break;
            }
            //FOR ALL MELDS IN THE OTHER TABLE
            for(int j = 1; j < otherSize; j++){
                //Only check melds of same size
                if(melds.get(i).getTiles().size() !=  other.getMelds().get(j).getTiles().size()){
                    continue;
                }


                boolean[] areTilesPresent = new boolean[melds.get(i).getTiles().size()];
                for(int k = 0; k < areTilesPresent.length; k++){
                    areTilesPresent[k] = false;
                }

                int tileIndex = 0;

                //FOR ALL TILES IN THIS TABLE'S MELD
                for(Tile t1 : melds.get(i).getTiles()){
                    //FOR ALL TILES IN THE OTHER TABLE'S MELD
                    for(Tile t2 : other.getMelds().get(j).getTiles()) {
                        //IF T1 IS EQUAL TO T2 SET T1 TO FOUND
                        if(t1.equals(t2)){
                            //System.out.println(t1.toString() + " = " + t2.toString());
                            areTilesPresent[tileIndex] = true;
                        } else {
                            //System.out.println(t1.toString() + " != " + t2.toString());
                        }
                    }
                    tileIndex++;
                }

                //if tiles are all FOUND, set meld to found true and break
                boolean allTilesFoundInOtherMeld = true;
                for(int k = 0; k < areTilesPresent.length; k++){
                    if(!areTilesPresent[k]){
                        allTilesFoundInOtherMeld = false;
                    }
                }

                if(allTilesFoundInOtherMeld){
                    isMeldPresent[i-1] = true;
                    break;
                }

            }
        }

        //IF ANY MELD WASN'T FOUND RETURN FALSE
        for(int j = 0; j < isMeldPresent.length; j++){
            //System.out.print(isMeldPresent[j]);
            if(!isMeldPresent[j]){
                return false;
            }
        }

        return true;
    }

    public ArrayList<Meld> getDiff(Table other) {
        ArrayList<Meld> diffMelds = new ArrayList<Meld>();
        if(other.getMelds().size() == 1 && this.melds.size() == 1){ //Only blank melds
            return diffMelds;
        }

        int otherSize = other.getMelds().size();
        //MINUS ONE DO NOT CHECK FIRST MELDS
        boolean[] isMeldPresent = new boolean[this.melds.size()-1];
        for(int i = 0; i < isMeldPresent.length-1; i++){
            isMeldPresent[i] = false;
        }

        //FOR ALL MELDS IN THIS TABLE
        for(int i = 1; i < this.melds.size(); i++){
            if(isMeldPresent[i-1] ){ //already found it in other
                break;
            }
            //FOR ALL MELDS IN THE OTHER TABLE
            for(int j = 1; j < otherSize; j++){
                //Only check melds of same size
                if(melds.get(i).getTiles().size() !=  other.getMelds().get(j).getTiles().size()){
                    continue;
                }


                boolean[] areTilesPresent = new boolean[melds.get(i).getTiles().size()];
                for(int k = 0; k < areTilesPresent.length; k++){
                    areTilesPresent[k] = false;
                }

                int tileIndex = 0;

                //FOR ALL TILES IN THIS TABLE'S MELD
                for(Tile t1 : melds.get(i).getTiles()){
                    //FOR ALL TILES IN THE OTHER TABLE'S MELD
                    for(Tile t2 : other.getMelds().get(j).getTiles()) {
                        //IF T1 IS EQUAL TO T2 SET T1 TO FOUND
                        if(t1.equals(t2)){
                            //System.out.println(t1.toString() + " = " + t2.toString());
                            areTilesPresent[tileIndex] = true;
                        } else {
                            //System.out.println(t1.toString() + " != " + t2.toString());
                        }
                    }
                    tileIndex++;
                }

                //if tiles are all FOUND, set meld to found true and break
                boolean allTilesFoundInOtherMeld = true;
                for(int k = 0; k < areTilesPresent.length; k++){
                    if(!areTilesPresent[k]){
                        allTilesFoundInOtherMeld = false;
                    }
                }

                if(allTilesFoundInOtherMeld){
                    isMeldPresent[i-1] = true;
                    break;
                }

            }
        }

        //IF ANY MELD WASN'T FOUND RETURN FALSE
        for(int j = 0; j < isMeldPresent.length; j++){
            //System.out.print(isMeldPresent[j]);
            if(!isMeldPresent[j]){
                //return false;
                diffMelds.add(this.melds.get(j+1));
            }
        }

        return diffMelds;
    }

    public String prettyString() {
        String str = "";
        int i= 0;
        //if(this.melds.size() == 0){
            //System.out.println("no melds at all");
        //}
        //if(this.melds.size() == 1){
        //    System.out.println("Only meld 0");
        //}
        for( Meld m : this.melds){
            for( Tile t : m.getTiles() ) {
                str += i + " |" + Character.toString(t.getColour()) + "-" + Integer.toString(t.getValue()) + "| ";
            }
            str += "\n";
            i++;
        }
        return str;
    }

    public Table copy(){
        Table copy = new Table();
        int i = 0;
        for(Meld m: this.melds) {
            if(i != 0){
                Meld newM = new Meld();
                for(Tile t: m.getTiles()) {
                    newM.add(t);
                }
                copy.add(newM);
            }
            i++;
        }
        return copy;
    }

    @Override
    public String toString() { return prettyString(); }
}