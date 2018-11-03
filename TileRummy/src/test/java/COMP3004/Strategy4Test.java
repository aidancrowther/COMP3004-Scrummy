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
public class Strategy4Test{
    
    Tile tile1 = new Tile('O', 10);
    Tile tile2 = new Tile('O', 9);
    Tile tile3 = new Tile('O', 8);
    Tile tile4 = new Tile('B', 6);
    Tile tile5 = new Tile('G', 6);
    Tile tile6 = new Tile('O', 7);
    Tile tile7 = new Tile('R', 6);
    Tile tile8 = new Tile('O', 6);
    Tile tile9 = new Tile('O', 5);

    ArtificialIntelligence AI = new Strategy4();

    Meld hand = new Meld();
    Table table = new Table();
    Table output = new Table();
    ArrayList<Meld> melds = new ArrayList<>();

    @Test
    //Test that the AI doesn't play if it can't break 30 points
    public void testCantBreak30(){
        //Give the AI a hand that can't break 30
        hand.add(tile1); //O10
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        AI.setHand(hand);
        AI.setTable(table);

        //Assert that nothing is played
        output = AI.play(hand);
        assertTrue(output.getMelds().size() == 1);
    }

    @Test
    //Test that the AI can play to break 30 points
    public void testCanBreak30(){
        //Give the AI a hand that can't break 30
        hand.add(tile1); //O10
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        hand.add(tile6); //O7
        AI.setHand(hand);
        AI.setTable(table);

        Meld expected = new Meld();
        expected.add(tile1); //O10
        expected.add(tile2); //O9
        expected.add(tile3); //O8
        expected.add(tile6); //O7

        //Assert that a meld breaking 30 is played
        output = AI.play(hand);
        assertTrue(output.getMelds().size() == 2);
        assertTrue(output.getMelds().get(1).compare(expected));
    }

    @Test
    //Test that AI will not wait for a card if the odds of getting it are low
    public void testWontWaitLowOdds(){
        //Give the AI a hand without a 10
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        hand.add(tile6); //O7
        AI.setHand(hand);
        AI.setTable(table);
        //Set score to 30 so it will play
        AI.setScore(30);

        Meld expected = new Meld();
        expected.add(tile2); //O9
        expected.add(tile3); //O8
        expected.add(tile6); //O7

        //Assert that a new meld is played
        output = AI.play(hand);
        assertTrue(output.getMelds().size() == 2);
        assertTrue(output.getMelds().get(1).compare(expected));
    }

    @Test
    //Test that the AI will play to existing melds on low ods
    public void testPlayToTableLowOdds(){
        //Give the AI a hand that can play to the table or could wait
        hand.add(tile8); //O6
        hand.add(tile6); //O7
        AI.setHand(hand);
        AI.setTable(table);
        //Set score to 30 so it will play
        AI.setScore(30);

        table = new Table();
        table.add(tile1); //O10
        table.add(tile2); //O9
        table.add(tile3); //O8

        Meld expected = new Meld();
        expected.add(tile1); //O10
        expected.add(tile2); //O9
        expected.add(tile3); //O8
        expected.add(tile6); //O7
        expected.add(tile8); //O6

        //Assert that a meld on the table is played to
        output = AI.play(hand);
        assertTrue(output.getMelds().size() == 2);
        assertTrue(output.getMelds().get(1).compare(expected));
    }

    @Test
    //Test tht the AI will not play to the table on high odds of receiving a given card
    public void testHoldOnHighOdds(){
        table = new Table();
        char colours[] = {'O', 'G', 'B', 'R'};
        for(int i=0; i<8; i++){
            Meld toAdd = new Meld();
            for(int j=1; j<=13; j++){
                toAdd.add(new Tile(colours[i%4], j));
            }
            if((i%4) != 0) table.add(toAdd);
        }

        hand = new Meld();
        hand.add(tile2); //O9
        hand.add(tile3); //O8
        hand.add(tile6); //O7

        AI.setHand(hand);
        AI.setTable(table);
        AI.setScore(30);

        //Assert that the AI doesn't play to the table due to the high odds of receiving tiles it can use
        output = AI.play(hand);
        assertTrue(output.getMelds().size() == 1);
    }

}