package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import COMP3004.artificial_intelligence.ArtificialIntelligence;
import COMP3004.artificial_intelligence.Strategy1;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import org.junit.Test;



//Test all AI strategies 1 - 4
public class StrategyTest{

    public static Meld getKeyFromValue(HashMap<Meld, Integer> hm, int value) {
        for (Meld o : hm.keySet()) {
          if (hm.get(o).equals(value)) {
            return o;
          }
        }
        return null;
    }

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

        assertTrue(AI1.getHand() == null);
        AI1.setHand(meld1);
        assertTrue(AI1.getHand().equals(meld1));
        meld1 = null;
        assertFalse(AI1.getHand() == null);
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
    public void testSearchHand() {
        //Tests searchHand with a series of different hands
        //SearchHand should return a HashMap of the most possible melds
        Strategy1 AI1 = new Strategy1();
        Meld m1 = new Meld();
        //testing a hand with 2 sets and 2 runs (of different sizes)
        m1.add(new Tile('B', 2));
        m1.add(new Tile('B', 3));
        m1.add(new Tile('B', 4));
        m1.add(new Tile('B', 8));
        m1.add(new Tile('B', 12));
        m1.add(new Tile('G', 1));
        m1.add(new Tile('G', 2));
        m1.add(new Tile('G', 3));
        m1.add(new Tile('G', 4));
        m1.add(new Tile('G', 5));
        m1.add(new Tile('G', 8));
        m1.add(new Tile('O', 8));
        m1.add(new Tile('O', 12));
        m1.add(new Tile('R', 8));
        m1.add(new Tile('R', 12));

        AI1.setHand(m1);
        HashMap<Meld, Integer> h = AI1.searchHand();
        assertTrue(h.size() == 11);
        assertTrue(getKeyFromValue(h, 0).getTiles().size() == 3);
        assertTrue(getKeyFromValue(h, 1).getTiles().size() == 3);
        assertTrue(getKeyFromValue(h, 2).getTiles().size() == 4);
        assertTrue(getKeyFromValue(h, 3).getTiles().size() == 5);
        assertTrue(getKeyFromValue(h, 4).getTiles().size() == 3);
        assertTrue(getKeyFromValue(h, 5).getTiles().size() == 4);
        assertTrue(getKeyFromValue(h, 6).getTiles().size() == 3);
        assertTrue(getKeyFromValue(h, 7).getTiles().size() == 3);
        assertTrue(getKeyFromValue(h, 8).getTiles().size() == 4);
        assertTrue(getKeyFromValue(h, 9).getTiles().size() == 3);
        assertTrue(getKeyFromValue(h, 10).getTiles().size() == 3);


    }

    @Test
    public void testSearchTable() {
        Strategy1 AI1 = new Strategy1();
        Table t = new Table();
        Meld m = new Meld();

        t.add(new Tile('R', 3));
        t.add(new Tile('R', 4));
        t.add(new Tile('R', 5));
        t.add(new Tile('B', 8));
        t.add(new Tile('O', 8));
        t.add(new Tile('R', 8));

        m.add(new Tile('R', 2));    //can be added to the beginning of a run
        m.add(new Tile('R', 6));    //can be added to the end of a run
        m.add(new Tile('R', 7));    //can be added to the end of a run
        m.add(new Tile('G', 8));    //can be added to a set
        m.add(new Tile('O', 12));   //can't be added to anything

        AI1.setHand(m);

        HashMap<Meld, Integer> h = AI1.searchTable(t);

        assertTrue(h.size() == 3);
        assertTrue(getKeyFromValue(h, 0).getTiles().size() == 0); //keep value 0 empty
        assertTrue(getKeyFromValue(h, 1).getTiles().size() == 6);
        assertTrue(getKeyFromValue(h, 2).getTiles().size() == 4);

    }


    @Test
    public void forMySanity(){
        ArtificialIntelligence AI1 = new Strategy1();
        Meld hand = new Meld();

        Tile tile1 = new Tile('O', 10);
        Tile tile2 = new Tile('O', 9);
        Tile tile3 = new Tile('O', 8);
        Tile tile4 = new Tile('B', 10);
        Tile tile5 = new Tile('G', 8);
        Tile tile6 = new Tile('O', 7);
        Tile tile7 = new Tile('O', 10);

        hand.add(tile1);
        hand.add(tile2);
        hand.add(tile3);
        hand.add(tile4);
        hand.add(tile5);
        hand.add(tile6);
        hand.add(tile7);

        AI1.setHand(hand);
        AI1.setTable(new Table());

        AI1.play();
        System.out.println(AI1.output);
    }

/*
    @Test
    public void testBreak30(){

        //Generate tiles to use
        Tile tile1 = new Tile('O', 10);
        Tile tile2 = new Tile('O', 9);
        Tile tile3 = new Tile('O', 8);
        Tile tile4 = new Tile('B', 10);
        Tile tile5 = new Tile('G', 8);
        Tile tile6 = new Tile('O', 7);

        //Initialize a hand that can't generate a play of 30
        Meld hand = new Meld();
        hand.add(tile1);
        hand.add(tile2);
        hand.add(tile3);
        hand.add(tile4);
        hand.add(tile5);

        //Initialize AI with the specified hand
        Strategy1 AI1 = new Strategy1();
        AI1.setHand(hand);

        //Asser that the AI doesn't play if it can't break 30 points
        Table table = AI1.play();
        assertTrue(table == null);

        //Generate the expected table
        Table outTable = new Table();
        outTable.add(tile1);
        outTable.add(tile2);
        outTable.add(tile3);
        outTable.add(tile6);

        //Add a card to allow the player to break 30
        hand.add(tile6);
        AI1.setHand(hand);

        //Assert that the player plays their meld and returns the correct table
        table = AI1.play();
        assertTrue(table == outTable);

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
        Table output = AI1.play();
        assertTrue(output == table);

        //Update the table so that the player can only add to existing melds
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
        output = AI1.play();
        ArrayList<Meld> melds = output.getMelds();
        assertTrue(melds.size() == 1);
        assertTrue(melds.get(0) == expected);

        //Create a table onto which the AI can only add a new meld
        table = new Table();
        AI1.setTable(table);

        //Create a hand to play with
        hand = new Meld();
        hand.add(tile5);
        hand.add(tile7);
        hand.add(tile4);

        //Derive our expected output
        expected = new Meld();
        expected.add(tile7);
        expected.add(tile4);
        expected.add(tile5);

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI1.play();
        melds = output.getMelds();
        assertTrue(melds.size() == 1);
        assertTrue(melds.get(0) == expected);

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

        //Derive our expected outputs
        Meld expected1 = new Meld();
        expected.add(tile7);
        expected.add(tile8);
        expected.add(tile4);

        Meld expected2 = new Meld();
        expected2.add(tile1);
        expected2.add(tile2);
        expected2.add(tile3);
        expected2.add(tile6);


        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI1.play();
        melds = output.getMelds();
        assertTrue(melds.size() == 2);
        
        Boolean expected1Found = false;
        Boolean expected2Found = false;

        for(Meld meld : melds){
            expected1Found |= expected1 == meld;
            expected2Found |= expected2 == meld;
        }

        assertTrue(expected1Found);
        assertTrue(expected2Found);
    }
    */
}