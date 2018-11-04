package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import COMP3004.artificial_intelligence.*;
import COMP3004.controllers.Controller;
import COMP3004.models.*;
import org.junit.Test;


public class Strategy3Test {



    public void testCantBreak30() {
        //Ensure play() does not change the table if it cannot break 30

        //Generate tiles to use
        Tile tile1 = new Tile('O', 9);
        Tile tile2 = new Tile('O', 8);
        Tile tile3 = new Tile('O', 7);
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
    }

    @Test
    public void testCanBreak30() {
        //Generate tiles to use
        Tile tile1 = new Tile('O', 9);
        Tile tile2 = new Tile('O', 8);
        Tile tile3 = new Tile('O', 7);
        Tile tile4 = new Tile('O', 10);
        Meld m1 = new Meld();
        m1.add(new Tile('R', 1));
        m1.add(new Tile('R', 2));
        m1.add(new Tile('R', 3));

        //Initialize a hand that can generate a play of 30
        Meld hand = new Meld();
        hand.add(tile1);
        hand.add(tile2);
        hand.add(tile3);
        hand.add(tile4);

        //Initialize AI with the specified hand
        Strategy3 AI3 = new Strategy3();
        AI3.setHand(hand);
        AI3.setScore(0);

        //Build a table
        Table table = new Table();
        table.add(m1);
        AI3.setTable(table);

        Table output2 = AI3.play(AI3.getHand());
        assertTrue(output2.getMelds().size() == 3);

    }

    @Test
    public void testObserver() {
        Controller controller = new Controller();
        

        



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