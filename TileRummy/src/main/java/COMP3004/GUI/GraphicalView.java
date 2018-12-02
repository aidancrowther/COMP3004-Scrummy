package COMP3004.GUI;

import COMP3004.controllers.Controller;
import COMP3004.controllers.GameInteractionController;
import COMP3004.controllers.PlayerInteractionController;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GraphicalView {
    protected Table tableBefore;
    protected Meld handBefore;
    protected Tile selectedTile;

    protected Meld toMeld;
    protected Meld fromMeld;
    protected int currentPlayerIndex = 0;


    protected StackPane root = new StackPane();
    protected VBox menuPane = new VBox();
    protected GridPane gamePane = new GridPane();
    protected BorderPane tablePane = new BorderPane();
    protected HBox topButtons = new HBox();

    protected Controller controller;
    protected ArrayList<Meld> playerHands = new ArrayList<Meld>();
    //protected Table table;

    public GraphicalView(Controller controller){
        //SET UP MENU SCREEN
        this.menuPane.setStyle("-fx-background-color: #333333");
        Image img = new Image("/images/bar.png");
        ImageView imv = new ImageView(img);
        this.menuPane.getChildren().add(imv);

        Image img2 = new Image("/images/title.png");
        ImageView imv2 = new ImageView(img2);
        this.menuPane.getChildren().add(imv2);


        VBox buttons = new VBox();
        buttons.setPadding(new Insets(30, 365, 50, 365));
        buttons.setSpacing(30);

        Button playButton = new Button("PLAY NEW GAME");
        playButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        playButton.setMaxSize(270, 100);
        playButton.setOnMouseClicked(e -> {
            //loadGamePane();
            loadGameSettingsPane();
        });
        buttons.getChildren().add(playButton);

        Button loadButton = new Button("LOAD SAVED GAME");
        loadButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        loadButton.setMaxSize(270, 100);
        loadButton.setOnMouseClicked(e -> {
            loadImportSavePane();
        });
        buttons.getChildren().add(loadButton);

        Button rigButton = new Button("NEW RIGGED GAME");
        rigButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        rigButton.setMaxSize(270, 100);
        rigButton.setOnMouseClicked(e -> {
            loadRigPane();
        });
        buttons.getChildren().add(rigButton);

        this.menuPane.getChildren().add(buttons);
        this.root.getChildren().add(menuPane);

        this.controller = controller;
        for(GameInteractionController iControl : controller.getPlayerControllers()){
            iControl.setGUI(this);
            this.addPlayer(iControl.getPlayer().getHand());
        }
    }

    public void loadGameSettingsPane(){
        //- drop downs for players 2-4
        //When new player created show spawned order tile for the new player
        //start game button
        // -- loads game
        VBox setPlayersMenu = new VBox();
        //buttons.setPadding(new Insets(30, 365, 50, 365));
        setPlayersMenu.setSpacing(30);
        setPlayersMenu.setStyle("-fx-background-color: #333333");

        ArrayList<String> playerTypes = new ArrayList<String>();
        playerTypes.add(null);
        playerTypes.add(null);
        playerTypes.add(null);
        playerTypes.add(null);

        ChoiceBox<String> playerOption1 = new ChoiceBox<>(FXCollections.observableArrayList(
                "Human", "AI Strategy 1", "AI Strategy 2", "AI Strategy 3", "AI Strategy 4")
        );
        playerOption1.setOnAction(e -> {
            playerTypes.set(0, playerOption1.getSelectionModel().getSelectedItem().toString());
        });
        playerOption1.getSelectionModel().selectFirst();
        setPlayersMenu.getChildren().add(playerOption1);

        ChoiceBox<String> playerOption2 = new ChoiceBox<>(FXCollections.observableArrayList(
                "Human", "AI Strategy 1", "AI Strategy 2", "AI Strategy 3", "AI Strategy 4")
        );
        playerOption2.setOnAction(e -> {
            playerTypes.set(1, playerOption2.getSelectionModel().getSelectedItem().toString());
        });
        playerOption2.getSelectionModel().selectFirst();
        setPlayersMenu.getChildren().add(playerOption2);

        ChoiceBox<String> playerOption3 = new ChoiceBox<>(FXCollections.observableArrayList(
                "Human", "AI Strategy 1", "AI Strategy 2", "AI Strategy 3", "AI Strategy 4")
        );
        playerOption3.setOnAction(e -> {
            playerTypes.set(2, playerOption3.getSelectionModel().getSelectedItem().toString());
        });
        setPlayersMenu.getChildren().add(playerOption3);

        ChoiceBox<String> playerOption4 = new ChoiceBox<>(FXCollections.observableArrayList(
                "Human", "AI Strategy 1", "AI Strategy 2", "AI Strategy 3", "AI Strategy 4")
        );
        playerOption4.setOnAction(e -> {
            playerTypes.set(3, playerOption4.getSelectionModel().getSelectedItem().toString());
        });
        setPlayersMenu.getChildren().add(playerOption4);

        Button play = new Button("PLAY");
        play.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        play.setOnMouseClicked(e -> {
            for(String type : playerTypes){
                System.out.println(type);
                if(type != null){
                    controller.addPlayer(type);
                }
            }
            //this.setCurrentPlayerIndex(0);
            this.loadPlayerOrderPane();
        });
        setPlayersMenu.getChildren().add(play);

        this.root.getChildren().add(setPlayersMenu);
    }

    public void loadPlayerOrderPane(){
        VBox playerOrder = new VBox();
        playerOrder.setPadding(new Insets(30, 50, 30, 50));
        playerOrder.setSpacing(30);
        playerOrder.setStyle("-fx-background-color: #333333");

        VBox chooseAttemptsPane = new VBox();
        chooseAttemptsPane.setSpacing(30);

        HBox chosenTilesPane = new HBox();
        chosenTilesPane.setSpacing(50);

        HBox chosenTilesLabelPane = new HBox();
        chosenTilesLabelPane.setSpacing(50);

        ArrayList<Tile> selectedTiles = new ArrayList<Tile>();
        for(GameInteractionController p : this.controller.getPlayerControllers()){
            String label = p.getPlayer().getName();
            if(label.length() < 8){
                label += "   ";
            }
            Text text = new Text(label);
            text.setFont(Font.font ("Verdana", 15));
            text.setFill(Color.WHITE);
            chosenTilesLabelPane.getChildren().add(text);

            Tile t = this.controller.getScrummy().getDeck().pop();
            selectedTiles.add(t);
            chosenTilesPane.getChildren().add(this.generateTilePaneLarge(t));
        }
        chooseAttemptsPane.getChildren().add(chosenTilesLabelPane);
        chooseAttemptsPane.getChildren().add(chosenTilesPane);


        int highestValue = -1;
        int startIndex = 0;
        int index = 0;

        ArrayList<Tile> otherAttempts = new ArrayList<Tile>();
        for(Tile t : selectedTiles){
            if(highestValue == -1){
                highestValue = t.getValue();
            } else if(t.getValue() > highestValue){
                startIndex = index;
                highestValue = t.getValue();
            } else if(t.getValue() == highestValue){
                Text text = new Text(
                        this.controller.getPlayerControllers().get(index).getPlayer().getName()
                                + " has tied! They selected the following tile(s) to try and break the tie:");
                text.setFont(Font.font ("Verdana", 15));
                text.setFill(Color.WHITE);
                chooseAttemptsPane.getChildren().add(text);

                HBox otherAttemptsPane = new HBox();
                otherAttemptsPane.setSpacing(10);
                while(true){
                    Tile t2 = this.controller.getScrummy().getDeck().pop();
                    otherAttemptsPane.getChildren().add(this.generateTilePane(t2));
                    otherAttempts.add(t2);
                    if(t2.getValue() > highestValue) {
                        highestValue = t2.getValue();
                        startIndex = index;
                        break;
                    } else if (t2.getValue() < highestValue) {
                        break;
                    }
                }
                chooseAttemptsPane.getChildren().add(otherAttemptsPane);
            }
            index++;
        }

        selectedTiles.addAll(otherAttempts);
        for(Tile t : selectedTiles){
            this.controller.getScrummy().getDeck().push(t);
        }

        playerOrder.getChildren().add(chooseAttemptsPane);


        System.out.println("START INDEX: " + startIndex);
        this.controller.setCurrentPlayerIndex(startIndex);

        Text wintext = new Text(
                this.controller.getPlayerControllers().get(startIndex).getPlayer().getName()
                        + " will play first.");
        wintext.setFont(Font.font ("Verdana", 15));
        wintext.setFill(Color.WHITE);
        playerOrder.getChildren().add(wintext);

        Button play = new Button("START GAME");
        play.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        play.setOnMouseClicked(e -> {
            this.loadGamePane();
        });
        playerOrder.getChildren().add(play);

        this.root.getChildren().add(playerOrder);
    }

    public StackPane generateTilePaneLarge(Tile t){
        Rectangle rectangle = new Rectangle( 100,100,70,90);
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
        return tile;
    }

    public StackPane generateTilePane(Tile t){
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
        return tile;
    }

    public void loadRigPane(){

    }

    public void loadImportSavePane(){

    }

    public void loadGamePane(){
        //SET UP GAME SCREEN
        this.gamePane.setStyle("-fx-background-color: #333333");
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
                if(this.fromMeld != null){
                    this.fromMeld.remove(selectedTile);
                    this.fromMeld = null;
                } else {
                    this.controller.getPlayerController(this.currentPlayerIndex).getPlayer().getHand().remove(selectedTile);
                }
                selectedTile = null;
                draw();
            } else {
                System.out.println("Select a tile first");
            }
        });
        newMeldBtn.setPrefSize(100, 20);

        this.topButtons.getChildren().addAll(finishTurnBtn, newMeldBtn);
        this.gamePane.add(this.topButtons, 0, 0);
        this.gamePane.add(this.tablePane, 0, 1);

        this.root.getChildren().add(gamePane);
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
                                    //int meldIndex = controller.getScrummy().getTable().getMelds().indexOf(m);
                                    controller.getPlayerController(currentPlayerIndex).getPlayer().getHand().add(t);
                                    //controller.getScrummy().getTable().getMelds().get(meldIndex).remove(t);
                                    m.getTiles().remove(t);
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
                                        fromMeld.getTiles().remove(selectedTile);
                                        toMeld.getTiles().add(selectedTile);
                                        controller.getScrummy().getTable().checkMeldZeroValidAndAppend();
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
                            } else {
                                fromMeld = playerControl.getPlayer().getHand();
                            }
                        } else {
                            //YOU HAVE TO SELECT A TILE FIRST
                            System.out.println(fromMeld);
                            if(selectedTile != null && fromMeld == controller.getScrummy().getTable().getMelds().get(currentPlayerIndex)){ //Only allow player to add back tiles from new meld
                                playerControl.getPlayer().getHand().add(selectedTile);
                                fromMeld.remove(selectedTile);
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
        if(!(this.controller.getPlayerControllers().get(this.currentPlayerIndex) instanceof PlayerInteractionController)){ //TODO: set these check if 0s to check if instance of player interaction controller instead
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


    public StackPane getRoot() {
        return root;
    }

    public void setRoot(StackPane root) {
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