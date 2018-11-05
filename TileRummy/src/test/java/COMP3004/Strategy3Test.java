package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import COMP3004.artificial_intelligence.*;
import COMP3004.models.*;
import org.junit.Test;


public class Strategy3Test {


    @Test
    public void testObserver() {
        Scrummy s = new Scrummy();
        Strategy3 AI3 = new Strategy3();
        AI3.setPlayerHandSizes(s.getPlayers());

        s.registerPlayerHandObserver(AI3);
        s.notifyObservers();
        for (int i=0; i<AI3.getPlayerHandSizesLength(); i++) {
            assertTrue(AI3.getPlayerHandSize(i) == 14);
        }
    }


    @Test
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

        //observer
        Scrummy s = new Scrummy(); //everyone else's hands should be at 14 cards
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        //Assert that the player does not play onto the table
        Table output = AI3.play(AI3.getHand());
        assertTrue(output.getMelds().size() == 2);
    }

    @Test
    public void testCanBreak30() {
        //Generate tiles to use
        Tile tile1 = new Tile('O', 9);
        Tile tile2 = new Tile('O', 10);
        Tile tile3 = new Tile('O', 11);
        Meld m1 = new Meld();
        m1.add(new Tile('R', 1));
        m1.add(new Tile('R', 2));
        m1.add(new Tile('R', 3));

        //Initialize a hand that can generate a play of 30
        Meld hand = new Meld();
        hand.add(tile1);
        hand.add(tile2);
        hand.add(tile3);

        //Initialize AI with the specified hand
        Strategy3 AI3 = new Strategy3();
        AI3.setHand(hand);
        AI3.setScore(0);

        //observer
        Scrummy s = new Scrummy(); //everyone else's hands should be at 14 cards
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        //Build a table
        Table table = new Table();
        table.add(m1);
        AI3.setTable(table);

        Table output = AI3.play(AI3.getHand());
        assertTrue(output.getMelds().size() == 3);

    }


    @Test
    public void testPlayWithoutHand() {

        Meld hand3 = new Meld();
        hand3.add(new Tile('O', 1));
        hand3.add(new Tile('O', 2));
        hand3.add(new Tile('O', 3));
        hand3.add(new Tile('G', 9));
        hand3.add(new Tile('R', 12));

        Meld m1 = new Meld();
        m1.add(new Tile('R', 9));
        m1.add(new Tile('O', 9));
        m1.add(new Tile('B', 9));

        //set table
        Table table = new Table();
        table.add(m1);

        //initialize strategy
        Strategy3 AI3 = new Strategy3();
        AI3.setScore(30);
        AI3.setHand(hand3);
        AI3.setTable(table);

        //observer
        Scrummy s = new Scrummy(); //everyone else's hands should be at 14 cards
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        //ai3 can't make new melds, but it can add to preexisting ones
        Table output = AI3.play(AI3.getHand());
        assertTrue(output.getMelds().size() == 2);      
    
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
        hand3.add(new Tile('O', 11));

        Meld m1 = new Meld();
        m1.add(new Tile('R', 9));
        m1.add(new Tile('O', 9));
        m1.add(new Tile('B', 9));

        //set table
        Table table = new Table();
        table.add(m1);

        //initialize strategy
        Strategy3 AI3 = new Strategy3();
        AI3.setScore(30);
        AI3.setHand(hand3);
        AI3.setTable(table);

        //observer
        Scrummy s = new Scrummy();
        s.getPlayers()[0].setHand(hand0); //player 0's hand is 3 less than p3
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();


        Table output = AI3.play(AI3.getHand());
        assertTrue(output.getMelds().size() == 3);

    }

    @Test
    public void testWinsWithoutTable() {

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
        Strategy3 AI3 = new Strategy3();
        AI3.setScore(30);
        AI3.setHand(hand3);
        AI3.setTable(table);

        //observer
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();


        Table output = AI3.play(AI3.getHand());
        assertTrue(AI3.getHand().size() == 0);
    }

    @Test
    public void testWinsUsingTable() {

        Meld hand3 = new Meld();
        hand3.add(new Tile('O', 1));
        hand3.add(new Tile('O', 2));
        hand3.add(new Tile('O', 3));
        hand3.add(new Tile('O', 4));
        hand3.add(new Tile('G', 9));
        hand3.add(new Tile('R', 12));

        Meld m1 = new Meld();
        m1.add(new Tile('R', 9));
        m1.add(new Tile('O', 9));
        m1.add(new Tile('B', 9));

        Meld m2 = new Meld();
        m2.add(new Tile('R', 8));
        m2.add(new Tile('R', 9));
        m2.add(new Tile('R', 10));
        m2.add(new Tile('R', 11));

        //set table
        Table table = new Table();
        table.add(m1);
        table.add(m2);

        //initialize strategy
        Strategy3 AI3 = new Strategy3();
        AI3.setScore(30);
        AI3.setHand(hand3);
        AI3.setTable(table);

        //observer
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();


        Table output = AI3.play(AI3.getHand());
        assertTrue(AI3.getHand().size() == 0);
    }






}