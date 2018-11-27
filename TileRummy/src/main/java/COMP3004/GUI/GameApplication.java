package COMP3004.GUI;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GameInteractionController;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameApplication {
    //hands given
    //table given
    //Selected tile - observer?
    protected Controller controller;
    StackPane root = new StackPane(); //add layers to our view
    protected Pane firstLayer = new Pane();
    protected int currentPlayerIndex;
    protected ArrayList<Meld> playerHands = new ArrayList<Meld>();
    protected Table table;


    public GameApplication(Controller controller){
        this.root.setStyle("-fx-background-color: #333333");
        this.controller = controller;
        for(GameInteractionController iControl : controller.getPlayerControllers()){
            iControl.setGUI(this);
            this.addPlayer(iControl.getPlayer().getHand());
        }
    }

    public void setSelectedTile(Tile t, int playerIndex, boolean isFromPlayerHand){

    }

    public void draw(){
        this.firstLayer.getChildren().clear();
        int i = 0;
        for(GameInteractionController iControl : this.controller.getPlayerControllers()){
            this.drawHand(iControl);
            i++;
        }
        this.drawTable(this.controller.getScrummy().getTable());
    }

    public void drawTable(Table table){
        for(Meld m : table.getMelds()){
            System.out.println("here");
            for(Tile t : m.getTiles()) {
                System.out.println("here 2");
                Rectangle rectangle = new Rectangle( 100,100,30,50);
                rectangle.setFill(Color.rgb(252, 248, 224,1.0));//")); //rgb()
                rectangle.setOnMousePressed(e -> System.out.println(t));
                this.firstLayer.getChildren().add(rectangle);
            }
        }
    }

    public void drawHand(GameInteractionController playerControl) {
        for(Tile t : playerControl.getPlayer().getHand().getTiles()) {
            Rectangle rectangle = new Rectangle( 100,100,30,50);
            rectangle.setFill(Color.rgb(252, 248, 224,1.0));
            rectangle.setOnMousePressed(e -> System.out.println(playerControl.getPlayer()));
            this.firstLayer.getChildren().add(rectangle);
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