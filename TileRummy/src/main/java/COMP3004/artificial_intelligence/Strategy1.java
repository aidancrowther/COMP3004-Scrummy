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
 * AI Strategy1 should do exactly as the spec requires
 * --play when it can, all that it can
 */

package COMP3004.artificial_intelligence;

import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap;

public class Strategy1 extends ArtificialIntelligence
{

    public Strategy1(){
        
    }

    @Override
    public Table play(){
        return play(this.player.getHand());
    }


    /*
     * Terminal View: Loops through the prompts until player enters text to complete their turn, then returns this.table.
     * GUI View: Allows the user to interact with the table until the user presses complete turn button. When button is pressed end the user’s turn, and the method returns this.table.
     * AI: Strategies 1 - 4
     */
    public Table play(Meld hand){
        /*  

            -> HashMap<Meld, int> handResults = searchHand();
            -> HashMap<Meld, int> tableResults = searchTable(table);

            -> ArrayList<ArrayList<Meld>> results

            -> results contains every combination of the Melds within handResults and tableResults
                that do not use the same elements from hand
            -> Pick the largest ArrayList. Remove every tile from hand that is there.
            -> From the largest ArrayList of melds, search for the keyvalue of each meld within its
                original hashmap, and append said meld to the meld specified in the Hashmap
            -> If the specified meld is from the hand, add the entire meld. Otherwise, append by tile

            -> Return the brand new table :)

        */
        this.player.setHand(hand);

        //Get all possible melds
        HashMap<Meld, Integer> handResults = new HashMap<Meld, Integer>();
        HashMap<Meld, Integer> tableResults = new HashMap<Meld, Integer>();
        HashMap<Meld, AbstractMap.SimpleEntry<ArrayList<Meld>, Integer>> splitResults = new HashMap<>();
        
        handResults = searchHand();
        if (score >= 30) {
            tableResults = searchTable(table);
            splitResults = searchSplit(table);
        }

        //Lists to track hand status
        HashMap<Tile, Integer> inHand = new HashMap<>();
        ArrayList<ArrayList<Meld>> results = new ArrayList<>();

        //Output table
        Table output = this.getTableCopy(table);

        //Identify duplicate tiles and keep track of all tiles
        for(Tile tile : hand.getTiles()){
            Boolean found = false;
            for(Map.Entry<Tile, Integer> pair : inHand.entrySet()){
                if(tile.equals(pair.getKey())){
                    inHand.put(pair.getKey(), pair.getValue()+1);
                    found = true;
                    break;
                }
            }
            if(!found) inHand.put(tile, 1);
        }

        //Generate array lists of moves to make
        HashMap<Meld, Integer> allMelds = new HashMap<>();
        for(Map.Entry<Meld, Integer> pair : handResults.entrySet()){
            allMelds.put(pair.getKey(), 0);
        }
        for(Map.Entry<Meld, Integer> pair : tableResults.entrySet()){
            allMelds.put(pair.getKey(), pair.getValue());
        }
        for(Map.Entry<Meld, AbstractMap.SimpleEntry<ArrayList<Meld>, Integer>> pair : splitResults.entrySet()){
            allMelds.put(pair.getKey(), pair.getValue().getValue());
        }

        //Find all sets of melds that can go together
        allMelds = sortByLength(allMelds);
        for(Map.Entry<Meld, Integer> m : allMelds.entrySet()){
            results.add(findUnique(m.getKey(), allMelds, inHand));
        }

        //Find the longest set of melds to use
        int longest = 0;
        ArrayList<Meld> longestList = new ArrayList<>();
        for(ArrayList<Meld> a : results){
            if(a.size() > longest && score >= 30){
                longest = a.size();
                longestList = a;
            }
            else if(listScore(a) > longest && score < 30){
                longest = listScore(a);
                longestList = a;
            }
        }

        //Add each meld to the correct meld on the table, removing the tiles from the players hand
        for(Meld m : longestList){
            //If the meld is being played from the hand
            if(handResults.get(m) != null){
                //Build up the meld to add, removing tiles from the players hand as needed
                Meld toAdd = new Meld();
                for(Tile t : m.getTiles()){
                    this.score += t.getValue(); // this.hand.get(t);
                    toAdd.add(t); //this.hand.remove(t));
                    this.player.getHand().remove(t);
                }
                output.add(toAdd);
            }
            //If the meld is being played onto the table
            else if(tableResults.get(m) != null){
                //Remove all necessary tiles from the players hand, appending them to the specified meld
                for(Tile t : m.getTiles()){
                    output.add(t, tableResults.get(m));
                    hand.remove(t);
                }
            }
            //If the player is splitting
            else if(splitResults.get(m) != null){
                //Build up local variables
                AbstractMap.SimpleEntry<ArrayList<Meld>, Integer> toSplit = splitResults.get(m);
                ArrayList<Meld> meldsToAdd = new ArrayList<>();
                ArrayList<Meld> result = new ArrayList<>();
                int splitId = 0;

                //Get the resultant melds and the id of the meld to split
                meldsToAdd = splitResults.get(m).getKey();
                splitId = splitResults.get(m).getValue();

                //Get the meld that is being split from the table using the id
                Meld beingSplit = output.getMelds().get(splitId);

                //For each meld involved in the split
                for(Meld meld : meldsToAdd){
                    //Build up a meld to add, removing tiles from hand, or getting the reference
                    Meld toAdd = new Meld();
                    for(Tile t : meld.getTiles()){
                        if(indexOf(beingSplit, t) >= 0) toAdd.add(beingSplit.getTiles().get(indexOf(beingSplit, t)));
                        else if(indexOf(hand, t) >= 0) toAdd.add(hand.remove(t));
                    }

                    result.add(toAdd);
                }

                //Add all of out new melds back to the table
                Boolean replaced = false;
                for(Meld meld : result){
                    if(!replaced){
                        output.replace(meld, splitId);
                        replaced = true;
                    }
                    else output.add(meld);
                }
            }
        }

        //Return the output table
        if (longest >= 30 || score >= 30) {
            System.out.println(this.player.getName() + " AI HAS MOVED!");
            this.table = output;
        }
        
        return this.table;
    }

}