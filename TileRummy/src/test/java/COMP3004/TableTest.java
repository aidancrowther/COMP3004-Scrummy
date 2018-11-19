package COMP3004;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import org.junit.Test;




public class TableTest {

    @Test
    //test adding complete new melds properly
    public void testAddMeld() {
        Table t = new Table();
        Meld m1 = new Meld();
        Meld m2 = new Meld();

        t.add(m1);
        t.add(m2);

        assertTrue(t.getMelds().get(0).getTiles().size() == 0);
        assertTrue(t.getMelds().get(1) == m1);
        assertTrue(t.getMelds().get(2) == m2);
    }

    @Test
    //test adding new tiles individually, using 0 slot then redirecting once a meld is made
    public void testAddTile() {
        Table t = new Table();

        t.add(new Tile('O', 1));
        t.add(new Tile('O', 2));
        t.add(new Tile('O', 3));
        //a new meld should automatically be created now

        t.add(new Tile('O', 5));
        t.add(new Tile('B', 5));
        t.add(new Tile('G', 5));
        //a new meld should automatically be created now

        assertTrue(t.getMelds().get(0).getTiles().size() == 0);
        assertTrue(t.getMelds().get(1).isValid());
        assertTrue(t.getMelds().get(2).isValid());
    }


    @Test
    //Test Table.isValid()
    public void testIsValid() {
        Table t1 = new Table();
        Table t2 = new Table();
        Table t3 = new Table();

        Meld m1 = new Meld();
        Meld m2 = new Meld();
        Meld m3 = new Meld();

        for (int i=1; i<6; i++) {
            m1.add(new Tile('R', i));
        }
        m2.add(new Tile('R', 12));
        m2.add(new Tile('G', 12));
        m2.add(new Tile('O', 12));

        m3.add(new Tile('R', 12));
        m3.add(new Tile('R', 13));
        m3.add(new Tile('R', 1));

        t1.add(m1);
        t1.add(m2);
        t2.add(m1);
        t2.add(m3);
        t3.add(m2);
        t3.add(new Tile('O', 5));
        

        assertTrue(t1.isValid());
        assertFalse(t2.isValid());
        assertFalse(t3.isValid());
    }


    @Test
    public void testRemoveMeld() {

        Meld m1 = new Meld();
        for (int i=1; i<6; i++) {
            m1.add(new Tile('R', i));
        }
        Meld m2 = new Meld();
        m2.add(new Tile('R', 12));
        m2.add(new Tile('G', 12));
        m2.add(new Tile('O', 12));

        Meld m3 = new Meld();
        for (int i=3; i<8; i++) {
            m3.add(new Tile('O', i));
        }

        Meld m4 = m3.copy();

        Table t = new Table();
        t.add(m1);
        t.add(m2);
        t.add(m3);

        t.remove(0);
        t.remove(1);
        t.remove(m4);

        assertTrue(t.getMelds().size() == 2);
        assertTrue(t.getMelds().get(1).size() == 3);

    }


}