package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;




public class Strategy1Test{

    @Test
    //Assert that add() will place a tile in a meld
    public void testSetTable() {
        Table toAdd = new Table();
        Strategy1 AI1 = new Strategy1();

        assertTrue(AI1.table == null);
        AI1.update(toAdd);
        assertTrue(AI1.table == toAdd);
    }

}