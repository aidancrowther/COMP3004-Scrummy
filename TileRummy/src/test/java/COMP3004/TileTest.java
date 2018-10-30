package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import COMP3004.models.Tile;
import org.junit.Test;

/**
 * Unit test for Tile
 */
public class TileTest{

    @Test
    //Assert that a new card can be generated correctly
    public void CheckTileInit(){

        char colour = 'R';
        int value = 10;
        Tile card = new Tile(colour, value);

        assertTrue(card.getColour() == colour);
        assertTrue(card.getValue() == value);
    }

    @Test
    //Verify that cards get printed correctly
    public void CheckTilePrint(){

        char colour = 'G';
        int value = 8;
        Tile card = new Tile(colour, value);

        assertTrue(card.toString().split("")[0].equals(""+colour));
        assertTrue(card.toString().split("")[1].equals(""+value));
    }

    @Test
    public void checkTileEquals(){
        char colour = 'O';
        int value = 13;
        Tile card1 = new Tile(colour, value);
        
        value = 10;
        Tile card2 = new Tile(colour, value);

        assertTrue(card1.equals(card1));
        assertFalse(card1.equals(card2));
    }
}
