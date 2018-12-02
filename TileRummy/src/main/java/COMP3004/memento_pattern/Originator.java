package COMP3004.memento_pattern;

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
