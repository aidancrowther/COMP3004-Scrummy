package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import COMP3004.artificial_intelligence.*;
import COMP3004.models.Scrummy;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import org.junit.Test;


public class Strategy3Test {



    public void testBreak30() {
        //Ensure play() does not change the table if it cannot break 30
        //and does break 30 if it can

        //Generate tiles to use
        Tile tile1 = new Tile('O', 9);
        Tile tile2 = new Tile('O', 8);
        Tile tile3 = new Tile('O', 7);
        Tile tile4 = new Tile('O', 10);

        Meld m1 = new Meld();
        m1.add(new Tile('R', 1));
        m1.add(new Tile('R', 2));
        m1.add(new Tile('R', 3));

        //Initialize a hand that can't generate a play of 30
        Meld hand = new Meld();
        hand.add(tile1);
        hand.add(tile2);
        hand.add(tile3);

        //Initialize AI with the specified hand
        Strategy3 AI3 = new Strategy3();
        AI3.setHand(hand);
        AI3.setScore(0);

        //Build a table
        Table table = new Table();
        table.add(m1);
        AI3.setTable(table);

        //Assert that the player does not play onto the table
        Table output1 = AI3.play(AI3.getHand());
        assertTrue(output1.getMelds().size() == 2);

        //Assert that the player does play onto the table when 30 is available
        hand.add(tile4);
        AI3.setHand(hand);

        Table output2 = AI3.play(AI3.getHand());
        assertTrue(output2.getMelds().size() == 3);

    }

    @Test
    public void testObserver() {
        Scrummy scrummy = new Scrummy(); 
        



    }


    @Test
    public void testPlayWithoutHand() {
        //Test playing when observer results give Strategy 2 (no other players have 3 fewer tiles)
    
    }

    @Test
    public void testPlayWithHand() {
        //Test playing when observer results give Strategy 1 (another player has 3 fewer tiles)
        

        


    }






}