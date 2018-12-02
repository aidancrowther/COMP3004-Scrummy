package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import COMP3004.models.Deck;
import COMP3004.models.Tile;
import org.junit.Test;

/**
 * Unit test for Deck
 */
public class DeckTest 
{

    @Test
    //Assert that a new deck will have exactly 52 cards in it.
    public void Check104Tiles()
    {
        int counter = 0;
        Deck testDeck = new Deck();

        for(;!testDeck.isEmpty();testDeck.pop()) counter++;

        assertTrue(counter == 106);

    }

    @Test
    //Assert that two shuffled decks will not be the same
    public void checkDeckShuffle(){
        Deck deckOne = new Deck();
        Deck deckTwo = new Deck();
        
        deckOne.shuffle();
        deckTwo.shuffle();

        Boolean decksAreEqual = true;

        while(!deckOne.isEmpty() && !deckTwo.isEmpty()){
            Tile cardOne = deckOne.pop();
            Tile cardTwo = deckTwo.pop();

            decksAreEqual &= cardOne.toString().equals(cardTwo.toString());
        }

        assertFalse(decksAreEqual);
    }
}
