package COMP3004.synchronus_reactor_pattern.memento_pattern.game_state;

import COMP3004.synchronus_reactor_pattern.memento_pattern.game_state.GameState;
import COMP3004.synchronus_reactor_pattern.memento_pattern.game_state.Memento;

public class Originator {
    private GameState state;

    public void setState(GameState state){
        this.state = state;
    }

    public GameState getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
