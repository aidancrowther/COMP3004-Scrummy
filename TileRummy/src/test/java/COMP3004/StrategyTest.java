package COMP3004;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;



//Test all AI strategies
public class StrategyTest{

    @Test
    //Assert that update will change the table
    public void testUpdate() {
        Table toAdd = new Table();
        Strategy1 AI1 = new Strategy1();

        assertTrue(AI1.table == null);
        AI1.update(toAdd);
        assertTrue(AI1.table == toAdd);
    }

}