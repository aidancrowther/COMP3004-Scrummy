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


    Tile t1 = new Tile('B', 2);
    Tile t2 = new Tile('G', 2);
    Tile t3 = new Tile('O', 2);
    Tile t4 = new Tile('R', 2);
    Tile t5 = new Tile('B', 10);
    Tile t6 = new Tile('G', 10);
    Tile t7 = new Tile('O', 10);
    Tile t8 = new Tile('R', 10);
    Tile t9 = new Tile('B', 6);
    Tile t10 = new Tile('G', 6);
    Tile t11 = new Tile('O', 6);
    Tile t12 = new Tile('R', 6);

 

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
        m1.add(t9);
        m1.add(t10);
        m1.add(t11);
        Meld m2 = new Meld();
        m2.add(t5);
        m2.add(t6);
        m2.add(t7);

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
        m1.add(t9);
        m1.add(t10);
        m1.add(t11);
        Meld m2 = new Meld();
        m2.add(t5);
        m2.add(t6);
        m2.add(t7);
        m2.add(t8);

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
        m1.add(t1);
        m1.add(t2);
        m1.add(t3);
        m1.add(t9);
        m1.add(t10);
        m1.add(t11);
        m1.add(t12);
        Meld m2 = new Meld();
        m2.add(t5);
        m2.add(t6);
        m2.add(t7);
       

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
        m1.add(t2);
        m1.add(t3);
        m1.add(t4);
        m1.add(t5);
        m1.add(t6);
        m1.add(t7);
        Meld m2 = new Meld();
        m2.add(t9);
        m2.add(t10);
        m2.add(t11);
       

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
        m1.add(t1);
        m1.add(t2);
        m1.add(t3);
        m2.add(t5);
        m2.add(t6);
        m2.add(t7);

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

    public void testCanPlayRuns() {






    }












}
