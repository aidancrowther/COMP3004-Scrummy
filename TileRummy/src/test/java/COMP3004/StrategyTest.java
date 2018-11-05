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
public class StrategyTest{

 

    @Test
    //Assert that update will change the table
    public void testSetHand() {
        Strategy1 AI1 = new Strategy1();
        //Generate a small list of tiles and melds for the test
        ArrayList<Tile> tiles = new ArrayList<>();
        for(int i=1; i<=6; i++) tiles.add(new Tile('O', i));

        Meld meld1 = new Meld();
        meld1.add(tiles.get(0));
        meld1.add(tiles.get(1));
        meld1.add(tiles.get(2));

        for(int i=1; i<=3; i++) assertTrue(meld1.getTiles().get(i-1).toString().equals("O"+i));

        assertTrue(AI1.getPlayer().getHand().compare(new Meld()));
        AI1.setHand(meld1);
        assertTrue(AI1.getPlayer().getHand().equals(meld1));
        meld1 = null;
        assertFalse(AI1.getPlayer().getHand().compare(new Meld()));
    }

    @Test
    //Assert that the selectTile method will move tiles between melds correctly
    public void testSelectTile(){

        Strategy1 AI1 = new Strategy1();
        //Generate a small list of tiles and melds for the test
        ArrayList<Tile> tiles = new ArrayList<>();
        for(int i=1; i<=6; i++) tiles.add(new Tile('O', i));

        Meld meld1 = new Meld();
        meld1.add(tiles.get(0));
        meld1.add(tiles.get(1));
        meld1.add(tiles.get(2));

        Meld meld2= new Meld();
        meld2.add(tiles.get(3));
        meld2.add(tiles.get(4));
        meld2.add(tiles.get(5));

        for(int i=1; i<=3; i++) assertTrue(meld1.getTiles().get(i-1).toString().equals("O"+i));
        for(int i=4; i<=6; i++) assertTrue(meld2.getTiles().get(i-4).toString().equals("O"+i));

        //Test moving a tile that is in meld1 to meld2
        AI1.selectTile(meld1, meld2, tiles.get(0));
        assertFalse(meld1.getTiles().contains(tiles.get(0)));
        assertTrue(meld2.getTiles().contains(tiles.get(0)));

        //Test moving a tile that is not in meld1 to meld2
        AI1.selectTile(meld1, meld2, tiles.get(0));
        assertFalse(meld1.getTiles().size() < 2);
        assertTrue(meld2.getTiles().size() == 4);
    }

    @Test
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
        Strategy1 AI1 = new Strategy1();
        AI1.setHand(hand);
        AI1.setScore(30);

        //Build a table that the player can't add to
        Table table = new Table();
        table.add(tile2);
        table.add(tile3);
        table.add(tile6);
        AI1.setTable(table);

        //Assert that the player does not play onto the table
        Table output = AI1.play(AI1.getHand());
        assertTrue(output.getMelds().size() == 2);

        //Update the table so that the player can only add to existing melds
        table = new Table();
        table.add(tile7);
        table.add(tile8);
        table.add(tile5);
        AI1.setTable(table);

        //Derive our expected output
        Meld expected = new Meld();
        expected.add(tile7);
        expected.add(tile8);
        expected.add(tile5);
        expected.add(tile4);

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI1.play(AI1.getHand());
        ArrayList<Meld> melds = output.getMelds();

        assertTrue(melds.size() == 2);
        assertTrue(melds.get(1).compare(expected));

        //Create a table onto which the AI can only add a new meld
        table = new Table();
        AI1.setTable(table);

        //Create a hand to play with
        hand = new Meld();
        hand.add(tile5);
        hand.add(tile7);
        hand.add(tile4);
        AI1.setHand(hand);

        //Derive our expected output
        expected = new Meld();
        expected.add(tile7);
        expected.add(tile4);
        expected.add(tile5);

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI1.play(AI1.getHand());
        melds = output.getMelds();

        assertTrue(melds.size() == 2);
        assertTrue(melds.get(1).compare(expected));

        //Create a table onto which the AI can add a new meld, and modify an existing one
        table = new Table();
        table.add(tile2);
        table.add(tile3);
        table.add(tile6);
        AI1.setTable(table);

        //Create a hand to play with
        hand = new Meld();
        hand.add(tile5);
        hand.add(tile7);
        hand.add(tile4);
        hand.add(tile1);
        AI1.setHand(hand);

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
        output = AI1.play(AI1.getHand());

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

