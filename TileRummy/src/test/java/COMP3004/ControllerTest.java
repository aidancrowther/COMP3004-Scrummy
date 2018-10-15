package COMP3004;

import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void testCreateController() {
        Controller controller = new Controller();
        assertNotNull(controller.getScrummy());
        assertNotNull(controller.getView());
        assertNotEquals(controller.getScrummy().getObservers().size(), 0);
        assertTrue(controller.getScrummy().getObservers().contains(controller.getView()));
    }

}