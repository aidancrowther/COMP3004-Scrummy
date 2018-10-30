package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import COMP3004.models.Meld;
import COMP3004.models.Tile;
import org.junit.Test;




public class MeldTest {

    @Test
    //Assert that add() will place a tile in a meld
    public void testAdd() {
        Meld meld = new Meld();
        Tile t = new Tile('G', 10);
        meld.add(t); //add a green 10

        assertTrue(meld.getTiles().contains(t));

    }



    @Test
    //Assert that remove() can remove a tile in a meld
    public void testRemove() {
        Meld meld = new Meld();
        Tile t = new Tile('G', 10);
        meld.add(t); //add a green 10
        assertTrue(meld.getTiles().contains(t));

        Tile r = meld.remove(t);
        assertFalse(meld.getTiles().contains(t));
        assertTrue(r != null);
    }



    @Test
    //Assert that sort() will properly sort a run
    //once sort() works, it will be called by add() and remove()
    public void testSortRun() {
        Meld run = new Meld();

        run.add(new Tile('R', 4));
        run.add(new Tile('R', 3));
        run.add(new Tile('R', 1));
        run.add(new Tile('R', 5));
        run.add(new Tile('R', 2));

        run.sort();
        for (int i=0; i<5; i++) {
            assertTrue(run.getTiles().get(i).getValue() == (i+1));
        }
    }




    @Test
    //Assert that sort() will properly sort a set
    public void testSortSet() {
        Meld set = new Meld();

        set.add(new Tile('R', 1));
        set.add(new Tile('G', 1));
        set.add(new Tile('O', 1));
        set.add(new Tile('B', 1));

        set.sort();
        assertTrue(set.getTiles().get(0).getColour() == 'B');
        assertTrue(set.getTiles().get(1).getColour() == 'G');
        assertTrue(set.getTiles().get(2).getColour() == 'O');
        assertTrue(set.getTiles().get(3).getColour() == 'R');

    }


    @Test
    //Assert that isValid() will correctly determine between a valid and invalid run
    public void testIsValidRun() {
        Meld vRun = new Meld();
        Meld ivRun = new Meld();
        Meld ivRun2 = new Meld();

        vRun.add(new Tile('R', 1));
        vRun.add(new Tile('R', 2));
        vRun.add(new Tile('R', 3));
        vRun.add(new Tile('R', 4));

        ivRun.add(new Tile('R', 1));
        ivRun.add(new Tile('R', 2));
        ivRun.add(new Tile('R', 3));
        ivRun.add(new Tile('R', 5));

        ivRun2.add(new Tile('R', 1));
        ivRun2.add(new Tile('R', 2));
        ivRun2.add(new Tile('O', 3));
        ivRun2.add(new Tile('R', 4));

        assertTrue(vRun.isValid());     //valid run
        assertFalse(ivRun.isValid());   //invalid because of tile value
        assertFalse(ivRun2.isValid());  //invalid becayse of tile colour

    }


    @Test
    //Assert that isValid() will correctly determine between a valid and invalid set
    public void testIsValidSet() {
        Meld vSet = new Meld();
        Meld ivSet = new Meld();
        Meld ivSet2 = new Meld();

        vSet.add(new Tile('R', 3));
        vSet.add(new Tile('G', 3));
        vSet.add(new Tile('O', 3));

        ivSet.add(new Tile('R', 3));
        ivSet.add(new Tile('R', 3));
        ivSet.add(new Tile('G', 3));
        ivSet.add(new Tile('B', 3));

        ivSet2.add(new Tile('R', 3));
        ivSet2.add(new Tile('G', 4));
        ivSet2.add(new Tile('O', 3));
        ivSet2.add(new Tile('B', 3));
        
        assertTrue(vSet.isValid());     //valid set
        assertFalse(ivSet.isValid());   //invalid because of duplicate tile
        assertFalse(ivSet2.isValid());  //invalid becayse of tile value
    }



    @Test
    //Assert that the compare method in Meld will compare two melds and return 
    //whether or not they're equal
    //This is only for testing AI
    public void testCompare() {
        Meld m1 = new Meld();
        Meld m2 = new Meld();
        Meld m3 = new Meld();

        m1.add(new Tile('G', 3));
        m1.add(new Tile('B', 3));
        m1.add(new Tile('O', 3));

        m2.add(new Tile('R', 10));
        m2.add(new Tile('R', 11));
        m2.add(new Tile('R', 12));

        m3.add(new Tile('R', 10));
        m3.add(new Tile('R', 11));
        m3.add(new Tile('R', 12));

        assertTrue(m2.compare(m3));
        assertFalse(m2.compare(m1));

    }

    @Test
    public void testCopy() {
        Meld m1 = new Meld();

        m1.add(new Tile('G', 3));
        m1.add(new Tile('B', 3));
        m1.add(new Tile('O', 3));

        Meld m2 = m1.copy();

        assertTrue(m2.compare(m1));


    }

    @Test
    public void testMeldType() {
        Meld m1 = new Meld();
        Meld m2 = new Meld();
        Meld m3 = new Meld();

        m1.add(new Tile('G', 3));
        m1.add(new Tile('B', 3));
        m1.add(new Tile('O', 3));

        m2.add(new Tile('G', 1));
        m2.add(new Tile('G', 2));
        m2.add(new Tile('G', 3));

        m3.add(new Tile('G', 1));
        m3.add(new Tile('R', 1));
        m3.add(new Tile('G', 6));

        assertTrue(m1.meldType() == 0);
        assertTrue(m2.meldType() == 1);
        assertTrue(m3.meldType() == -1);
    }






}