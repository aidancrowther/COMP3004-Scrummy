package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import COMP3004.artificial_intelligence.*;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import org.junit.Test;

//Test all AI strategies 1 - 4
public class Strategy2Test{
    
    Tile tile1 = new Tile('O', 10);
    Tile tile2 = new Tile('O', 9);
    Tile tile3 = new Tile('O', 8);
    Tile tile4 = new Tile('B', 6);
    Tile tile5 = new Tile('G', 6);
    Tile tile6 = new Tile('O', 7);
    Tile tile7 = new Tile('R', 6);
    Tile tile8 = new Tile('O', 6);

    ArtificialIntelligence AI = new Strategy2();

    Meld hand = new Meld();
    Table table = new Table();
    Table output = new Table();
    ArrayList<Meld> melds = new ArrayList<>();

    @Test
    //Test the AI with a hand that it can't break with
    public void testCantBreakLessThan30UnplayedTable(){
        //Give them a hand that can't break 30
        hand.clear();
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        hand.add(tile6); //O7

        //Give it an empty table
        table = new Table();
        AI.setTable(table);

        output = AI.play(hand);

        //Assert that the table has been unmodified
        assertTrue(output.getMelds().size() == 1);
    }

    @Test
    //Test the AI with a hand that it can't break with
    public void testCanBreak30UnplayedTable(){
        //Give them a hand that can break 30
        hand.clear();
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        hand.add(tile6); //O7
        hand.add(tile8); //O6

        //Give it an empty table
        table = new Table();
        AI.setTable(table);

        output = AI.play(hand);

        //Assert that the table has been unmodified
        assertTrue(output.getMelds().size() == 1);
    }

    @Test
    //Test the AI with a hand that it can't break with
    public void testCantBreakLessThan30PlayedTable(){
        //Give them a hand that can't break 30
        hand.clear();
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        hand.add(tile6); //O7

        //Give it a used table
        table = new Table();
        table.add(tile4); //B6
        table.add(tile5); //G6
        table.add(tile7); //R6
        AI.setTable(table);

        output = AI.play(hand);

        //Assert that the table has been unmodified
        assertTrue(output.getMelds().size() == 2);
    }

    @Test
    //Test the AI with a hand that it can break with
    public void testCanBreak(){
        //Give them a hand that can't break 30
        hand.clear();
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        hand.add(tile6); //O7
        hand.add(tile8); //O6

        //Give it a used table
        table = new Table();
        table.add(tile4); //B6
        table.add(tile5); //G6
        table.add(tile7); //R6
        AI.setTable(table);

        output = AI.play(hand);
        melds = output.getMelds();

        Meld expected = new Meld();
        expected.add(tile2);
        expected.add(tile3);
        expected.add(tile6);
        expected.add(tile8);

        //Assert that the table has been played to
        assertTrue(output.getMelds().size() == 3);

        Boolean match = false;

        for(Meld m : melds){
            match |= m.compare(expected);
        }

        //Assert that the played meld is correct
        assertTrue(match);
    }
}