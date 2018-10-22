package COMP3004;

import COMP3004.controllers.Controller;

public class Main {
    public static void main (String[] args) {
        Controller controller = new Controller();
        String viewType = "g";//"t";
        controller.setInteractionType(viewType); // TODO: make interactive
        if(viewType.equals("g")){
            controller.launchGraphicalView(args);
        }
        controller.run();
    }
}
