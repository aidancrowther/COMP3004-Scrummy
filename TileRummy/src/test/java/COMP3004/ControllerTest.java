package COMP3004;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GraphicalViewController;
import COMP3004.controllers.TerminalViewController;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ControllerTest {

    /*
     * While everyone has cards in their hand...
     * Set view's hand to current players hand in scrummy
     * copy players hand, pass in players hand?
     * Set views table to table in scrummy
     * If table equals scrummy table,
     *   add a card to the players hand
     * else
     *   have scrummy evaluate the table and update if valid
     * */
    @Test
    public void testRun() {
        Controller controller = new Controller();
        ArrayList<Meld> originalTableMelds = controller.getScrummy().getTable().getMelds();
        Table originalTable = new Table();
        for(Meld m : originalTableMelds) {
            originalTable.add(m);
        }

        int intitialPlayerHandLen = controller.getScrummy().getCurrentPlayer().getHand().getTiles().size();
        controller.run("Test");
        assertEquals(controller.getInteractionController().getTable(), controller.getScrummy().getTable());

        // Set views table to table in scrummy
        boolean playerMoved = true;
        for(Meld viewMeld: controller.getInteractionController().getTable().getMelds()) {
            for(Meld scrummyMeld: controller.getScrummy().getTable().getMelds()){
                if(!viewMeld.equals(scrummyMeld)){
                    playerMoved = false;
                }
            }
        }

        if(!playerMoved){
            // If table equals scrummy table, add a card to the players hand
            int currentPlayerHandLen = controller.getScrummy().getCurrentPlayer().getHand().getTiles().size();
            assertTrue((currentPlayerHandLen - intitialPlayerHandLen) == 1);
        } else {
            // else have scrummy evaluate the table and update if valid
            if(controller.getInteractionController().getTable().isValid()){
                assertEquals(controller.getInteractionController().getTable(), controller.getScrummy().getTable());
            } else {
                boolean resetToOriginal = true;
                for(Meld viewMeld: originalTable.getMelds()) {
                    for(Meld scrummyMeld: controller.getScrummy().getTable().getMelds()){
                        if(!viewMeld.equals(scrummyMeld)){
                            resetToOriginal = false;
                        }
                    }
                }
                assertTrue(resetToOriginal);
            }
        }
    }


    @Test
    public void testCreateController() {
        Controller controller = new Controller();
        assertNotNull(controller.getScrummy());
        assertNotEquals(controller.getScrummy().getObservers().size(), 0);
        assertTrue(controller.getScrummy().getObservers().contains(controller.getInteractionController()));
    }

    @Test
    public void testSetViewType() {
        Controller controller = new Controller();
        String response = "t";
        controller.setInteractionType(response);
        assertNotNull(controller.getInteractionController());
        assertTrue(controller.getInteractionController() instanceof TerminalViewController);

        response = "g";
        controller.setInteractionType(response);
        assertNotNull(controller.getInteractionController());
        assertTrue(controller.getInteractionController() instanceof GraphicalViewController);

    }
}