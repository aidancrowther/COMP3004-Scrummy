package COMP3004;

import COMP3004.GUI.GraphicalView;
import COMP3004.controllers.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    protected static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        GraphicalView gameView = new GraphicalView(controller);
        controller.setGraphicalView(gameView);
        //gameView.getRoot().getChildren().addAll(gameView.getFirstLayer());
        gameView.draw();
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(new Scene(gameView.getRoot(), 1000, 750));
        this.primaryStage.show();
    }


    public static void main (String[] args) {
        launch(args);
        /*Controller controller = new Controller();
        GraphicalView gameView = new GraphicalView(controller);

        gameView.launch(GraphicalView.class, args);
        /*if (args.length == 0)
            controller = new Controller();
        else{
            args = parseArgs(args);
            controller = new Controller(args);
        }*/

        //controller.run(false);
    }

    private static String[] parseArgs(String[] args){
        if (args[0].charAt(0) == '-'){
            if (args[0].substring(1).equals("f")){//file
                args[0] = "f";
                if (args.length != 2 && args.length != 4)
                    blow("FileInput requires specifically one parameter with input formatted as: -f <fileName>\n  it is acceptable to also have a -d flag with exactly one parameter formatted as: -f <filename> -d <testDirectory>");
                if (args.length == 4)
                    if (!args[2].equals("-d"))
                        blow("third parameter for execution unexpected. '-d' is the only acceptable third parameter.");
            }
            else if (args[0].substring(1).equals("g")){//graphical
                args[0] = "g";
                if (args.length > 1)
                    blow("Too many params for graphical user interface");
            }
            else if (args[0].substring(1).equals("t")){//graphical
                args[0] = "t";
                if (args.length > 1)
                    blow("Too many params for terminal execution");
            }
        }
        return args;
    }
    private static void blow(String errMsg) {
        System.out.println(errMsg);
        System.exit(1);
    }
}
