package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertTrue;

import COMP3004.artificial_intelligence.*;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import org.junit.Test;


public class Strategy3Test {



    public void testBreak30() {
        //Ensure play() does not change the table if it cannot break 30

        //Generate tiles to use
        Tile tile2 = new Tile('O', 9);
        Tile tile3 = new Tile('O', 8);
        Tile tile4 = new Tile('B', 6);
        Tile tile6 = new Tile('O', 7);

        //Initialize a hand that can't generate a play of 30
        Meld hand = new Meld();
        hand.add(tile4);

        //Initialize AI with the specified hand
        Strategy3 AI3 = new Strategy3();
        AI3.setHand(hand);
        AI3.setScore(30);

        //Build a table that the player can't add to
        Table table = new Table();
        table.add(tile2);
        table.add(tile3);
        table.add(tile6);
        AI3.setTable(table);

        //Assert that the player does not play onto the table
        Table output = AI3.play(AI3.getHand());
        assertTrue(output.getMelds().size() == 2);

    }

    @Test
    public void testObserver() {
        //Test the observer


    }


    @Test
    public void testRegularPlay() {
        //Test playing when observer calls Strategy 2 (no other players have 3 fewer tiles)
    
    }

    @Test
    public void testPlayWithHand() {
        //Test playing when observer calls Strategy 1 (another player has 3 fewer tiles)
        

        


    }






}