package COMP3004;

import COMP3004.controllers.Controller;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ControllerTest {
    @Test
    public void testCreateController() {
        Controller controller = new Controller();
        assertNotNull(controller.getScrummy());
        assertNotEquals(controller.getScrummy().getTableObservers().size(), 0);
    }

}