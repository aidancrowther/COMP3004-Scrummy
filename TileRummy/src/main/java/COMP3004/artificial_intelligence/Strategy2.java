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

public class Strategy2 extends ArtificialIntelligence
{

    public Strategy2(){
        
    }

    @Override
    public Table play(){
        return play(hand);
    }

    public Table play(Meld hand){
        return null;
    }
}