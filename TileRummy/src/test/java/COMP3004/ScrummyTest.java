package COMP3004;

import COMP3004.artificial_intelligence.Strategy3;
import COMP3004.controllers.GameInteractionController;
import COMP3004.controllers.PlayerInteractionController;
import COMP3004.models.Meld;
import COMP3004.models.Scrummy;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.oberver_pattern.PlayerHandObserverInterface;
import COMP3004.oberver_pattern.TableObserverInterface;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Scrummy
 */
public class ScrummyTest
{
    @Test
    public void testScrummyController() {
        Scrummy s = new Scrummy();
        //Test that each player was initialized and that they have hands of size 14
        assertNotNull(s.getPlayers());
        for(int i = 0; i < s.getPlayers().size(); i++){
            assertTrue(s.getPlayers().get(i).getHand().getTiles().size() == 14);
        }
    }

    @Test
    public void testAddObserver() {
        PlayerInteractionController t = new PlayerInteractionController();
        Scrummy s = new Scrummy();
        int prevLen = s.getTableObservers().size();
        s.registerTableObserver(t);
        int newLen = s.getTableObservers().size();
        assertTrue((newLen - prevLen) == 1);

        Strategy3 strat = new Strategy3();
        prevLen = s.getPlayerHandObservers().size();
        s.registerPlayerHandObserver(strat);
        newLen = s.getPlayerHandObservers().size();
        assertTrue((newLen - prevLen) == 1);
    }

    @Test
    public void testRemoveObserver() {
        PlayerInteractionController t = new PlayerInteractionController();
        Scrummy s = new Scrummy();
        s.registerTableObserver(t);

        int prevLen = s.getTableObservers().size();
        s.removeTableObserver(t);
        int newLen = s.getTableObservers().size();
        assertTrue((prevLen-newLen) == 1);
        assertTrue(!(s.getTableObservers().contains(t)));

        Strategy3 strat = new Strategy3();
        s.registerPlayerHandObserver(strat);

        prevLen = s.getPlayerHandObservers().size();
        s.removePlayerHandObserver(strat);
        newLen = s.getPlayerHandObservers().size();
        assertTrue((prevLen-newLen) == 1);
        assertTrue(!(s.getPlayerHandObservers().contains(strat)));

    }

    @Test
    public void testNotifyObservers(){
        PlayerInteractionController t = new PlayerInteractionController();
        Scrummy s = new Scrummy();
        s.addNewPlayer("Human", false);
        s.registerTableObserver(t);

        Strategy3 strat = new Strategy3();
        s.registerPlayerHandObserver(strat);
        strat.setPlayerHandSizes(s.getPlayers());

        s.notifyObservers();

        for(TableObserverInterface observer : s.getTableObservers()){
            assertTrue(observer.getTable().isEquivalent(s.getTable()));
        }

        int index = 0;
        for(PlayerHandObserverInterface observer : s.getPlayerHandObservers()){
            assertEquals(observer.getPlayerHandSize(index), s.getPlayerHandByIndex(index).size());
            index++;
        }
    }

    @Test
    public void testValidateTable(){
        Scrummy scrummy = new Scrummy();
        GameInteractionController gameInteractionController = new GameInteractionController();
        scrummy.registerTableObserver(gameInteractionController);

        Meld bad = new Meld();
        bad.add(new Tile('R', 1));
        bad.add(new Tile('R', 1));

        Table inValidTable = new Table();
        inValidTable.add(bad);

        assertFalse(inValidTable.isValid());

        scrummy.validatePlayerMove(inValidTable);

        assertNotEquals(scrummy.getTable(), inValidTable);
        assertNotEquals(gameInteractionController.getTable(), inValidTable);
        assertTrue(gameInteractionController.getTable().isEquivalent(scrummy.getTable()));

        Meld good = new Meld();
        good.add(new Tile('R', 1));
        good.add(new Tile('B', 1));
        good.add(new Tile('G', 1));


        Table validTable = new Table();
        validTable.add(good);

        scrummy.validatePlayerMove(validTable);

        assertEquals(scrummy.getTable(), validTable);
        assertEquals(gameInteractionController.getTable(), scrummy.getTable());
    }

    @Test
    public void testScrummyGetPlayerByIndex() {
        Scrummy s = new Scrummy();
        for(int i = 0; i < s.getPlayers().size(); i++) {
            Meld playerHand = s.getPlayers().get(i).getHand();
            assertEquals(playerHand, s.getPlayerHandByIndex(i));
        }
    }
}
