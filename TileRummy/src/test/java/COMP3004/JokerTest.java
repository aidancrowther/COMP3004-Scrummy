package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    }


    //assert that a joker can be used in a meld (isValid() passes)

    //assert that a joker is counted as 30 points in meld.getScore()

    //assert that Table.prettyString() shows the jokers properly

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