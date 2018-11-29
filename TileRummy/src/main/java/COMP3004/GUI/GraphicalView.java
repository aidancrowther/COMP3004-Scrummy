package COMP3004.GUI;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GameInteractionController;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import java.util.ArrayList;

public class GraphicalView {
    protected Table tableBefore;
    protected Meld handBefore;
    protected Tile selectedTile;

    protected Meld toMeld;
    protected Meld fromMeld;
    protected int currentPlayerIndex = 0;

    protected GridPane root = new GridPane();
    protected BorderPane tablePane = new BorderPane();
    protected HBox topButtons = new HBox();

    protected Controller controller;
    protected ArrayList<Meld> playerHands = new ArrayList<Meld>();
    //protected Table table;

    public GraphicalView(Controller controller){
        this.root.setStyle("-fx-background-color: #333333");
        this.tablePane.setStyle("-fx-background-color: #333333");
        this.tablePane.setMinSize(1000,600);

        this.topButtons.setStyle("-fx-background-color: #333333");
        Button finishTurnBtn = new Button("Finish Turn");
        finishTurnBtn.setOnMouseClicked(e -> {
            this.finishTurn();
        });
        finishTurnBtn.setPrefSize(100, 20);

        Button newMeldBtn = new Button("Add To New Meld");
        newMeldBtn.setOnMouseClicked(e -> {
            if(this.selectedTile != null){
                controller.getScrummy().getTable().add(selectedTile);
                this.controller.getPlayerController(this.currentPlayerIndex).getPlayer().getHand().remove(selectedTile);
                selectedTile = null;
                draw();
            } else {
                System.out.println("Select a tile first");
            }
        });
        newMeldBtn.setPrefSize(100, 20);

        this.topButtons.getChildren().addAll(finishTurnBtn, newMeldBtn);
        this.root.add(this.topButtons, 0, 0);

        this.root.add(this.tablePane, 0, 1);

        this.controller = controller;
        for(GameInteractionController iControl : controller.getPlayerControllers()){
            iControl.setGUI(this);
            this.addPlayer(iControl.getPlayer().getHand());
        }
    }

    public void setSelectedTile(Tile t, int playerIndex, boolean isFromPlayerHand){

    }

    public void draw(){
        this.tablePane.getChildren().clear();
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
            if(m.getTiles().size()!=0){
                for(Tile t : m.getTiles()) {
                    Rectangle rectangle = new Rectangle( 100,100,30,50);
                    rectangle.setFill(Color.rgb(252, 248, 224,1.0));//")); //rgb()
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
                    tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                                System.out.println("Left click");
                                //if(mouseEvent.getClickCount() == 2){
                                if(selectedTile == t) {
                                    selectedTile = null;
                                } else {
                                    selectedTile = t;
                                    fromMeld = m;
                                }
                                System.out.println(controller.getScrummy().getTable().getMelds().size());
                                if(controller.getScrummy().getTable().getMelds().size() == 1){
                                    int meldIndex = controller.getScrummy().getTable().getMelds().indexOf(m);
                                    controller.getPlayerController(currentPlayerIndex).getPlayer().getHand().add(t);
                                    controller.getScrummy().getTable().getMelds().get(meldIndex).remove(t);
                                    selectedTile = null;
                                    fromMeld = null;
                                }
                            } else {
                                //TODO: set user selected tile here or to meld
                                System.out.println("right click");
                                //YOU HAVE TO SELECT A TILE FIRST
                                if(selectedTile != null){
                                    if (fromMeld != null) {
                                        toMeld = m;
                                    }

                                    if (fromMeld != null && toMeld != null) {
                                        int fromMeldIndex = controller.getScrummy().getTable().getMelds().indexOf(fromMeld);
                                        int toMeldIndex = controller.getScrummy().getTable().getMelds().indexOf(toMeld);
                                        table.getMelds().get(fromMeldIndex).getTiles().remove(selectedTile);
                                        table.getMelds().get(toMeldIndex).getTiles().add(selectedTile);
                                        fromMeld = null;
                                        toMeld = null;
                                        selectedTile = null;
                                    }
                                } else {
                                    System.out.println("select a tile first by left click one");
                                }
                            }
                            draw();
                        }
                    });
                    gridPane.add(tile, i, j);
                    i++;
                    if(m.size() == i){
                      i = 0;
                    }
                }
            }
            j++;
        }

        this.tablePane.setCenter(null);
        scrollPane.setContent(gridPane);
        this.tablePane.setCenter(scrollPane);
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
            if(index == this.currentPlayerIndex){ //only the player can select their hand
                tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //TODO: set user selected tile here
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                            if(selectedTile == t) {
                                selectedTile = null;
                            } else {
                                selectedTile = t;
                            }
                            if(controller.getScrummy().getTable().getMelds().size() == 1){
                                controller.getScrummy().getTable().add(selectedTile);
                                playerControl.getPlayer().getHand().remove(t);
                                selectedTile = null;
                            }
                        } else {
                            //TODO: set user selected tile here or to meld
                            //YOU HAVE TO SELECT A TILE FIRST
                            if(selectedTile != null){
                                playerControl.getPlayer().getHand().add(selectedTile);
                                selectedTile = null;
                            } else {
                                System.out.println("select a tile first by left clicking one");
                            }
                        }
                        draw();
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
            this.tablePane.setBottom(handPane);
        } else if (index == 1) {
            this.tablePane.setRight(handPane);
        } else if (index == 2) {
            this.tablePane.setTop(handPane);
        } else if (index == 3) {
            this.tablePane.setLeft(handPane);
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

    public void setCurrentPlayerIndex(int c) {
        this.currentPlayerIndex = c;
        this.tableBefore = controller.getScrummy().getTable().copy();
        System.out.println("PLayer table b4: " + this.tableBefore.toString());
        this.handBefore = controller.getPlayerController(c).getPlayer().getHand().copy();
        if(this.currentPlayerIndex != 0){ //TODO: set these check if 0s to check if instance of player interaction controller instead
            this.finishTurn();
        }
        this.draw();
    }

    public void finishTurn(){
        this.controller.finishTurn();
        //this.draw();
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    public GridPane getRoot() {
        return root;
    }

    public void setRoot(GridPane root) {
        this.root = root;
    }


    public Table getTableBefore() {
        return tableBefore;
    }

    public void setTableBefore(Table tableBefore) {
        this.tableBefore = tableBefore;
    }

    public Meld getHandBefore() {
        return handBefore;
    }

    public void setHandBefore(Meld handBefore) {
        this.handBefore = handBefore;
    }

    /*public Pane getFirstLayer() {
        return firstLayer;
    }

    public void setFirstLayer(Pane firstLayer) {
        this.firstLayer = firstLayer;
    }*/

}