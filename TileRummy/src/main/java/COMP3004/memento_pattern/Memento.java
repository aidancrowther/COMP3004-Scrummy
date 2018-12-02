package COMP3004.memento_pattern;

public class Memento {
    private GameState state;

    public Memento(GameState state){
        this.state = state;
    }

    public GameState getState(){
        return state;
    }
}
