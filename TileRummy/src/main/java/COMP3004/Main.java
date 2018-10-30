package COMP3004;

import COMP3004.controllers.Controller;

public class Main {
    public static void main (String[] args) {
        Controller controller = new Controller();
        char viewType = 't';//"g";
        controller.setInteractionType(viewType); // TODO: make interactive
        if(viewType == 'g'){
            controller.launchGraphicalView(args);
        }
        controller.run(true);
    }
}
