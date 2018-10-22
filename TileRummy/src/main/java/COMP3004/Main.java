package COMP3004;

import COMP3004.controllers.Controller;

public class Main {
    public static void main (String[] args) {
        Controller controller = new Controller();
        String viewType = "t";//"g";
        controller.setViewType(viewType); // TODO: make interactive
        controller.run();
    }
}
