package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import COMP3004.models.Tile;
import COMP3004.models.Joker;
import COMP3004.models.Meld;
import org.junit.Test;


public class JokerTest {


    //assert that a joker can be created and assigned different values, acting as a true wild card
    @Test
    public void testJokerChanging() {
        Joker j = new Joker();

        assertTrue(j.getColour() == 'J');
        assertTrue(j.getValue() == 0);

        j.setColour('B');
        j.setValue(12);

        assertTrue(j.getColour() == 'B');
        assertTrue(j.getValue() == 12);
    }


    //assert that isJoker() can identify which cards are jokers and which are not
    @Test
    public void testIsJoker() {
        Joker j = new Joker();
        Tile t = new Tile('B', 12);


        assertTrue(j.isJoker());
        assertFalse(t.isJoker());
    }


    //assert that a joker can be used in a meld (isValid() passes)

    public void testValidity() {
        Joker j = new Joker();
        Tile t1 = new Tile('B', 9);
        Tile t2 = new Tile('B', 10);
        Tile t3 = new Tile('B', 12);
        Tile t4 = new Tile('B', 1);
        Tile t5 = new Tile('G', 1);
        Tile t6 = new Tile('O', 1);

        Meld m1 = new Meld();
        Meld m2 = new Meld();
        Meld m3 = new Meld();

        m1.add(t1);
        m1.add(t2);
        m1.add(j);
        assertTrue(m1.isValid());

        m2.add(t2);
        m2.add(t3);
        m2.add(j);
        assertTrue(m2.isValid());

        m3.add(t4);
        m3.add(t5);
        m3.add(j);
        assertTrue(m3.isValid());

        m3.add(t6);
        assertTrue(m3.isValid());
    }

    //assert that a joker is counted as 30 points in meld.getScore()
    @Test
    public void testGetScore() {
        Meld m1 = new Meld();
        m1.add(new Tile('O', 1));
        m1.add(new Tile('B', 1));
        m1.add(new Tile('R', 1));

        assertTrue(m1.getScore() == 3);

        m1.add(new Joker());
        assertTrue(m1.isValid());
        assertTrue(m1.getScore() == 33);

    }

    //assert that a joker will change its colour to suit the meld it is being added to

    //asset some function that can tell if a meld contains a joker

    //discuss with everyone about the organization of a printed meld when the joker is included; 
    //should it just read as a big "J" or something else?

   /*
    //LATER...

        -> assert that jokers can be added to melds made from searchHand()
        -> assert that jokers can be added to melds made from searchTable()
        
        -> TBA: whether or not the AI know how to reuse jokers already on the table

        -> assert that jokers will not just be appended to the fronts or ends of melds and will 
            be used with some "cleverness"
        -> assert that if the joker is the only card left in the hand, the ai plays it
    */

}