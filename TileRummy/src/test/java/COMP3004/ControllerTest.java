package COMP3004;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GraphicalViewController;
import COMP3004.controllers.TerminalViewController;
import COMP3004.models.Meld;
import COMP3004.models.Player;
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
        controller.getScrummy().setCurrentPlayerIndex(1); // CURRENT PLAYER STRAT 1

        ArrayList<Meld> originalTableMelds = controller.getScrummy().getTable().getMelds();
        Table originalTable = new Table();
        for(Meld m : originalTableMelds) {
            originalTable.add(m);
        }

        int intitialPlayerHandLen = controller.getScrummy().getCurrentPlayer().getHand().getTiles().size();
        //controller.run(true);

        //test that there is a winner
        boolean isWinner = false;
        for(Player p : controller.getScrummy().getPlayers()){
            if(p.getHand().size() == 0){
                isWinner = true;
            }
        }
        assertTrue(isWinner);

        /*Table t = controller.getPlayerController(1).getTable(); // TODO: figure out why calling this directly in assert was causing error
        assertEquals(t, controller.getScrummy().getTable());

        // Set views table to table in scrummy
        boolean playerMoved = true;
        if(!controller.getPlayerController(1).getTable().equals(controller.getScrummy().getTable())){
            playerMoved = false;
        }

        if(!playerMoved){
            // If table equals scrummy table, add a card to the players hand
            int currentPlayerHandLen = controller.getScrummy().getCurrentPlayer().getHand().getTiles().size();
            assertTrue((currentPlayerHandLen - intitialPlayerHandLen) == 1);
        } else {
            // else have scrummy evaluate the table and update if valid
            if(controller.getPlayerController(1).getTable().isValid()){
                assertEquals(controller.getPlayerController(1).getTable(), controller.getScrummy().getTable());
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
        }*/
    }


    @Test
    public void testCreateController() {
        Controller controller = new Controller();
        //controller.setInteractionType('t');
        assertNotNull(controller.getScrummy());
        assertNotEquals(controller.getScrummy().getTableObservers().size(), 0);
        //assertTrue(controller.getScrummy().getTableObservers().contains(controller.getInteractionController()));
    }

    @Test
    public void testSetViewType() {
        /*Controller controller = new Controller();
        char response = 't';
        controller.setInteractionType(response);
        assertNotNull(controller.getInteractionController());
        assertTrue(controller.getInteractionController() instanceof TerminalViewController);

        response = 'g';
        controller.setInteractionType(response);
        assertNotNull(controller.getInteractionController());
        assertTrue(controller.getInteractionController() instanceof GraphicalViewController);*/

    }
}