package COMP3004;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Scrummy
 */
public class ScrummyTest
{

    @Test
    public void testAddObserver()
    {
        TerminalUI t = new TerminalUI();
        Scrummy s = new Scrummy();
        int prevLen = s.getObservers().size();
        s.registerObserver(t);
        int newLen = s.getObservers().size();
        assertTrue((newLen - prevLen) == 1);
    }
}
