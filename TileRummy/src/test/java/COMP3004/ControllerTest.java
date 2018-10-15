package COMP3004;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


//Test all AI strategies
public class ControllerTest {

    @Test
    //Assert that update will change the table
    public void testCreateController() {
        Controller controller = new Controller();
        assertNotNull(controller.getScrummy());
        assertNotNull(controller.getView());
        assertNotEquals(controller.getScrummy().getObservers().size(), 0);
        assertTrue(controller.getScrummy().getObservers().contains(controller.getView()));
    }

    /*
    @Test
    public void testPlayEmptyTable() {}

    @Test
    public void testDifferentTableStates() {
        does play() react appropriately to:
            Table it can't play
            Table it can play melds and add to melds
            Table it can only play melds
            Table it can only add to preexisting melds
    }

    */

}