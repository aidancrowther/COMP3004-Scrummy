package COMP3004.GUI;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GameInteractionController;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import java.util.ArrayList;

public class GraphicalView {
    protected Tile selectedTile;
    protected int toMeldIndex;
    protected int fromMeldIndex;

    protected BorderPane root = new BorderPane();
    //protected StackPane root = new StackPane(); //add layers to our view

    protected Controller controller;
    protected ArrayList<Meld> playerHands = new ArrayList<Meld>();
    //protected Table table;
    protected int currentPlayerIndex = 0;

    protected Table tableBefore;


    public GraphicalView(Controller controller){
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
        this.root.getChildren().clear();
        int i = 1;
        for(GameInteractionController iControl : this.controller.getPlayerControllers()){
            this.drawHand(iControl, i-1, i % 2 == 0);
            i++;
        }
        this.drawTable(this.controller.getScrummy().getTable());
    }

    public void drawTable(Table table){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: #333333");
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #333333");
        gridPane.setMinSize(850, 550);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(10);

        int i = 0;
        int j = 0;
        for(Meld m : table.getMelds()){
            for(Tile t : m.getTiles()) {
                Rectangle rectangle = new Rectangle( 100,100,30,50);
                rectangle.setFill(Color.rgb(252, 248, 224,1.0));//")); //rgb()
                Text text = new Text(Integer.toString(t.getValue()));
                text.setFont(Font.font ("Verdana", 20));
                if(t.getColour() == 'R'){
                    text.setFill(Color.RED);
                } else if (t.getColour() == 'G') {
                    text.setFill(Color.GREEN);
                } else if (t.getColour() == 'B') {
                    text.setFill(Color.BLUE);
                } else if (t.getColour() == 'O') {
                    text.setFill(Color.DARKGOLDENROD);
                }
                text.setBoundsType(TextBoundsType.VISUAL);
                StackPane tile = new StackPane();
                tile.getChildren().addAll(rectangle, text);
                tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                            //if(mouseEvent.getClickCount() == 2){
                                if(selectedTile == t) {
                                    selectedTile = null;
                                } else {
                                    selectedTile = t;
                                }
                            } else {
                                //TODO: set user selected tile here or to meld
                                //YOU HAVE TO SELECT A TILE FIRST
                                int clickedMeldIndex = table.getMelds().indexOf(m);
                                if(selectedTile != null){
                                    if(fromMeldIndex != clickedMeldIndex){
                                        table.getMelds().get(clickedMeldIndex).add(selectedTile);
                                    }
                                } else {
                                    System.out.println("select a tile first by left click one");
                                }
                            //}
                        }
                    }
                });
                gridPane.add(tile, i, j);
                i++;
            }
            j++;
        }

        scrollPane.setContent(gridPane);
        this.root.setCenter(scrollPane);
        BorderPane.setAlignment(scrollPane, Pos.CENTER);
    }

    public void drawHand(GameInteractionController playerControl, int index, boolean isHorizontal) {
        GridPane handPane = new GridPane();
        handPane.setAlignment(Pos.CENTER);
        handPane.setStyle("-fx-background-color: #333333");
        handPane.setPadding(new Insets(10, 10, 10, 10));
        handPane.setVgap(5);
        handPane.setHgap(5);

        int i = 0;
        int j = 0;
        for(Tile t : playerControl.getPlayer().getHand().getTiles()) {
            Rectangle rectangle = new Rectangle( 400,100,30,50);
            rectangle.setFill(Color.rgb(252, 248, 224,1.0));
            Text text = new Text(Integer.toString(t.getValue()));
            text.setFont(Font.font ("Verdana", 20));
            if(t.getColour() == 'R'){
                text.setFill(Color.rgb(204, 0, 0));
            } else if (t.getColour() == 'G') {
                text.setFill(Color.GREEN);
            } else if (t.getColour() == 'B') {
                text.setFill(Color.BLUE);
            } else if (t.getColour() == 'O') {
                text.setFill(Color.rgb(239, 143, 0));
            }
            text.setBoundsType(TextBoundsType.VISUAL);
            StackPane tile = new StackPane();
            tile.getChildren().addAll(rectangle, text);
            if(index == currentPlayerIndex){ //only the player can select their hand
                tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("Yay");
                        //TODO: set user selected tile here
                        //controller.getScrummy().getTable().add(t);
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                            //if(mouseEvent.getClickCount() == 2){
                                if(selectedTile == t) {
                                    selectedTile = null;
                                } else {
                                    selectedTile = t;
                                }

                                System.out.println(controller.getScrummy().getTable().getMelds().size());
                                if(controller.getScrummy().getTable().getMelds().size() == 1){
                                    controller.getScrummy().getTable().add(selectedTile);
                                    playerControl.getPlayer().getHand().remove(t);
                                    //drawTable(controller.getScrummy().getTable());
                                    draw();
                                }

                            } else {
                                //TODO: set user selected tile here or to meld
                                //YOU HAVE TO SELECT A TILE FIRST
                                if(selectedTile != null){
                                    playerControl.getPlayer().getHand().add(selectedTile);
                                } else {
                                    System.out.println("select a tile first by left clicking one");
                                }
                            }
                        //}
                    }
                });
            }

            if(isHorizontal){
                handPane.add(tile, j, i);
                if(i == 8){
                    i = 0;
                    j++;
                } else {
                    i++;
                }
            } else {
                handPane.add(tile, i, j);
                if(i == 20){
                    i = 0;
                    j++;
                } else {
                    i++;
                }
            }
        }

        if(index == 0){
            this.root.setBottom(handPane);
        } else if (index == 1) {
            this.root.setRight(handPane);
        } else if (index == 2) {
            this.root.setTop(handPane);
        } else if (index == 3) {
            this.root.setLeft(handPane);
        }
        BorderPane.setAlignment(handPane, Pos.CENTER);
        BorderPane.setMargin(handPane, new Insets(12,12,12,12));
    }

    public void setSelectedTile(Rectangle rectangle){

    }

    public void addPlayer(Meld hand) {
        this.playerHands.add(hand);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.tableBefore = controller.getScrummy().getTable().copy();
        this.currentPlayerIndex = currentPlayerIndex;
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    /*public Pane getFirstLayer() {
        return firstLayer;
    }

    public void setFirstLayer(Pane firstLayer) {
        this.firstLayer = firstLayer;
    }*/

}