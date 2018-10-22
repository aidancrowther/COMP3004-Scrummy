package COMP3004;

import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

        assertTrue(AI1.hand == null);
        AI1.setHand(meld1);
        assertTrue(AI1.hand.equals(meld1));
        meld1 = null;
        assertFalse(AI1.hand == null);
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


    /*
    @Test
    public void testSearchHand() {}

    @Test
    public void testSearchTable() {}
    */

    @Test
    public void testBreak30(){

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

    /*
    @Test
    public void testPlayEmptyTable() {}

    @Test
    public void testDifferentTableStates() {
        does play() react appropriately to:
            Table it can't play
            Table it can play melds and add to melds
            Table it can only play melds
            Table it can only add to preexisting melds
    }

    */






}