package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import COMP3004.models.*;
import COMP3004.artificial_intelligence.*;
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
    @Test
    public void testValidity() {
        Joker j = new Joker();
        Tile t1 = new Tile('B', 9);
        Tile t2 = new Tile('B', 10);
        Tile t3 = new Tile('B', 12);
        Tile t4 = new Tile('B', 1);
        Tile t5 = new Tile('G', 1);
        Tile t6 = new Tile('O', 1);
        Tile t7 = new Tile('R', 1);

        Meld m = new Meld();

        //assert jokers can be added to the end of a run
        m.add(t1);
        m.add(t2);
        m.add(j);
        assertTrue(m.isValid()); 
        m.clear();
        
        //assert jokers can be added to the middle of a run
        m.add(t2);
        m.add(t3);
        m.add(j);
        assertTrue(m.isValid());
        m.clear();

        //assert jokers can be added to a set
        m.add(t4);
        m.add(t5);
        m.add(j);
        assertTrue(m.isValid());

        //assert more can be added to the set with no complications
        m.add(t6);
        assertTrue(m.isValid());
        
        //assert 5 cards, joker included, cannot make a set
        m.add(t7);
        assertFalse(m.isValid());
        m.clear();

        //assert that a joker can be added to an empty meld, then adjust to what's added to it
        m.add(j);
        m.add(t2);
        m.add(t3);
        assertTrue(m.isValid());
        m.clear();

       

    }

    //assert that a joker is counted as 30 points in meld.getScore()
    @Test
    public void testGetScore() {
        Meld m = new Meld();
        m.add(new Tile('O', 1));
        m.add(new Tile('B', 1));
        m.add(new Tile('R', 1));

        assertTrue(m.getScore() == 3);

        m.add(new Joker());
        assertTrue(m.isValid());
        assertTrue(m.getScore() == 33);

    }

    //assert that multiple jokers will not break the methods
    @Test
    public void testMultipleJokers() {
        Joker j1 = new Joker();
        Joker j2 = new Joker();
        Meld m = new Meld();

        //assert a set can have 2 jokers as long as there are <5 cards
        m.add(new Tile('R', 1));
        m.add(new Tile('G', 1));
        m.add(j1);
        m.add(j2);
        assertTrue(m.isValid());
        
        m.add(new Tile('O', 1));
        assertFalse(m.isValid());

        //assert that a run can have 2 jokers as long as there are <14 cards
        m.clear();
        m.add(new Tile('O', 2));
        m.add(new Tile('O', 3));
        m.add(new Tile('O', 4));
        m.add(new Tile('O', 5));
        m.add(new Tile('O', 6));
        m.add(new Tile('O', 7));
        m.add(new Tile('O', 8));
        m.add(new Tile('O', 9));
        m.add(new Tile('O', 10));
        m.add(new Tile('O', 11));
        m.add(new Tile('O', 12));
        m.add(j1);
        m.add(j2);
        assertTrue(m.isValid());

        m.add(new Tile('O', 13));
        assertFalse(m.isValid());

        /*assert that two jokers added at the beginning will still make a valid meld as long as another card
        is added*/
        m.clear();
        m.add(j1);
		m.add(j2);
		m.add(new Tile('R', 1));
        m.add(new Tile('G', 1));
        assertTrue(m.isValid());

        m.clear();
		m.add(j1);
		m.add(j2);
		m.add(new Tile('O', 1));
        assertTrue(m.isValid());

        m.clear();
        m.add(j1);
        m.add(j2);
		m.add(new Tile('O', 2));
        assertTrue(m.isValid());

        m.add(new Tile('O', 4));
        assertTrue(m.isValid());

    }

    //assert that melds have a method for determining whether or not there are jokers
    @Test
    public void testGetJokers() {
        Joker j1 = new Joker();
        Joker j2 = new Joker();
        Meld m = new Meld();
        Tile t1 = new Tile('G', 10);
        Tile t2 = new Tile('R', 10);

        m.add(t1);
        m.add(j1);
        m.add(t2);

        assertTrue(m.getJokers() == 1);

        m.add(j2);

        assertTrue(m.getJokers() == 2);
    }

    @Test
    public void testJokerStaticity() {
        Meld m = new Meld();
        Joker j1 = new Joker();
        Joker j2 = new Joker();
        
        m.add(j1);
        m.add(j2);
        m.add(new Tile('G', 3));
        assertTrue(m.isValid());
		m.clear();
		
		m.add(j1);
		m.add(new Tile('G', 3));
		m.add(j2);
		m.add(new Tile('G', 5));
		assertTrue(m.isValid());
		
		m.add(new Tile('G', 4));
		assertFalse(m.isValid());
		m.clear();
		
		m.add(j1);
		m.add(j2);
		m.add(new Tile('G', 3));
		m.add(new Tile('G', 5));
        assertTrue(m.isValid());
        
        m.clear();
		m.add(j1);
		m.add(new Tile('G', 1));
		m.add(new Tile('G', 2));
		
		assertTrue(m.isValid());
		
		m.add(new Tile('G', 3));
		assertFalse(m.isValid());
    }


    //assert that addJoker() can do its thing
    @Test
    public void testAddJoker() {
        Strategy1 AI1 = new Strategy1();
        Meld hand = new Meld();
        Meld m = new Meld();

        //assert that a joker can be added to a meld
        m.add(new Tile('B', 2));
        m.add(new Tile('B', 3));
        m.add(new Tile('B', 4));
        m.add(new Tile('B', 5));

        hand.add(new Tile('B', 2));
        hand.add(new Tile('B', 3));
        hand.add(new Tile('B', 4));
        hand.add(new Tile('B', 5));
        hand.add(new Joker());
        hand.add(new Tile('O', 10));
        hand.add(new Tile('R', 9));

        AI1.addJoker(m, hand);

        assertTrue(m.size() == 5);
        assertTrue(m.getJokers() == 1);

        //assert that two jokers can be added to a meld
        m.clear();
        m.add(new Tile('R', 8));
        m.add(new Tile('R', 9));
        m.add(new Tile('R', 10));

        hand.add(new Joker());

        assertTrue(hand.getJokers() == 2);

        AI1.addJoker(m, hand);

        assertTrue(m.size() == 5);
        assertTrue(m.getJokers() == 2);

        //assert that jokers won't be added to a full run
        m.clear();
        for (int i=1; i<14; i++) {
            m.add(new Tile('B', i));
        }
        assertTrue(m.size() == 13);

        AI1.addJoker(m, hand);
        assertTrue(m.size() == 13);

        
        //assert that only one joker will be added if there is only one slot left in a set
        m.clear();
        for (int i=2; i<14; i++) {
            m.add(new Tile('B', i));
        }
        AI1.addJoker(m, hand);
        assertTrue(m.size() == 13);

        //assert that jokers won't be added to a full set
        m.clear();
        m.add(new Tile('B', 1));
        m.add(new Tile('R', 1));
        m.add(new Tile('O', 1));
        m.add(new Tile('G', 1));
        AI1.addJoker(m, hand);
        assertTrue(m.size() == 4);
        assertTrue(m.getJokers() == 0);
        
        //assert that only one joker will be added if there is only one slot left in a set
        m.clear();
        m.add(new Tile('B', 1));
        m.add(new Tile('R', 1));
        m.add(new Tile('O', 1));

        AI1.addJoker(m, hand);
        assertTrue(m.size() == 4);
        assertTrue(m.getJokers() == 1);
    }



    //assert that if the joker is the only card left in the hand, the ai plays it
    @Test
    public void testOnlyJokersLeftInHand() {
        Strategy1 AI1 = new Strategy1();

        Meld m1 = new Meld();
        Meld m2 = new Meld();
        Meld hand = new Meld();

        for (int i=2; i<9; i++) {
            m1.add(new Tile('R', i));
        }
        m2.add(new Tile('G', 1));
        m2.add(new Tile('O', 1));
        m2.add(new Tile('B', 1));

        Table t = new Table();
        t.add(m1);
        t.add(m2);

        hand.add(new Joker());
        hand.add(new Joker());

        AI1.setHand(hand);
        AI1.setTable(t);
        AI1.setScore(50);

        Table output = AI1.play();

        assertTrue(AI1.getHand().size() == 0);
    }




}