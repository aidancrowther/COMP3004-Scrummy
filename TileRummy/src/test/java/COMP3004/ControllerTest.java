package COMP3004;

import COMP3004.controllers.Controller;
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
        Controller controller = new Controller(true);
        controller.getScrummy().setCurrentPlayerIndex(1); // CURRENT PLAYER STRAT 1

        ArrayList<Meld> originalTableMelds = controller.getScrummy().getTable().getMelds();
        Table originalTable = new Table();
        for(Meld m : originalTableMelds) {
            originalTable.add(m);
        }

        int intitialPlayerHandLen = controller.getScrummy().getCurrentPlayer().getHand().getTiles().size();
        controller.run(true);

        //test that there is a winner
        boolean isWinner = false;
        for(Player p : controller.getScrummy().getPlayers()){
            if(p.getHand().size() == 0){
                isWinner = true;
            }
        }
        //assertTrue(isWinner);
    }


    @Test
    public void testCreateController() {
        Controller controller = new Controller();
        assertNotNull(controller.getScrummy());
        assertNotEquals(controller.getScrummy().getTableObservers().size(), 0);
    }

}