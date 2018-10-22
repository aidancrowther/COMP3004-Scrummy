package COMP3004;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GraphicalViewController;
import COMP3004.controllers.TerminalViewController;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void testCreateController() {
        Controller controller = new Controller();
        assertNotNull(controller.getScrummy());
        assertNotEquals(controller.getScrummy().getObservers().size(), 0);
        assertTrue(controller.getScrummy().getObservers().contains(controller.getView()));
    }

    @Test
    public void testSetViewType() {
        Controller controller = new Controller();
        String response = "t";
        controller.setViewType(response);
        assertNotNull(controller.getView());
        assertTrue(controller.getView() instanceof TerminalViewController);

        response = "g";
        controller.setViewType(response);
        assertNotNull(controller.getView());
        assertTrue(controller.getView() instanceof GraphicalViewController);

    }
}