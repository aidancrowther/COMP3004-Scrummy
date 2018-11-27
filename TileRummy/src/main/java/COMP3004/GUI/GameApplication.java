package COMP3004.GUI;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GameInteractionController;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameApplication {
    //hands given
    //table given
    //Selected tile
    protected Controller controller;
    StackPane root = new StackPane(); //add layers to our view
    protected Pane firstLayer = new Pane();
    protected int currentPlayerIndex;
    protected ArrayList<Meld> playerHands = new ArrayList<Meld>();
    protected Table table;


    public GameApplication(Controller controller){
        this.controller = controller;
        for(GameInteractionController iControl : controller.getPlayerControllers()){
            iControl.setGUI(this);
            this.addPlayer(iControl.getPlayer().getHand());
        }
    }

    public void setSelectedTile(Tile t, int playerIndex, boolean isFromPlayerHand){

    }

    public void draw(){
        System.out.println(this.controller);
        int i = 0;
        for(GameInteractionController iControl : this.controller.getPlayerControllers()){
            this.drawHand(iControl.getPlayer().getHand(), i);
            i++;
        }

        this.drawTable(this.controller.getScrummy().getTable());

        System.out.println(this.root);
        System.out.println(this.firstLayer);
    }

    public void drawTable(Table table){
        for(Meld m : table.getMelds()){
            System.out.println("here");
            for(Tile t : m.getTiles()) {
                System.out.println("here 2");
                Rectangle rectangle = new Rectangle( 100,100,30,80);
                rectangle.setFill(Color.GREEN);
                rectangle.setOnMousePressed(e -> System.out.println(t));
                this.firstLayer.getChildren().add(rectangle);
            }
        }
        //.getChildren().clear();
    }

    public void drawHand(Meld hand, int playerIndex){
        if(playerIndex >= 0 && playerIndex < this.playerHands.size()){
            System.out.println("here 3");
            for(Tile t : hand.getTiles()) {
                System.out.println("here 4");
                Rectangle rectangle = new Rectangle( 100,100,30,80);
                rectangle.setFill(Color.GREEN);
                rectangle.setOnMousePressed(e -> System.out.println(t));
                this.firstLayer.getChildren().add(rectangle);
            }
        }
    }

    public void addPlayer(Meld hand) {
        this.playerHands.add(hand);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public StackPane getRoot() {
        return root;
    }

    public void setRoot(StackPane root) {
        this.root = root;
    }

    public Pane getFirstLayer() {
        return firstLayer;
    }

    public void setFirstLayer(Pane firstLayer) {
        this.firstLayer = firstLayer;
    }

}