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

public class Strategy4 extends ArtificialIntelligence
{

    public Strategy4(){
        
    }

    @Override
    public Table play(){
        return play(hand);
    }

    public Table play(Meld hand){
        return null;
    }

    //Return a mapping of tiles and the probability of drawing them next
    private HashMap<String, Double> getOdds(){

        HashMap<String, Double> results = new HashMap<>();
        char colours[] = {'O', 'G', 'B', 'R'};
        int totalSeen = 0;

        for(int i=0; i<4; i++){
            for(int j=1; j<=13; j++){
                results.put(new Tile(colours[i], j).toString(), 0.0);
            }
        }

        for(Meld m : table.getMelds()){
            for(Tile t : m.getTiles()){results.put(t.toString(), results.get(t.toString())+1);
                totalSeen++;
            }
        }

        for(Tile t : hand.getTiles()){results.put(t.toString(), results.get(t.toString())+1);
            totalSeen++;
        }

        for(Map.Entry<String, Double> e : results.entrySet()){
            results.put(e.getKey(), ((2-e.getValue())/((52*2)-totalSeen)));
        }

        return results;
    }

}