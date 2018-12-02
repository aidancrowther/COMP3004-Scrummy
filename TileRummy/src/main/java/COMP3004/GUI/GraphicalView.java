package COMP3004.GUI;

import COMP3004.artificial_intelligence.Strategy4;
import COMP3004.controllers.Controller;
import COMP3004.controllers.GameInteractionController;
import COMP3004.controllers.PlayerInteractionController;
import COMP3004.models.Joker;
import COMP3004.models.Meld;
import COMP3004.models.Table;
import COMP3004.models.Tile;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class GraphicalView {
    protected Table tableBefore;
    protected Meld handBefore;
    protected Tile selectedTile;
    protected ArrayList<Meld> tableDiff = new ArrayList<Meld>();

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

    protected boolean limitHumanTime = false;
    protected Timer timer = new Timer();
    //protected Table table;


    protected boolean suggestionsEnabled = false;
    protected Strategy4 suggestionEngine;
    protected ArrayList<Meld> suggestedPlays;

    protected Tile selectedTileForRigging;
    protected int riggedStartIndex = 0;

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
            loadGameSettingsPane(false);
        });
        buttons.getChildren().add(playButton);

        /*Button loadButton = new Button("LOAD SAVED GAME");
        loadButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        loadButton.setMaxSize(270, 100);
        loadButton.setOnMouseClicked(e -> {
            loadImportSavePane();
        });
        buttons.getChildren().add(loadButton);*/

        Button rigButton = new Button("NEW RIGGED GAME");
        rigButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        rigButton.setMaxSize(270, 100);
        rigButton.setOnMouseClicked(e -> {
            //loadRigPane();
            loadGameSettingsPane(true);
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

    public void loadGameSettingsPane(boolean rigged){
        //- drop downs for players 2-4
        //When new player created show spawned order tile for the new player
        //start game button
        // -- loads game
        VBox setPlayersMenu = new VBox();
        setPlayersMenu.setPadding(new Insets(30, 50, 30, 50));
        //buttons.setPadding(new Insets(30, 365, 50, 365));
        setPlayersMenu.setSpacing(30);
        setPlayersMenu.setStyle("-fx-background-color: #333333");

        Text title = new Text("Game setup:");
        title.setFont(Font.font ("Verdana", 20));
        title.setFill(Color.WHITE);
        setPlayersMenu.getChildren().add(title);

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

        CheckBox limitCheck = new CheckBox("Limit human player turn times");
        limitCheck.setStyle("-fx-text-fill:white");
        limitCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                limitHumanTime = new_val;
            }
        });
        setPlayersMenu.getChildren().add(limitCheck);

        CheckBox suggestionsCheck = new CheckBox("Enable real time suggestions");
        suggestionsCheck.setStyle("-fx-text-fill:white");
        suggestionsCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                suggestionsEnabled = new_val;
            }
        });
        setPlayersMenu.getChildren().add(suggestionsCheck);

        Button play = new Button(rigged ? "-> RIGGING SETUP" : "PLAY");
        play.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        play.setOnMouseClicked(e -> {
            //this.setCurrentPlayerIndex(0);
            for(String type : playerTypes){
                //System.out.println(type);
                if(type != null){
                    controller.addPlayer(type);
                }
            }

            if(rigged){
                this.loadRigPane(playerTypes);
            } else {
                this.loadPlayerOrderPane();
            }
        });
        setPlayersMenu.getChildren().add(play);

        this.root.getChildren().add(setPlayersMenu);
    }

    public void loadPlayerOrderPane(){

        if(this.suggestionsEnabled){
            this.suggestionEngine = new Strategy4();
        }

        VBox playerOrder = new VBox();
        playerOrder.setPadding(new Insets(30, 50, 30, 50));
        playerOrder.setSpacing(30);
        playerOrder.setStyle("-fx-background-color: #333333");


        Text title = new Text("Player order:");
        title.setFont(Font.font ("Verdana", 20));
        title.setFill(Color.WHITE);
        playerOrder.getChildren().add(title);

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


        //System.out.println("START INDEX: " + startIndex);
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

    public StackPane generateTilePaneMini(Tile t){
        Rectangle rectangle = new Rectangle( 100,100,25,35);
        rectangle.setFill(Color.rgb(252, 248, 224,1.0));//")); //rgb()
        Text text = new Text(Integer.toString(t.getValue()));
        text.setFont(Font.font ("Verdana", 14));
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

    public void loadRigPane(ArrayList<String> playerTypes){
        if(this.suggestionsEnabled){
            this.suggestionEngine = new Strategy4();
        }
        /*CHOOSE PLAYER ORDER, HANDS, AND TILES POPPED FORM DECKS FOR USER*/
        //Create an array for rigged cards for each user

        ArrayList<Meld> riggedHands = new ArrayList<Meld>();
        ArrayList<ArrayList<Tile>> riggedDeckOrders = new ArrayList<ArrayList<Tile>>();
        for(GameInteractionController playerController : this.controller.getPlayerControllers()){
            riggedHands.add(new Meld());
            riggedDeckOrders.add(new ArrayList<Tile>());
        }
        Stack<Tile> deckCopy = this.controller.getScrummy().getDeck().clone();


        VBox rigOptions = new VBox();
        rigOptions.setPadding(new Insets(30, 50, 30, 50));
        //buttons.setPadding(new Insets(30, 365, 50, 365));
        rigOptions.setSpacing(30);
        rigOptions.setStyle("-fx-background-color: #333333");

        Text title = new Text("Game Rigging");
        title.setFont(Font.font ("Verdana", 20));
        title.setFill(Color.WHITE);
        rigOptions.getChildren().add(title);


        Text sub = new Text("The Game Deck:");
        sub.setFont(Font.font ("Verdana", 15));
        sub.setFill(Color.WHITE);
        rigOptions.getChildren().add(sub);
        //Show interactive deck
        FlowPane deckPane = new FlowPane();
        deckPane.setHgap(2);
        deckPane.setVgap(2);
        int tileIndex = 0;
        for(Tile t : deckCopy){
            //set selectedTileForRigging
            StackPane tilePane = this.generateTilePaneMini(t);
            tilePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(selectedTileForRigging == t) {
                        selectedTileForRigging = null;
                        Rectangle r = (Rectangle)tilePane.getChildren().get(0);
                        r.setFill(Color.rgb(252, 248, 224,1.0));
                    } else {
                        selectedTileForRigging = t;
                        Rectangle r = (Rectangle)tilePane.getChildren().get(0);
                        r.setFill(Color.rgb(255, 255, 255,0.5));
                    }
                }
            });
            deckPane.getChildren().add(tilePane);
            tileIndex++;
        }
        rigOptions.getChildren().add(deckPane);


        Text sub1 = new Text("Player Hands and Rigged Deck Tiles");
        sub1.setFont(Font.font ("Verdana", 15));
        sub1.setFill(Color.WHITE);
        rigOptions.getChildren().add(sub1);
        Text desc1 = new Text("Please click tiles from the deck below to populate the player hands. It will automatically move to the next player when you've filled up the current player's hand.");
        desc1.setFont(Font.font ("Verdana", 12));
        desc1.setFill(Color.WHITE);
        rigOptions.getChildren().add(desc1);



        for(GameInteractionController playerController : this.controller.getPlayerControllers()) {
            VBox playerSetupPane = new VBox();
            playerSetupPane.setSpacing(10);
            HBox buttons = new HBox();
            buttons.setSpacing(10);
            HBox handAndDeckTiles = new HBox();
            //Mini tile view of player hand and rigged deck
            FlowPane playerHand = new FlowPane();
            playerHand.setHgap(2);
            playerHand.setVgap(2);
            FlowPane playerDeckTiles = new FlowPane();
            playerDeckTiles.setHgap(2);
            playerDeckTiles.setVgap(2);

            //Buttons for adding to players hand or rigged deck
            Button addToHand = new Button("Add to hand");
            addToHand.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
            addToHand.setOnMouseClicked(e -> {
                if(selectedTileForRigging != null){
                    int playerIndex = controller.getPlayerControllers().indexOf(playerController);
                    playerHand.getChildren().add(generateTilePaneMini(selectedTileForRigging)); //add to view
                    riggedHands.get(playerIndex).add(selectedTileForRigging); //save for setup later
                }
            });

            Button addToPlayerDeck = new Button("Add to rigged draws");
            addToPlayerDeck.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
            addToPlayerDeck.setOnMouseClicked(e -> {
                if(selectedTileForRigging != null){
                    int playerIndex = controller.getPlayerControllers().indexOf(playerController);
                    playerDeckTiles.getChildren().add(generateTilePaneMini(selectedTileForRigging)); //add to view
                    riggedDeckOrders.get(playerIndex).add(selectedTileForRigging); //save for setup later
                }
            });
            buttons.getChildren().addAll(addToHand, addToPlayerDeck);


            handAndDeckTiles.getChildren().addAll(playerHand, playerDeckTiles);
            playerSetupPane.getChildren().addAll(buttons, handAndDeckTiles);
            rigOptions.getChildren().add(playerSetupPane);

        }


        //START INDEX

        Text sub3= new Text("Starting Player");
        sub3.setFont(Font.font ("Verdana", 15));
        sub3.setFill(Color.WHITE);
        rigOptions.getChildren().add(sub3);


        ToggleGroup startGroup = new ToggleGroup();
        HBox radioButtons = new HBox();
        radioButtons.setSpacing(10);
        int playerIndex = 0;
        for(GameInteractionController playerController : this.controller.getPlayerControllers()) {
            RadioButton playerRadio = new RadioButton(playerController.getPlayer().getName());
            playerRadio.setStyle("-fx-text-fill:white");
            playerRadio.setToggleGroup(startGroup);
            playerRadio.setUserData(playerIndex);
            radioButtons.getChildren().add(playerRadio);
            playerIndex++;
        }

        startGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (startGroup.getSelectedToggle() != null) {
                    riggedStartIndex = Integer.parseInt(startGroup.getSelectedToggle().getUserData().toString());
                }
            }
        });
        rigOptions.getChildren().add(radioButtons);

        Button play = new Button("START GAME");
        play.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        play.setOnMouseClicked(e -> {
            //Save game settings

            int i = 0;
            for(Meld hand : riggedHands){
                controller.getPlayerControllers().get(i).getPlayer().setHand(hand);
                i++;
            }

            i = 0;
            for(ArrayList<Tile> riggedTiles : riggedDeckOrders){
                controller.getPlayerControllers().get(i).setRiggedTiles(riggedTiles);
                i++;
            }

            //Set starting player
            System.out.println("Start: " + riggedStartIndex);
            controller.setCurrentPlayerIndex(riggedStartIndex);

            //Load game
            this.loadGamePane();
        });
        rigOptions.getChildren().add(play);


        this.root.getChildren().add(rigOptions);
    }

    public void loadImportSavePane(){

    }

    public void loadGamePane(){
        //SET UP GAME SCREEN
        this.gamePane.setStyle("-fx-background-color: #333333");
        this.tablePane.setStyle("-fx-background-color: #333333");
        this.tablePane.setMinSize(1000,600);

        this.topButtons.setStyle("-fx-background-color: #333333");
        this.topButtons.setSpacing(10);
        Button finishTurnBtn = new Button("Finish Turn");
        finishTurnBtn.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        finishTurnBtn.setOnMouseClicked(e -> {
            this.timer.cancel();
            this.finishTurn();
        });
        finishTurnBtn.setPrefSize(100, 20);

        Button newMeldBtn = new Button("Add To New Meld");
        newMeldBtn.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
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
        draw();
    }

    public void draw(){
        this.tablePane.getChildren().clear();
        int i = 1;
        for(GameInteractionController iControl : this.controller.getPlayerControllers()){
            this.drawHand(iControl, i-1, i % 2 == 0);
            i++;
        }
        this.drawTable(this.controller.getScrummy().getTable());
        this.drawSuggestedMelds();
    }

    public void drawTable(Table table){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: #333333");


        GridPane gridPane = new GridPane();

        gridPane.setStyle("-fx-background-color: #333333");
        gridPane.setMinSize(800, 500);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(10);

        VBox rows = new VBox();
        rows.setSpacing(10);
        rows.setPadding(new Insets(10, 10, 10, 10));

        int i = 0;
        int j = 0;
        for(Meld m : table.getMelds()){
            HBox cols = new HBox();
            if(m.getTiles().size()!=0){
                for(Tile t : m.getTiles()) {
                    Rectangle rectangle = new Rectangle( 100,100,30,50);
                    if(this.selectedTile == t){
                        rectangle.setFill(Color.rgb(242, 255, 230,1));
                    } else {
                        rectangle.setFill(Color.rgb(252, 248, 224,1));//")); //rgb()
                    }

                    if(this.tableDiff.contains(m)){
                        rectangle.setFill(Color.rgb(245, 221, 213,1.0));//")); //rgb()
                    }

                    String tileText = Integer.toString(t.getValue());
                    if(t instanceof Joker){
                        //System.out.println("Mask joker");
                        tileText = "J";
                    }
                    Text text = new Text(tileText);
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
                    tile.setPadding(new Insets(0, 2.5, 0, 2.5));
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
                                //System.out.println(controller.getScrummy().getTable().getMelds().size());
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
                    //gridPane.add(tile, i, j);
                    cols.getChildren().add(tile);
                    i++;
                    if(m.size() == i){
                      i = 0;
                    }
                }
                rows.getChildren().add(cols);
            }
            j++;
        }

        this.tablePane.setCenter(null);
        scrollPane.setContent(rows);
        this.tablePane.setCenter(scrollPane);
        BorderPane.setAlignment(scrollPane, Pos.CENTER);
    }

    public Text getTileText(Tile t, boolean pressed){
        Text text = new Text(Integer.toString(t.getValue()) + (pressed ? "*" : ""));
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
        return text;
    }

    public void drawHand(GameInteractionController playerControl, int index, boolean isHorizontal) {
        GridPane handPane = new GridPane();
        handPane.setAlignment(Pos.CENTER);
        handPane.setPadding(new Insets(10, 10, 10, 10));
        handPane.setVgap(5);
        handPane.setHgap(5);

        int i = 0;
        int j = 0;
        for(Tile t : playerControl.getPlayer().getHand().getTiles()) {
            Rectangle rectangle = new Rectangle( 400,100,30,50);
            if(this.selectedTile == t){
                rectangle.setFill(Color.rgb(242, 255, 230,1));
            } else {
                rectangle.setFill(Color.rgb(252, 248, 224,1));//")); //rgb()
            }
            String tileText = Integer.toString(t.getValue());
            if(t instanceof Joker){
                t.setValue(0);
                t.setColour('J');
                tileText = "J";
            }
            Text text = new Text(tileText);
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
                            //System.out.println(fromMeld);
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
                //playerControl.getPlayer().getHand()
                handPane.add(tile, j, i);
                if(i == 7){
                    i = 0;
                    j++;
                } else {
                    i++;
                }
            } else {
                handPane.add(tile, i, j);
                if(i == 18){
                    i = 0;
                    j++;
                } else {
                    i++;
                }
            }
        }

        VBox handLabelPane = new VBox();
        Text text = new Text(playerControl.getPlayer().getName());
        text.setFont(Font.font ("Verdana", 15));
        text.setFill(Color.WHITE);
        text.setTextAlignment(TextAlignment.CENTER);

        if(!isHorizontal){
            text.setTranslateX(450);
        }
        handLabelPane.getChildren().add(text);
        handLabelPane.getChildren().add(handPane);
        if(index == 0){
            handLabelPane.setPadding(new Insets(30, 10, 10, 10));
            if(index==this.currentPlayerIndex){
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #333333;");
            }
            this.tablePane.setBottom(handLabelPane);
        } else if (index == 1) {
            handLabelPane.setPadding(new Insets(0, 10, 10, 10));
            if(index==this.currentPlayerIndex){
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #333333;");
            }
            this.tablePane.setRight(handLabelPane);
        } else if (index == 2) {
            handLabelPane.setPadding(new Insets(10, 10, 10, 10));
            if(index==this.currentPlayerIndex){
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #333333;");
            }
            this.tablePane.setTop(handLabelPane);
        } else if (index == 3) {
            handLabelPane.setPadding(new Insets(0, 10, 10, 10));
            if(index==this.currentPlayerIndex){
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #333333;");
            }
            this.tablePane.setLeft(handLabelPane);
        }

        BorderPane.setAlignment(handLabelPane, Pos.CENTER);
        //BorderPane.setMargin(handPane, new Insets(12,12,12,12));
    }

    public void drawSuggestedMelds(){
        if(this.suggestionsEnabled){
            System.out.println(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getName());
            System.out.println(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getHand());
            this.suggestedPlays = this.suggestionEngine.getSuggestedPlays(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getHand(), this.controller.getScrummy().getTable());
        }

        //TODO: SUGGESTED PLAYS CLEANUP
        if(this.topButtons.getChildren().size() >= 2){
            if(this.topButtons.getChildren().size() == 3){
                this.topButtons.getChildren().remove(2);
            }
            String suggestedText = "Suggested Melds:\n";
            if(this.suggestionsEnabled && this.controller.getPlayerControllers().get(this.currentPlayerIndex) instanceof PlayerInteractionController){
                //ArrayList<Meld> suggestedPlays
                int index = 0;
                for(Meld m : this.suggestedPlays){
                    if(index != 0){
                        suggestedText += ", ";
                    }
                    suggestedText += m.toString();
                    index++;
                }
            }
            Text suggestions = new Text(suggestedText);
            suggestions.setFont(Font.font ("Verdana", 15));
            suggestions.setFill(Color.WHITE);
            this.topButtons.getChildren().add(suggestions);
        }
    }

    public void addPlayer(Meld hand) {
        this.playerHands.add(hand);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int c) {
        this.currentPlayerIndex = c;
        if(this.tableBefore != null){
            this.tableDiff = controller.getScrummy().getTable().getDiff(this.tableBefore);
            /*System.out.println("DIFF:");
            for(Meld m:this.tableDiff){
                System.out.println(m);
            }*/
        }

        this.tableBefore = controller.getScrummy().getTable().copy();
        //System.out.println("PLayer: " + this.currentPlayerIndex);
        this.handBefore = controller.getPlayerController(c).getPlayer().getHand().copy();
        this.draw();

        if(!(this.controller.getPlayerControllers().get(this.currentPlayerIndex) instanceof PlayerInteractionController)){
            this.finishTurn();
        } else {
            if(this.limitHumanTime){
                System.out.println("starting the countdown!");
                this.timer = new Timer();
                long delay = 1000L*60*2;
                this.timer.schedule(new PlayerTimerTask(), delay);
            }
        }
    }

    public void finishTurn(){
        this.controller.finishTurn();
        //this.draw();
    }

    public void displayWin() {
        BorderPane winnerScreen = new BorderPane();
        winnerScreen.setStyle("-fx-background-color: #333333");
        Text winnerText = new Text(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getName()+ " has won the game!");
        winnerText.setFont(Font.font ("Verdana", 20));
        winnerText.setFill(Color.WHITE);
        BorderPane.setAlignment(winnerText, Pos.CENTER);
        winnerScreen.setCenter(winnerText);
        this.root.getChildren().add(winnerScreen);
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

    class PlayerTimerTask extends TimerTask {
        public void run() {
            System.out.println("Turn Complete");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    finishTurn();
                }
            });
            //
        }
    }
    /*public Pane getFirstLayer() {
        return firstLayer;
    }

    public void setFirstLayer(Pane firstLayer) {
        this.firstLayer = firstLayer;
    }*/

}