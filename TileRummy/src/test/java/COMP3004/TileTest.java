package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit test for Tile
 */
public class TileTest{

    @Test
    //Assert that a new card can be generated correctly
    public void CheckTileInit(){

        String suit = "H";
        String value = "10";
        Tile card = new Tile(suit, value);

        assertTrue(card.getSuit().equals(suit));
        assertTrue(card.getValue().equals(value));
    }

    @Test
    //Verify that cards get printed correctly
    public void CheckTilePrint(){

        String suit = "H";
        String value = "A";
        Tile card = new Tile(suit, value);

        assertTrue(card.toString().split("")[0].equals(suit));
        assertTrue(card.toString().split("")[1].equals(value));
    }
}
