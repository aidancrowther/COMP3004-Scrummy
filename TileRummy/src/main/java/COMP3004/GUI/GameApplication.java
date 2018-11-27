package COMP3004.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameApplication extends Application {
    @Override
    public void start(Stage appStage) {
        StackPane root = new StackPane();
        appStage.setScene(new Scene(root, 1000, 1000));
        appStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}