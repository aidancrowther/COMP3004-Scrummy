package COMP3004;

import java.util.ArrayList;
import java.util.Map;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;






//Test all AI strategies
public class StrategyTest{

    /*public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
          if (hm.get(o).equals(value)) {
            return o;
          }
        }
        return null;
    }*/

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
        m1.add(new Tile('B', 5));
        m1.add(new Tile('B', 6));
        m1.add(new Tile('B', 8));
        m1.add(new Tile('B', 12));
        m1.add(new Tile('G', 1));
        m1.add(new Tile('G', 2));
        m1.add(new Tile('G', 3));
        m1.add(new Tile('G', 4));
        m1.add(new Tile('G', 5));
        m1.add(new Tile('G', 6));
        m1.add(new Tile('G', 8));
        m1.add(new Tile('O', 8));
        m1.add(new Tile('O', 12));
        m1.add(new Tile('R', 8));
        m1.add(new Tile('R', 12));

        AI1.setHand(m1);
        assertTrue(getKeyFromValue(AI1.searchHand(), 0).size() == 5);
        assertTrue(getKeyFromValue(AI1.searchHand(), 1).size() == 6);
        assertTrue(getKeyFromValue(AI1.searchHand(), 2).size() == 4);
        assertTrue(getKeyFromValue(AI1.searchHand(), 3).size() == 3);

        //testing a hand with more than one option; searchHand has to pick
        //whatever spends the most cards
        Meld m2 = new Meld();
        m2.add(new Tile('G', 1));
        m2.add(new Tile('G', 2));
        m2.add(new Tile('G', 3));
        m2.add(new Tile('B', 1));
        m2.add(new Tile('O', 1));
        m2.add(new Tile('R', 1));
        AI1.setHand(m2);
        assertTrue(AI1.searchHand().size() == 1);
        assertTrue(getKeyFromValue(AI1.searchHand(), 0).size() == 4);

    }

    @Test
    public void testSearchTable() {}


    /*
    @Test
    public void testBreak30() {}

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