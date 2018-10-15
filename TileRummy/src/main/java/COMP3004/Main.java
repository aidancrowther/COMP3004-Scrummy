package COMP3004;

public class Main {
    public static void main (String[] args) {
        TerminalUI view = new TerminalUI(); //TODO: make this an option
        Scrummy scrummy = new Scrummy();
        Controller controller = new Controller(scrummy, view);

        controller.startGame();
    }
}
