package COMP3004;

import COMP3004.controllers.TerminalViewController;
import COMP3004.models.Scrummy;
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
}
