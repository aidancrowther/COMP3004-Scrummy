package COMP3004.views;
import COMP3004.controllers.GameInteractionController;
import COMP3004.controllers.GraphicalViewController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GraphicalView extends Application {
    private static GraphicalViewController controller;

    private Button btn;
    private StackPane root;


    public void start(Stage primaryStage) {
        primaryStage = new Stage();
        primaryStage.setTitle("Scrummy");

        btn = new Button();
        btn.setText("Print table");

        this.setActionListeners();

        root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public void setActionListeners(){
        btn.setOnAction((event) -> {
            this.printTable();
        });
    }

    public void initInterface(String[] args) {
        this.launch(args);
    }

    public void printTable(){
        System.out.println("Table:");
        System.out.println(getController().getTable().toString());
    }

    public static void setController(GraphicalViewController controller){
        GraphicalView.controller = controller;
    }
    public static GraphicalViewController getController(){ return GraphicalView.controller; }
}
