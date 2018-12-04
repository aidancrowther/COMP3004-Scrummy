package COMP3004.synchronus_reactor_pattern.memento_pattern.game_state;

import COMP3004.synchronus_reactor_pattern.memento_pattern.game_state.GameState;

public class Memento {
    private GameState state;

    public Memento(GameState state){
        this.state = state;
    }

    public GameState getState(){
        return state;
    }
}
