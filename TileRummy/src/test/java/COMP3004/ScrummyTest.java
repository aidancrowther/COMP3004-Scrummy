package COMP3004;

import COMP3004.controllers.GameInteractionController;
import COMP3004.controllers.TerminalViewController;
import COMP3004.models.Meld;
import COMP3004.models.Scrummy;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import COMP3004.oberver_pattern.TableObserver;
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
        for(int i = 0; i < s.getPlayers().length; i++){
            assertTrue(s.getPlayers()[i].getHand().getTiles().size() == 14);
        }
    }

    @Test
    public void testAddObserver() {
        TerminalViewController t = new TerminalViewController();
        Scrummy s = new Scrummy();
        int prevLen = s.getObservers().size();
        s.registerObserver(t);
        int newLen = s.getObservers().size();
        assertTrue((newLen - prevLen) == 1);
    }

    @Test
    public void testRemoveObserver() {
        TerminalViewController t = new TerminalViewController();
        Scrummy s = new Scrummy();
        s.registerObserver(t);

        int prevLen = s.getObservers().size();
        s.removeObserver(t);
        int newLen = s.getObservers().size();
        assertTrue((prevLen-newLen) == 1);
        assertTrue(!(s.getObservers().contains(t)));
    }

    @Test
    public void testNotifyObservers(){
        TerminalViewController t = new TerminalViewController();
        Scrummy s = new Scrummy();
        s.registerObserver(t);

        s.notifyObservers();

        for(TableObserver observer : s.getObservers()){
            assertEquals(observer.getTable(), s.getTable());
        }
    }

    @Test
    public void testValidateTable(){
        Scrummy scrummy = new Scrummy();
        GameInteractionController gameInteractionController = new GameInteractionController();
        scrummy.registerObserver(gameInteractionController);

        Meld bad = new Meld();
        bad.add(new Tile('R', 1));
        bad.add(new Tile('R', 1));

        Table inValidTable = new Table();
        inValidTable.add(bad);

        assertFalse(inValidTable.isValid());

        scrummy.validatePlayerMove(inValidTable);

        assertNotEquals(scrummy.getTable(), inValidTable);
        assertNotEquals(gameInteractionController.getTable(), inValidTable);
        assertEquals(gameInteractionController.getTable(), scrummy.getTable());

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
        for(int i = 0; i < s.getPlayers().length; i++) {
            Meld playerHand = s.getPlayers()[i].getHand();
            assertEquals(playerHand, s.getPlayerHandByIndex(i));
        }
    }
}
