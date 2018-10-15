package COMP3004;

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
        assertTrue(controller.getView() instanceof TerminalUI);

        response = "g";
        controller.setViewType(response);
        assertNotNull(controller.getView());
        assertTrue(controller.getView() instanceof GraphicalUI);

    }
}