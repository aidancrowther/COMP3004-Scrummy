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


public class Strategy3Test {

    /*
    - Test breaking 30
    - Test the observer
    - Test playing when observer calls Strategy 1 (another player has 3 fewer tiles)
    - Test playing when observer calls Strategy 2 (no other players have 3 fewer tiles)
    - Test different table states
    */


    public void testDifferentTableStates() {
        //does play() react appropriately to:
        //    Table it can't play
        //    Table it can play melds and add to melds
        //    Table it can only play melds
        //    Table it can only add to preexisting melds

        //Generate tiles to use
        Tile tile1 = new Tile('O', 10);
        Tile tile2 = new Tile('O', 9);
        Tile tile3 = new Tile('O', 8);
        Tile tile4 = new Tile('B', 6);
        Tile tile5 = new Tile('G', 6);
        Tile tile6 = new Tile('O', 7);
        Tile tile7 = new Tile('R', 6);
        Tile tile8 = new Tile('O', 6);

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

        //Update the table so that the player can only add to existing melds
        table = new Table();
        table.add(tile7);
        table.add(tile8);
        table.add(tile5);
        AI3.setTable(table);

        //Derive our expected output
        Meld expected = new Meld();
        expected.add(tile7);
        expected.add(tile8);
        expected.add(tile5);
        expected.add(tile4);

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI3.play(AI3.getHand());
        ArrayList<Meld> melds = output.getMelds();

        assertTrue(melds.size() == 2);
        assertTrue(melds.get(1).compare(expected));

        //Create a table onto which the AI can only add a new meld
        table = new Table();
        AI3.setTable(table);

        //Create a hand to play with
        hand = new Meld();
        hand.add(tile5);
        hand.add(tile7);
        hand.add(tile4);
        AI3.setHand(hand);

        //Derive our expected output
        expected = new Meld();
        expected.add(tile7);
        expected.add(tile4);
        expected.add(tile5);

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI3.play(AI3.getHand());
        melds = output.getMelds();

        assertTrue(melds.size() == 2);
        assertTrue(melds.get(1).compare(expected));

        //Create a table onto which the AI can add a new meld, and modify an existing one
        table = new Table();
        table.add(tile2);
        table.add(tile3);
        table.add(tile6);
        AI3.setTable(table);

        //Create a hand to play with
        hand = new Meld();
        hand.add(tile5);
        hand.add(tile7);
        hand.add(tile4);
        hand.add(tile1);
        AI3.setHand(hand);

        //Derive our expected outputs
        Meld expected1 = new Meld();
        expected1.add(tile7);
        expected1.add(tile5);
        expected1.add(tile4);

        Meld expected2 = new Meld();
        expected2.add(tile1);
        expected2.add(tile2);
        expected2.add(tile3);
        expected2.add(tile6);

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI3.play(AI3.getHand());

        melds = output.getMelds();
        assertTrue(melds.size() == 3);
        
        Boolean expected1Found = false;
        Boolean expected2Found = false;

        for(Meld meld : melds){
            if(meld.size() > 0){
                expected1Found |= expected1.compare(meld);
                expected2Found |= expected2.compare(meld);
            }
        }

        assertTrue(expected1Found);
        assertTrue(expected2Found);
    }





}