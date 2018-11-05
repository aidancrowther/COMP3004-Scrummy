package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import COMP3004.artificial_intelligence.*;
import COMP3004.models.*;
import org.junit.Test;

//Test all AI strategies 1 - 4
public class StrategyMoreTests {


    Strategy1 AI1 = new Strategy1();
    Strategy2 AI2 = new Strategy2();
    Strategy3 AI3 = new Strategy3();
    Strategy4 AI4 = new Strategy4();    

    @Test
    public void testBreak30WithOneMeld() {
        //ITERATION GRID #4a1
        //Proves it works for 4+ player types

        //observer
        Table table = new Table();
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld(); 
        m1.add(new Tile('B', 6));
        m1.add(new Tile('G', 6));
        m1.add(new Tile('O', 6));
        Meld m2 = new Meld();
        m2.add(new Tile('B', 10));
        m2.add(new Tile('G', 10));
        m2.add(new Tile('O', 10));

        table.add(m1);

        AI1.setHand(m2.copy());
        AI1.setScore(0);
        AI1.setTable(table);

        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().size() == 3);
        assertTrue(AI1.getScore() == 30);

        AI2.setHand(m2.copy());
        AI2.setScore(0);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().size() == 3);
        assertTrue(AI2.getScore() == 30);

        AI3.setHand(m2.copy());
        AI3.setScore(0);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().size() == 3);
        assertTrue(AI3.getScore() == 30);

        AI4.setHand(m2.copy());
        AI4.setScore(0);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().size() == 3);
        assertTrue(AI4.getScore() == 30);

    }


    @Test
    public void testBreakOver30WithOneMeld() {
        Table table = new Table();
        //ITERATION GRID #4a2
        //Proves it works for 4+ player types

        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld(); ;
        m1.add(new Tile('B', 6));
        m1.add(new Tile('G', 6));
        m1.add(new Tile('O', 6));
        Meld m2 = new Meld();
        m2.add(new Tile('B', 10));
        m2.add(new Tile('G', 10));
        m2.add(new Tile('O', 10));
        m2.add(new Tile('R', 10));

        table.add(m1);

        AI1.setHand(m2.copy());
        AI1.setScore(0);
        AI1.setTable(table);

        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().size() == 3);
        assertTrue(AI1.getScore() == 40);

        AI2.setHand(m2.copy());
        AI2.setScore(0);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().size() == 3);
        assertTrue(AI2.getScore() == 40);

        AI3.setHand(m2.copy());
        AI3.setScore(0);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().size() == 3);
        assertTrue(AI3.getScore() == 40);

        AI4.setHand(m2.copy());
        AI4.setScore(0);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().size() == 3);
        assertTrue(AI4.getScore() == 40);

    }



    @Test
    public void testBreak30WithTwoMelds() {
        Table table = new Table();
        //ITERATION GRID #4b1
        //Proves it works for 4+ player types

        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        m1.add(new Tile('B', 2));
        m1.add(new Tile('G', 2));
        m1.add(new Tile('O', 2));
        m1.add(new Tile('B', 6));
        m1.add(new Tile('G', 6));
        m1.add(new Tile('O', 6));
        m1.add(new Tile('R', 6));
        Meld m2 = new Meld();
        m2.add(new Tile('B', 10));
        m2.add(new Tile('G', 10));
        m2.add(new Tile('O', 10));
       

        table.add(m2);

        AI1.setHand(m1.copy());
        AI1.setScore(0);
        AI1.setTable(table);

        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().size() == 4);
        assertTrue(AI1.getScore() == 30);

        AI2.setHand(m1.copy());
        AI2.setScore(0);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().size() == 4);
        assertTrue(AI2.getScore() == 30);

        AI3.setHand(m1.copy());
        AI3.setScore(0);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().size() == 4);
        assertTrue(AI3.getScore() == 30);

        AI4.setHand(m1.copy());
        AI4.setScore(0);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().size() == 4);
        assertTrue(AI4.getScore() == 30);
    }

    @Test
    public void testBreakOver30WithTwoMelds() {
        Table table = new Table();
        //ITERATION GRID #4b2
        //Proves it works for 4+ player types

        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        m1.add(new Tile('G', 2));
        m1.add(new Tile('O', 2));
        m1.add(new Tile('R', 2));
        m1.add(new Tile('B', 10));
        m1.add(new Tile('G', 10));
        m1.add(new Tile('O', 10));
        Meld m2 = new Meld();
        m2.add(new Tile('B', 6));
        m2.add(new Tile('G', 6));
        m2.add(new Tile('O', 6));
       

        table.add(m2);

        AI1.setHand(m1.copy());
        AI1.setScore(0);
        AI1.setTable(table);

        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().size() == 4);
        assertTrue(AI1.getScore() > 30);

        AI2.setHand(m1.copy());
        AI2.setScore(0);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().size() == 4);
        assertTrue(AI2.getScore() > 30);

        AI3.setHand(m1.copy());
        AI3.setScore(0);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().size() == 4);
        assertTrue(AI3.getScore() > 30);

        AI4.setHand(m1.copy());
        AI4.setScore(0);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().size() == 4);
        assertTrue(AI4.getScore() > 30);
    }

    @Test
    public void testWinOnFirstTurn() {
        //ITERATION GRID #4c

        Table table = new Table();
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        Meld m2 = new Meld();
        m1.add(new Tile('B', 2));
        m1.add(new Tile('G', 2));
        m1.add(new Tile('O', 2));
        m2.add(new Tile('B', 10));
        m2.add(new Tile('G', 10));
        m2.add(new Tile('O', 10));

        table.add(m1);

        AI1.setHand(m2.copy());
        AI1.setScore(0);
        AI1.setTable(table);

        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().size() == 3);
        assertTrue(AI1.getHand().getTiles().isEmpty());

        AI2.setHand(m2.copy());
        AI2.setScore(0);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().size() == 3);
        assertTrue(AI2.getHand().getTiles().isEmpty());

        AI3.setHand(m2.copy());
        AI3.setScore(0);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().size() == 3);
        assertTrue(AI3.getHand().getTiles().isEmpty());

        AI4.setHand(m2.copy());
        AI4.setScore(0);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().size() == 3);
        assertTrue(AI4.getHand().getTiles().isEmpty());

    }

    @Test
    public void testCanPlayRuns() {
        //ITERATION GRID 8a AND 8c

        Table table = new Table();
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        m1.add(new Tile('B', 2));
        m1.add(new Tile('G', 2));
        m1.add(new Tile('O', 2));
        Meld m2 = new Meld();
        m2.add(new Tile('O', 9));
        m2.add(new Tile('O', 10));
        m2.add(new Tile('O', 11));

        table.add(m1);

        //Try working with one meld first (8a)
        AI1.setHand(m2.copy());
        AI1.setScore(30);
        AI1.setTable(table);
        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().get(2).isRun());

        AI2.setHand(m2.copy());
        AI2.setScore(30);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().get(2).isRun());

        AI3.setHand(m2.copy());
        AI3.setScore(30);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().get(2).isRun());

        AI4.setHand(m2.copy());
        AI4.setScore(30);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().get(2).isRun());

        //Try adding multiple runs at once (8c)
        m2.add(new Tile('G', 6));
        m2.add(new Tile('G', 7));
        m2.add(new Tile('G', 8));
        m2.add(new Tile('G', 9));

        AI1.setHand(m2.copy());
        AI1.setScore(30);
        AI1.setTable(table);
        output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().get(3).isRun());
        assertTrue(output1.getMelds().size() == 4);

        AI2.setHand(m2.copy());
        AI2.setScore(30);
        AI2.setTable(table);
        output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().get(3).isRun());
        assertTrue(output2.getMelds().size() == 4);

        AI3.setHand(m2.copy());
        AI3.setScore(30);
        AI3.setTable(table);
        output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().get(3).isRun());
        assertTrue(output3.getMelds().size() == 4);

        AI4.setHand(m2.copy());
        AI4.setScore(30);
        AI4.setTable(table);
        output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().get(3).isRun());
        assertTrue(output4.getMelds().size() == 4);
    }

    @Test
    public void testCanPlaySets() {
        //ITERATION GRID 8b AND 8d

        Table table = new Table();
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        m1.add(new Tile('B', 2));
        m1.add(new Tile('G', 2));
        m1.add(new Tile('O', 2));
        Meld m2 = new Meld();
        m2.add(new Tile('O', 9));
        m2.add(new Tile('R', 9));
        m2.add(new Tile('G', 9));

        table.add(m1);

        //Try working with one set first (8b)
        AI1.setHand(m2.copy());
        AI1.setScore(30);
        AI1.setTable(table);
        Table output1 = AI1.play(AI1.getHand());
        assertFalse(output1.getMelds().get(2).isRun());

        AI2.setHand(m2.copy());
        AI2.setScore(30);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertFalse(output2.getMelds().get(2).isRun());

        AI3.setHand(m2.copy());
        AI3.setScore(30);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertFalse(output3.getMelds().get(2).isRun());

        AI4.setHand(m2.copy());
        AI4.setScore(30);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertFalse(output4.getMelds().get(2).isRun());

        //Try adding multiple sets at once (8d)
        m2.add(new Tile('G', 6));
        m2.add(new Tile('R', 6));
        m2.add(new Tile('O', 6));
        m2.add(new Tile('B', 6));

        AI1.setHand(m2.copy());
        AI1.setScore(30);
        AI1.setTable(table);
        output1 = AI1.play(AI1.getHand());
        assertFalse(output1.getMelds().get(3).isRun());
        assertTrue(output1.getMelds().size() == 4);

        AI2.setHand(m2.copy());
        AI2.setScore(30);
        AI2.setTable(table);
        output2 = AI2.play(AI2.getHand());
        assertFalse(output2.getMelds().get(3).isRun());
        assertTrue(output2.getMelds().size() == 4);

        AI3.setHand(m2.copy());
        AI3.setScore(30);
        AI3.setTable(table);
        output3 = AI3.play(AI3.getHand());
        assertFalse(output3.getMelds().get(3).isRun());
        assertTrue(output3.getMelds().size() == 4);

        AI4.setHand(m2.copy());
        AI4.setScore(30);
        AI4.setTable(table);
        output4 = AI4.play(AI4.getHand());
        assertFalse(output4.getMelds().get(3).isRun());
        assertTrue(output4.getMelds().size() == 4);
    }

    @Test
    public void testPlayRunsAndSets() {
        //ITERATION GRID 8e

        Table table = new Table();
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        m1.add(new Tile('B', 2));
        m1.add(new Tile('G', 2));
        m1.add(new Tile('O', 2));
        Meld m2 = new Meld();
        m2.add(new Tile('O', 9));
        m2.add(new Tile('O', 10));
        m2.add(new Tile('O', 11));
        m2.add(new Tile('R', 4));
        m2.add(new Tile('R', 5));
        m2.add(new Tile('R', 6));

        table.add(m1);

        //Try working with one meld first (8a)
        AI1.setHand(m2.copy());
        AI1.setScore(30);
        AI1.setTable(table);
        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().size() == 4);

        AI2.setHand(m2.copy());
        AI2.setScore(30);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().size() == 4);

        AI3.setHand(m2.copy());
        AI3.setScore(30);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().size() == 4);

        AI4.setHand(m2.copy());
        AI4.setScore(30);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().size() == 4);

    }

    @Test
    public void testAddToRun() {
        //ITERATION GRID 9a AND 9c

        Table table = new Table();
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        m1.add(new Tile('O', 3));
        m1.add(new Tile('O', 4));
        m1.add(new Tile('O', 5));
        m1.add(new Tile('O', 6));
        Meld m2 = new Meld();
        m2.add(new Tile('O', 2));
        m2.add(new Tile('O', 7));
        m2.add(new Tile('O', 8));

        table.add(m1);

        //Try working with one meld first (8a)
        AI1.setHand(m2.copy());
        AI1.setScore(30);
        AI1.setTable(table);
        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().get(1).size() == 7);

        AI2.setHand(m2.copy());
        AI2.setScore(30);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().get(1).size() == 7);

        AI3.setHand(m2.copy());
        AI3.setScore(30);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().get(1).size() == 7);

        AI4.setHand(m2.copy());
        AI4.setScore(30);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().get(1).size() == 7);

    }

    @Test
    public void testAddToSet() {
        //ITERATION GRID 9b

        Table table = new Table();
        Scrummy s = new Scrummy();
        s.registerPlayerHandObserver(AI3);
        AI3.setPlayerHandSizes(s.getPlayers());
        s.notifyObservers();

        Meld m1 = new Meld();
        m1.add(new Tile('O', 5));
        m1.add(new Tile('G', 5));
        m1.add(new Tile('B', 5));
        Meld m2 = new Meld();
        m2.add(new Tile('R', 5));

        table.add(m1);

        //Try working with one meld first (8a)
        AI1.setHand(m2.copy());
        AI1.setScore(30);
        AI1.setTable(table);
        Table output1 = AI1.play(AI1.getHand());
        assertTrue(output1.getMelds().get(1).size() == 4);

        AI2.setHand(m2.copy());
        AI2.setScore(30);
        AI2.setTable(table);
        Table output2 = AI2.play(AI2.getHand());
        assertTrue(output2.getMelds().get(1).size() == 4);

        AI3.setHand(m2.copy());
        AI3.setScore(30);
        AI3.setTable(table);
        Table output3 = AI3.play(AI3.getHand());
        assertTrue(output3.getMelds().get(1).size() == 4);

        AI4.setHand(m2.copy());
        AI4.setScore(30);
        AI4.setTable(table);
        Table output4 = AI4.play(AI4.getHand());
        assertTrue(output4.getMelds().get(1).size() == 4);

    }







}
