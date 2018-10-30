package COMP3004;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Iterator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

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
        m.add(new Tile('G', 8));    //can be added to a set
        m.add(new Tile('R', 6));    //can be added to the end of a run
        m.add(new Tile('R', 7));    //can be added to the end of a run
        m.add(new Tile('O', 12));   //can't be added to anything

        AI1.setHand(m);

        HashMap<Meld, Integer> h = AI1.searchTable(t);

        assertTrue(h.size() == 4);

    }

    @Test
    public void testBreak30(){

        //Generate tiles to use
        Tile tile1 = new Tile('O', 10);
        Tile tile2 = new Tile('O', 11);
        Tile tile3 = new Tile('O', 12);
        Tile tile4 = new Tile('B', 10);
        Tile tile5 = new Tile('G', 8);

        //Initialize a hand that can't generate a play of 30
        Meld hand = new Meld();
        hand.add(tile1);
        hand.add(tile2);
        hand.add(tile4);
        hand.add(tile5);

        //Initialize AI with the specified hand and table
        Strategy1 AI1 = new Strategy1();
        AI1.setHand(hand);
        Table t = new Table();
        AI1.setTable(t);

        //Asser that the AI doesn't play if it can't break 30 points
        Table table = AI1.play(AI1.getHand());
        assertTrue(table.getMelds().size() == 1);


        //Generate the expected table
        Meld outTable = new Meld();
        outTable.add(tile1);
        outTable.add(tile2);
        outTable.add(tile3);

        //Add a card to allow the player to break 30
        //hand.add(tile6);
        hand.add(tile3);
        AI1.setHand(hand);

        //Assert that the player plays their meld and returns the correct table
        table = AI1.play(AI1.getHand());
        assertTrue(table.getMelds().size() == 2);
        assertTrue(outTable.compare(table.getMelds().get(1)));
        assertTrue(AI1.hand.getTiles().size() == 2);
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
        assertTrue(melds.get(0).compare(expected));

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
        expected.add(tile7);
        expected.add(tile8);
        expected.add(tile4);

        Meld expected2 = new Meld();
        expected2.add(tile1);
        expected2.add(tile2);
        expected2.add(tile3);
        expected2.add(tile6);

        //Assert that the player plays onto the correct meld, and only onto that meld
        output = AI1.play(AI1.getHand());

        System.out.println(AI1.toPrint);

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

        Table output = AI1.play();
        ArrayList<Meld> melds = output.getMelds();

        //Assert that the player plays a normal hand
        assertTrue(melds.size() == 2);
        assertTrue(melds.get(1).compare(expected));

        //Test with a table where the player can split a run
        table = new Table();
        table.add(tile1);
        table.add(tile2);
        table.add(tile3);
        AI1.setTable(table);

        hand = new Meld();
        hand.add(tile9);
        hand.add(tile8);
        hand.add(tile7);
        AI1.setHand(hand);

        Meld expected1 = new Meld();
        expected.add(tile7);
        expected.add(tile2);
        expected.add(tile9);

        Meld expected2 = new Meld();
        expected.add(tile3);
        expected.add(tile8);
        expected.add(tile1);

        output = AI1.play();
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
        table.add(tile4);
        table.add(tile5);
        table.add(tile6);
        AI1.setTable(table);

        hand = new Meld();
        hand.add(tile7);
        hand.add(tile10);
        hand.add(tile11);
        AI1.setHand(hand);

        expected1 = new Meld();
        expected.add(tile4);
        expected.add(tile10);
        expected.add(tile6);

        expected2 = new Meld();
        expected.add(tile5);
        expected.add(tile7);
        expected.add(tile11);

        output = AI1.play();
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
        expected.add(tile4);
        expected.add(tile10);
        expected.add(tile6);

        expected2 = new Meld();
        expected.add(tile1);
        expected.add(tile2);
        expected.add(tile3);

        output = AI1.play();
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

    /*@Test
    public void testSplittingRuns() {
        Strategy1 AI1 = new Strategy1();
        Table t = new Table();

        Meld m1 = new Meld();
        m1.add(new Tile('R', 2));
        m1.add(new Tile('R', 3));
        m1.add(new Tile('R', 4));

        Meld m2 = new Meld();
        m2.add(new Tile('O', 3));
        m2.add(new Tile('O', 4));
        m2.add(new Tile('O', 5));
        m2.add(new Tile('O', 6));
        m2.add(new Tile('O', 7));

        Meld m3 = new Meld();
        m3.add(new Tile('R', 1));
        m3.add(new Tile('R', 2));
        m3.add(new Tile('R', 3));

        Meld m4 = new Meld();
        m4.add(new Tile('R', 3));
        m4.add(new Tile('R', 4));
        m4.add(new Tile('R', 5));

        Meld m5 = new Meld();
        m5.add(new Tile('O', 2));
        m5.add(new Tile('O', 3));
        m5.add(new Tile('O', 4));
        m5.add(new Tile('O', 5));

        Meld m6 = new Meld();
        m6.add(new Tile('O', 4));
        m6.add(new Tile('O', 5));
        m6.add(new Tile('O', 6));
        m6.add(new Tile('O', 7));

        Meld h = new Meld();
        h.add(new Tile('R', 1));
        h.add(new Tile('R', 3));
        h.add(new Tile('R', 5));
        h.add(new Tile('O', 2));
        h.add(new Tile('O', 4));
        h.add(new Tile('O', 5));

        t.add(m1);
        t.add(m2);
        AI1.setHand(h);

        HashMap<Meld, HashMap<ArrayList<Meld>, Integer>> sTest = AI1.searchSplit(t);

        Iterator<HashMap<ArrayList<Meld>, Integer>> iterator = sTest.values().iterator();
        ArrayList<Meld> test = new ArrayList<Meld>();
		
		while (iterator.hasNext()) {
			for (ArrayList<Meld> al : iterator.next().keySet()) {
				for (int i=0; i<al.size(); i++) {
					test.add(al.get(i));
				}
			}		
        }
        
        assertTrue(test.get(0).compare(m3));
        assertTrue(test.get(1).compare(m4));
        assertTrue(test.get(2).compare(m5));
        assertTrue(test.get(3).compare(m6));
        //more here later
    }

    /*@Test
    public void testSplittingSets() {
        Strategy1 AI1 = new Strategy1();
        Table t = new Table();

        Meld m1 = new Meld();
        m1.add(new Tile('B', 7));
        m1.add(new Tile('G', 7));
        m1.add(new Tile('O', 7));
        m1.add(new Tile('R', 7));

        Meld m2 = new Meld();
        m2.add(new Tile('G', 3));
        m2.add(new Tile('O', 3));
        m2.add(new Tile('R', 3));

        Meld m3 = new Meld();
        m3.add(new Tile('B', 6));
        m3.add(new Tile('B', 7));
        m3.add(new Tile('B', 8));

        Meld m4 = new Meld();
        m4.add(new Tile('G', 7));
        m4.add(new Tile('O', 7));
        m4.add(new Tile('R', 7));

        Meld m5 = new Meld();
        m5.add(new Tile('O', 2));
        m5.add(new Tile('O', 3));
        m5.add(new Tile('O', 4));
        m5.add(new Tile('O', 5));

        Meld m6 = new Meld();
        m6.add(new Tile('B', 3));
        m6.add(new Tile('G', 3));
        m6.add(new Tile('R', 3));

        Meld h = new Meld();
        h.add(new Tile('B', 3));
        h.add(new Tile('B', 7));
        h.add(new Tile('B', 8));
        h.add(new Tile('O', 2));
        h.add(new Tile('O', 4));
        h.add(new Tile('O', 5));

        t.add(m1);
        t.add(m2);
        AI1.setHand(h);

        HashMap<Meld, HashMap<ArrayList<Meld>, Integer>> sTest = AI1.searchSplit(t);

        Iterator<HashMap<ArrayList<Meld>, Integer>> iterator = sTest.values().iterator();
        ArrayList<Meld> test = new ArrayList<Meld>();


		
		while (iterator.hasNext()) {
			for (ArrayList<Meld> al : iterator.next().keySet()) {
				for (int i=0; i<al.size(); i++) {
					test.add(al.get(i));
				}
			}		
        }
        
        assertTrue(test.get(0).compare(m3));
        assertTrue(test.get(1).compare(m4));
        assertTrue(test.get(2).compare(m6));
        assertTrue(test.get(3).compare(m5));
        /*test should contain:
        B6 B7 B8
        G7 O7 R7
        B3 G3 R3
        O2 O3 O4 O5

        
    }

    @Test
    public void testOtherSplits() {
        /*TEST FOR SPLITS:
            Unsplittable melds
                * [ ] Melds that cannot be split at all
                * [ ] Runs that can be split but leave invalid function with the remaining cards
                * [ ] Sets that can be split but leave invalid function with the remaining cards
            Run Cases
                * [X] Splitting runs from the first tile
                * [X] Splitting runs with two of the same card involved 
                * [ ] Splitting runs from the middle
                * [ ] Splitting runs from the end
                * [ ] Splitting runs into a run and a set
                * [ ] Splitting long runs and leaving some
            Set Cases
                * [ ] Splitting sets from just the first tile
                * [ ] Splitting sets from two consecutive tiles
                * [ ] Splitting sets from the middle, leaving nonconsecutive tiles
                * [ ] Splitting sets 
                * [ ] Splitting sets into a run and a set
            Other / Edge Cases:
                * [ ] Checking twice over size>3 sets to make sure no runs are possible
                * [ ] Checking twice over size>3 runs to make sure no sets are possible from edges
    




    }*/
}