    @Test
    public void testPlaySplit(){
        //does play() react appropriately to:
        //    Table it can't split
        //    Table it can split run
        //    Table it can split set
        //    Table it can split multiple times

        //Generate tiles to use
        Tile tile1 = new Tile('O', 10);
        Tile tile2 = new Tile('O', 9);
        Tile tile3 = new Tile('O', 8);
        Tile tile4 = new Tile('B', 7);
        Tile tile5 = new Tile('G', 7);
        Tile tile6 = new Tile('O', 7);
        Tile tile7 = new Tile('O', 7);
        Tile tile8 = new Tile('O', 9);
        Tile tile9 = new Tile('O', 11);
        Tile tile10 = new Tile('R', 7);
        Tile tile11 = new Tile('R', 7);

        //Initialize the AI to use
        ArtificialIntelligence AI1 = new Strategy1();
        AI1.setScore(30);

        //Test with a table that the player can't split
        Table table = new Table();
        table.add(tile1);
        table.add(tile2);
        table.add(tile3);
        AI1.setTable(table);

        Meld hand = new Meld();
        hand.add(tile9);
        AI1.setHand(hand);

        Meld expected = new Meld();
        expected.add(tile1);
        expected.add(tile2);
        expected.add(tile3);
        expected.add(tile9);

        Table output = AI1.play(hand);
        ArrayList<Meld> melds = output.getMelds();

        //Assert that the player plays a normal hand
        assertTrue(melds.size() == 2);
        assertTrue(melds.get(1).compare(expected));

        //Test with a table where the player can split a run
        table = new Table();
        table.add(tile1); //10
        table.add(tile2); //9
        table.add(tile3); //8
        AI1.setTable(table);

        hand = new Meld();
        hand.add(tile9); //11
        hand.add(tile8); //9
        hand.add(tile7); //7
        AI1.setHand(hand);

        Meld expected1 = new Meld();
        expected1.add(tile7); //7
        expected1.add(tile3);//tile2); //8
        expected1.add(tile2);//tile9); //9

        Meld expected2 = new Meld();
        expected2.add(tile8);//tile3); //9
        expected2.add(tile1);//tile8); //10
        expected2.add(tile9);//tile1); //11

        output = AI1.play(hand);
        melds = output.getMelds();

        Boolean expected1Found = false;
        Boolean expected2Found = false;

        //Assert that the player plays a split on the tables run
        assertTrue(melds.size() == 3);
        for(Meld m : melds){
            if(m.size() > 0){
                expected1Found |= m.compare(expected1);
                expected2Found |= m.compare(expected2);
            }
        }

        assertTrue(expected1Found);
        assertTrue(expected2Found);

        //Test with a table where the player can split a set
        table = new Table();
        table.add(tile4); // B7
        table.add(tile5); // G7
        table.add(tile6); // 07
        AI1.setTable(table);

        hand = new Meld();
        hand.add(tile7); //07
        hand.add(tile10); //R7
        hand.add(tile11); //R7
        AI1.setHand(hand);

        expected1 = new Meld();
        expected1.add(tile4); //B7
        expected1.add(tile10); //R7
        expected1.add(tile6); //07

        expected2 = new Meld();
        expected2.add(tile5); //G7
        expected2.add(tile7); //07
        expected2.add(tile11); //R7

        output = AI1.play(hand);
        melds = output.getMelds();

        expected1Found = false;
        expected2Found = false;

        //Assert that the player plays a split on the tables run
        assertTrue(melds.size() == 3);
        for(Meld m : melds){
            if(m.size() > 0){
                expected1Found |= m.compare(expected1);
                expected2Found |= m.compare(expected2);
            }
        }

        //Test with a table where the player can split into a set and a run
        table = new Table();
        table.add(tile6);
        table.add(tile3);
        table.add(tile2);
        AI1.setTable(table);

        hand = new Meld();
        hand.add(tile10);
        hand.add(tile4);
        hand.add(tile1);
        AI1.setHand(hand);

        expected1 = new Meld();
        expected1.add(tile4);
        expected1.add(tile10);
        expected1.add(tile6);

        expected2 = new Meld();
        expected2.add(tile1);
        expected2.add(tile2);
        expected2.add(tile3);

        output = AI1.play(hand);
        melds = output.getMelds();

        expected1Found = false;
        expected2Found = false;

        //Assert that the player plays a split on the tables run
        assertTrue(melds.size() == 3);
        for(Meld m : melds){
            if(m.size() > 0){
                expected1Found |= m.compare(expected1);
                expected2Found |= m.compare(expected2);
            }
        }
        assertTrue(expected1Found);
        assertTrue(expected2Found);
    }

    @Test
    public void testListScore() {
        Strategy1 AI1 = new Strategy1();
        ArrayList<Meld> a = new ArrayList<Meld>();
        Meld m1 = new Meld();
        Meld m2 = new Meld();
        Meld m3 = new Meld();

        m1.add(new Tile('B', 2));
        m1.add(new Tile('B', 3));
        m1.add(new Tile('B', 4));
        m1.add(new Tile('B', 8));

        m2.add(new Tile('B', 12));
        m2.add(new Tile('G', 1));
        m2.add(new Tile('G', 2));
        m2.add(new Tile('G', 3));
        m2.add(new Tile('G', 4));
        m2.add(new Tile('G', 5));

        m3.add(new Tile('G', 8));
        m3.add(new Tile('O', 8));
        m3.add(new Tile('O', 12));
        m3.add(new Tile('R', 8));
        m3.add(new Tile('R', 12));

        a.add(m1);
        a.add(m2);
        a.add(m3);

        assertTrue(AI1.listScore(a) == 92);

    }







    
}