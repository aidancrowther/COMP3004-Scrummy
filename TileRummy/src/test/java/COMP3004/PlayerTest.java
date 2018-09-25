package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Unit test for Player
 */
public class PlayerTest 
{

    @Test
    //Assert that a new players hand shows both their initial cards
    public void CheckPlayerToString(){
        Boolean isDealer = false;
        Player player = new Player(isDealer);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("H", "A"));

        assertTrue(player.toString().split(" ").length == 2);
    }

    @Test
    //Assert that a new dealer hand shows only one of their initial cards
    public void CheckDealerToString(){
        Boolean isDealer = true;
        Player dealer = new Player(isDealer);

        dealer.giveTile(new Tile("H", "K"));
        dealer.giveTile(new Tile("H", "A"));

        assertTrue(dealer.toString().split(" ").length == 1);
    }

    @Test
    //Assert that dealer hand shows fully at end of game
    public void CheckDealerEndHand(){
        Boolean isDealer = true;
        Boolean gameOver = true;
        Player dealer = new Player(isDealer);

        dealer.giveTile(new Tile("H", "K"));
        dealer.giveTile(new Tile("H", "A"));

        assertTrue(dealer.toString(gameOver).split(" ").length == 2);
    }

    @Test
    //Test that players hands increment size when adding cards
    public void CheckPlayerDealing(){

        Player player = new Player(false);
        assertTrue(player.getHandSize() == 0);

        player.giveTile(new Tile("H", "K"));
        assertTrue(player.getHandSize() == 1);
    }

    @Test
    //Test that player hand value increases correctly when adding cards
    public void CheckPlayerHandValue(){

        Player player = new Player(false);

        assertTrue(player.getHandValue() == 0);

        player.giveTile(new Tile("S", "A"));
        assertTrue(player.getHandValue() == 11);

        player.giveTile(new Tile("H", "A"));
        assertTrue(player.getHandValue() == 12);

        player.giveTile(new Tile("C", "A"));
        assertTrue(player.getHandValue() == 13);

        player.giveTile(new Tile("H", "K"));
        assertTrue(player.getHandValue() == 13);

        player.giveTile(new Tile("H", "Q"));
        assertTrue(player.getHandValue() == 23);

    }

    @Test
    //Verify that the user can identify a blackjack
    public void CheckBJ(){
        Player player = new Player(false);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("H", "Q"));

        assertFalse(player.hasBJ());

        player = new Player(false);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("H", "A"));

        assertTrue(player.hasBJ());

    }

    @Test
    //Verify that the player can identify a soft 17
    public void CheckSoft(){

        Player player = new Player(false);

        player.giveTile(new Tile("H", "A"));
        player.giveTile(new Tile("H", "5"));

        assertFalse(player.getSoft());

        player.giveTile(new Tile("C", "A"));

        assertTrue(player.getSoft());
    }

    @Test
    //Verify that a dealer is reported as one and a player is not
    public void CheckIsDealer(){

        Player player = new Player(false);
        assertFalse(player.isDealer());

        Player dealer = new Player(true);
        assertTrue(dealer.isDealer());
    }

    @Test
    //Verify that a user correctly reports the ability to split
    public void checkCanSplit(){

        Player player = new Player(false);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("H", "Q"));

        assertFalse(player.canSplit());

        player = new Player(false);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("C", "K"));

        assertTrue(player.canSplit());

        player = new Player(false);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("C", "K"));
        player.giveTile(new Tile("H", "A"));

        assertFalse(player.canSplit());
    }

    @Test
    //Test creating a new player who has already split
    public void CheckHasSplit(){

        Player player = new Player(false);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("C", "K"));

        assertTrue(player.canSplit());

        player = new Player(false, true);

        player.giveTile(new Tile("H", "K"));
        player.giveTile(new Tile("C", "K"));

        assertFalse(player.canSplit());
    }

    @Test
    //Test taking a card from a players hand
    public void CheckTakingTile(){

        Player player = new Player(false);

        assertTrue(player.getHandSize() == 0);

        player.giveTile(new Tile("H", "K"));
        assertTrue(player.getHandSize() == 1);

        Tile received = player.takeTile();
        assertTrue(player.getHandSize() == 0);
        assertTrue(received.getSuit().equals("H"));
        assertTrue(received.getValue().equals("K"));

        player.giveTile(new Tile("H", "J"));
        player.giveTile(new Tile("H", "Q"));
        assertFalse(player.canSplit());
    }
}
