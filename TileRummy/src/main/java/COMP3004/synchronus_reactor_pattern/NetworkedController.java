package COMP3004.synchronus_reactor_pattern;

import COMP3004.controllers.Controller;
import COMP3004.synchronus_reactor_pattern.memento_pattern.game_state.GameState;

public class NetworkedController {
    Controller localGameController;
    GameState networkedGameState; //serializable game state

    public NetworkedController(Controller localGameController){
        this.localGameController = localGameController;
        this.networkedGameState = new GameState(
                this.localGameController.getScrummy().getTable(),
                this.localGameController.getPlayerControllers(),
                this.localGameController.getCurrentPlayerIndex());
        //user has setup game
        //then this will send the state to the reactor server to
        //allow other humans to join
    }

    public void sendGameState(){ //CALLED AT THE END OF controller.finishTurn()
        // Send  networkedGameState data to network
    }

    public void loadGameState(GameState networkedGameState){ //CALLBACK TO EVENT FORM REACTOR PATTERN
        this.networkedGameState = networkedGameState;
        this.localGameController.getScrummy().setTable(this.networkedGameState.getTable());
        //Call in callback for state event from reactor pattern
        //this.localGameController.getScrummy() table = networkedGameState.getTable()
        //update player hands in playerControllers

        this.localGameController.setCurrentPlayerIndex(this.networkedGameState.getCurrentPlayerIndex());
        this.localGameController.getGraphicalView().draw();
    }

}
