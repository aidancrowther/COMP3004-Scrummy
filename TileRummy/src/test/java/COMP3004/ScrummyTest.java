package COMP3004;

import COMP3004.controllers.TerminalViewController;
import COMP3004.models.Scrummy;
import COMP3004.oberver.pattern.TableObserver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Scrummy
 */
public class ScrummyTest
{

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
