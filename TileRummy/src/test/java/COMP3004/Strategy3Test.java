package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import COMP3004.artificial_intelligence.*;
import COMP3004.controllers.TerminalViewController;
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
    public void testPlayWithoutHand() {

        Meld hand3 = new Meld();
        hand3.add(new Tile('O', 1));
        hand3.add(new Tile('O', 2));
        hand3.add(new Tile('O', 3));
        hand3.add(new Tile('O', 4));
        hand3.add(new Tile('G', 9));

        Meld m1 = new Meld();
        m1.add(new Tile('R', 9));
        m1.add(new Tile('O', 9));
        m1.add(new Tile('B', 9));

        //set table
        Table table = new Table();
        table.add(m1);

        //initialize strategy
        Strategy3 ai3 = new Strategy3();
        ai3.setScore(30);
        ai3.setHand(hand3);
        ai3.setTable(table);

        //observer
        Scrummy s = new Scrummy(); //everyone else's hands should be at 14 cards
        s.registerPlayerHandObserver(ai3);

        //ai3 can't make new melds, but it can add to preexisting ones
        Table output = ai3.play(ai3.getHand());
        assertTrue(output.getMelds().size() == 2);      
        assertTrue(output.getMelds().get(1).size() == 4);
    
    }

    @Test
    public void testPlayWithHand() {
        //Test playing when observer results give Strategy 1 (another player has 3 fewer tiles)

        //melds
        Meld hand0 = new Meld();
        hand0.add(new Tile('G', 12));

        Meld hand3 = new Meld();
        hand3.add(new Tile('O', 1));
        hand3.add(new Tile('O', 2));
        hand3.add(new Tile('O', 3));
        hand3.add(new Tile('O', 4));

        Meld m1 = new Meld();
        m1.add(new Tile('R', 9));
        m1.add(new Tile('O', 9));
        m1.add(new Tile('B', 9));

        //set table
        Table table = new Table();
        table.add(m1);

        //initialize strategy
        Strategy3 ai3 = new Strategy3();
        ai3.setScore(30);
        ai3.setHand(hand3);
        ai3.setTable(table);

        //observer
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(ai3);

        //set other player's hand
        s.getPlayers()[0].setHand(hand0); //player 0's hand is 3 less than p3

        Table output = ai3.play(ai3.getHand());
        assertTrue(output.getMelds().size() == 3);

    }






}