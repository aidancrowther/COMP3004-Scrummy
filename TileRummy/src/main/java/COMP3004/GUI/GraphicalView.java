package COMP3004.GUI;

import COMP3004.artificial_intelligence.Strategy4;
import COMP3004.controllers.Controller;
import COMP3004.controllers.FileInputController;
import COMP3004.controllers.GameInteractionController;
import COMP3004.models.*;
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
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
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
    protected Timer displayerTimer = new Timer();

    protected boolean suggestionsEnabled = false;
    protected Strategy4 suggestionEngine;
    protected ArrayList<Meld> suggestedPlays;

    protected Tile selectedTileForRigging;
    protected int riggedStartIndex = 0;
    protected int startIndex = 0;

    protected boolean isNetworked = false;

    protected char colour = 'R';
    protected int value = 1;

    Stage primaryStage;

    public GraphicalView(Controller controller){
        //SET UP MENU SCREEN
        this.menuPane.setStyle("-fx-background-color: #000000");
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
            loadGameSettingsPane(false);
        });
        buttons.getChildren().add(playButton);

        Button rigButton = new Button("NEW RIGGED GAME");
        rigButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        rigButton.setMaxSize(270, 100);
        rigButton.setOnMouseClicked(e -> {
            loadGameSettingsPane(true);
        });
        buttons.getChildren().add(rigButton);

        Button scenarioButton = new Button("LOAD GAME SCENARIO");
        scenarioButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        scenarioButton.setMaxSize(270, 100);
        scenarioButton.setOnMouseClicked(e -> {
            loadGameFactorySettingsPane();
        });
        buttons.getChildren().add(scenarioButton);

        /*Button hostButton = new Button("HOST GAME");
        hostButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        hostButton.setMaxSize(270, 100);
        hostButton.setOnMouseClicked(e -> {
            loadGameSettingsPane(false);
        });
        buttons.getChildren().add(hostButton);

        Button connectButton = new Button("CONNECT TO GAME");
        connectButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        connectButton.setMaxSize(270, 100);
        connectButton.setOnMouseClicked(e -> {
            isNetworked = true;
        });
        buttons.getChildren().add(connectButton);*/


        Button connectButton = new Button("LOAD FROM FILE");
        connectButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        connectButton.setMaxSize(270, 100);
        connectButton.setOnMouseClicked(e -> {
            loadFromFilePane();
        });
        buttons.getChildren().add(connectButton);

        this.menuPane.getChildren().add(buttons);
        this.root.getChildren().add(menuPane);

        this.controller = controller;
        for(GameInteractionController iControl : controller.getPlayerControllers()){
            iControl.setGUI(this);
            this.addPlayer(iControl.getPlayer().getHand());
        }
    }

    public void loadFromFilePane(){
        VBox setPlayersMenu = new VBox();
        setPlayersMenu.setStyle("-fx-background-color: #000000");
        Text title = new Text("Enter file name:");
        title.setFont(Font.font ("Verdana", 20));
        title.setFill(Color.WHITE);
        setPlayersMenu.getChildren().add(title);

        TextField textField = new TextField ();
        setPlayersMenu.getChildren().add(textField);
        Button connectButton = new Button("K");
        connectButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 2em;-fx-text-fill:#ffffff;");
        connectButton.setMaxSize(270, 100);
        connectButton.setOnMouseClicked(e -> {
            String filename = textField.getText();
            File deckFile = new File("./fileInput/"+filename);
            Deck d = FileInputController.loadDeck(FileInputController.load(deckFile));
            controller.getScrummy().setDeck(d);
            //stuff to load form file
            loadGameSettingsPane(false);
        });
        setPlayersMenu.getChildren().add(connectButton);


        this.root.getChildren().add(setPlayersMenu);

    }

    public void loadGameSettingsPane(boolean rigged){
        //- drop downs for players 2-4
        //When new player created show spawned order tile for the new player
        //start game button
        // -- loads game
        VBox setPlayersMenu = new VBox();
        setPlayersMenu.setPadding(new Insets(30, 50, 30, 50));
        setPlayersMenu.setSpacing(30);
        setPlayersMenu.setStyle("-fx-background-color: #000000");

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
                    controller.addPlayer(type, rigged);
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
        playerOrder.setStyle("-fx-background-color: #000000");


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
        //int startIndex = 0;
        int index = 0;

        ArrayList<Tile> otherAttempts = new ArrayList<Tile>();
        for(Tile t : selectedTiles){
            if(highestValue == -1){
                highestValue = t.getValue();
            } else if(t.getValue() > highestValue){
                this.startIndex = index;
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
                        this.startIndex = index;
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

        Text wintext = new Text(
                this.controller.getPlayerControllers().get(this.startIndex).getPlayer().getName()
                        + " will play first.");
        wintext.setFont(Font.font ("Verdana", 15));
        wintext.setFill(Color.WHITE);
        playerOrder.getChildren().add(wintext);

        Button play = new Button("START GAME");
        play.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        play.setOnMouseClicked(e -> {
            //System.out.println("START INDEX: " + startIndex);
            currentPlayerIndex = startIndex;
            controller.setCurrentPlayerIndex(startIndex);
            if(isNetworked){
                this.controller.setupHostedGame();
            }
            if(this.limitHumanTime && this.controller.isPlayerHuman()){
                System.out.println("start tiemr");
                this.startTimer();
            }
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
        //Text text = new Text(Integer.toString(t.getValue()));
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
        tile.getChildren().addAll(rectangle, text);
        return tile;
    }

    public void loadRigPane(ArrayList<String> playerTypes){
        if(this.suggestionsEnabled){
            this.suggestionEngine = new Strategy4();
        }
        /*CHOOSE PLAYER ORDER, HANDS, AND TILES POPPED FORM DECKS FOR USER*/
        //Create an array for rigged cards for each user


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: #000000");

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
        rigOptions.setStyle("-fx-background-color: #000000");

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
        Text desc1 = new Text("Please click tiles from the deck below then click an action in one of the player's settings.");
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

            Text playerName = new Text(playerController.getPlayer().getName() + " Settings:");
            playerName.setFont(Font.font ("Verdana", 12));
            playerName.setFill(Color.WHITE);

            handAndDeckTiles.getChildren().addAll(playerHand, playerDeckTiles);
            playerSetupPane.getChildren().addAll(playerName, buttons, handAndDeckTiles);
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
            currentPlayerIndex = riggedStartIndex;
            controller.setCurrentPlayerIndex(riggedStartIndex);
            if(isNetworked){
                this.controller.setupHostedGame();
            }

            if(this.limitHumanTime && this.controller.isPlayerHuman()){
                System.out.println("start tiemr");
                this.startTimer();
            }
            this.loadGamePane();
        });
        rigOptions.getChildren().add(play);

        scrollPane.setContent(rigOptions);
        this.root.getChildren().add(scrollPane);
    }

    public void loadGameFactorySettingsPane(){

        VBox strategyPane = new VBox();

        strategyPane.setPadding(new Insets(30, 50, 30, 50));
        //buttons.setPadding(new Insets(30, 365, 50, 365));
        strategyPane.setSpacing(30);
        strategyPane.setStyle("-fx-background-color: #000000");

        Text title = new Text("Scenario Selection");
        title.setFont(Font.font ("Verdana", 20));
        title.setFill(Color.WHITE);
        strategyPane.getChildren().add(title);

        Button playButton = new Button("START GAME");

        String gameType = "";
        ChoiceBox<String> options = new ChoiceBox<>(FXCollections.observableArrayList(
            "Scenario 1", "Scenario 2", "Scenario 3")
        );
        options.setOnAction(e -> {
             controller.setGameType(options.getSelectionModel().getSelectedItem().toString());
        });
        options.getSelectionModel().selectFirst();

        playButton.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        playButton.setOnMouseClicked(e -> {
            this.controller.setupGameByType();
            this.loadGamePane();
            currentPlayerIndex = 0;
            controller.setCurrentPlayerIndex(0);
        });


        strategyPane.getChildren().add(options);
        strategyPane.getChildren().add(playButton);
        this.root.getChildren().add(strategyPane);

    }

    public void loadGamePane(){
        this.tableBefore = this.controller.getScrummy().getTable().copy();
        this.handBefore = this.controller.getPlayerController(this.currentPlayerIndex).getPlayer().getHand().copy();

        //SET UP GAME SCREEN
        this.gamePane.setStyle("-fx-background-color: #000000");
        this.gamePane.setHgap(10);
        this.gamePane.setVgap(10);

        this.gamePane.setPadding(new Insets(15, 15, 15, 15));
        this.tablePane.setStyle("-fx-background-color: #000000");
        this.tablePane.setMinSize(1000,600);

        this.topButtons.setStyle("-fx-background-color: #000000");
        this.topButtons.setSpacing(10);
        Button finishTurnBtn = new Button("Finish Turn");
        finishTurnBtn.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        finishTurnBtn.setOnMouseClicked(e -> {
            this.timer.cancel();
            this.displayerTimer.cancel();
            this.controller.finishTurn();
        });
        finishTurnBtn.setPrefSize(100, 20);

        Button newMeldBtn = new Button("Add To New Meld");
        newMeldBtn.setStyle("-fx-background-color: #00b359;-fx-font-size: 1em;-fx-text-fill:#ffffff;");
        newMeldBtn.setOnMouseClicked(e -> {
            if(this.selectedTile != null){
                if(selectedTile.isJoker()){
                    if(this.fromMeld != null){
                        drawJokerPopup(selectedTile, controller.getScrummy().getTable().getMelds().get(0), fromMeld);
                    } else {
                        drawJokerPopup(selectedTile, controller.getScrummy().getTable().getMelds().get(0), this.controller.getPlayerController(this.currentPlayerIndex).getPlayer().getHand());
                    }
                } else  {
                    controller.getScrummy().getTable().add(selectedTile);
                    if(this.fromMeld != null){
                        this.fromMeld.remove(selectedTile);
                        this.fromMeld = null;
                    } else {
                        this.controller.getPlayerController(this.currentPlayerIndex).getPlayer().getHand().remove(selectedTile);
                    }
                }
                //ADD SCORE
                controller.getPlayerControllers().get(currentPlayerIndex).setScore(controller.getPlayerControllers().get(currentPlayerIndex).getScore()+selectedTile.getValue());
                selectedTile = null;
                draw();
            } else {
                System.out.println("Select a tile first");
            }
        });
        newMeldBtn.setPrefSize(100, 20);
        this.topButtons.getChildren().addAll(finishTurnBtn, newMeldBtn);

        Text tip = new Text("** Select tiles by left clicking on the tile. Select melds by right clicking on a tile in the meld. A tile must be selected before a meld.");
        tip.setFont(Font.font ("Verdana", 12));
        tip.setFill(Color.WHITE);

        Text countDownText = new Text("");
        countDownText.setFont(Font.font ("Verdana", 15));
        countDownText.setFill(Color.WHITE);
        this.topButtons.getChildren().add(countDownText);


        Text suggestionText = new Text("");
        suggestionText.setFont(Font.font("Verdana", 15));
        suggestionText.setFill(Color.WHITE);

        this.gamePane.add(tip, 0, 0);
        this.gamePane.add(this.topButtons, 0, 1);
        this.gamePane.add(suggestionText, 0, 2);
        this.gamePane.add(this.tablePane, 0, 3);

        this.root.getChildren().add(gamePane);

        draw();

        System.out.println(this.currentPlayerIndex);

        if(!(this.controller.isPlayerHuman())) {
            this.startAILoop();
        }
    }

    public void draw(){
        this.tablePane.getChildren().clear();
        this.drawSuggestedMelds();
        int i = 1;
        for(GameInteractionController iControl : this.controller.getPlayerControllers()){
            this.drawHand(iControl, i-1, i % 2 == 0);
            i++;
        }
        this.drawTable(this.controller.getScrummy().getTable());
    }

    public void draw(Table t){
        this.tablePane.getChildren().clear();
        this.drawSuggestedMelds();
        int i = 1;
        for(GameInteractionController iControl : this.controller.getPlayerControllers()){
            this.drawHand(iControl, i-1, i % 2 == 0);
            i++;
        }

        this.drawTable(t);
    }

    public void drawTable(Table table){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: #000000");


        GridPane gridPane = new GridPane();

        gridPane.setStyle("-fx-background-color: #000000");
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
                    } else if ((fromMeld != null && fromMeld.getTiles().contains(t))
                            || (toMeld != null && toMeld.getTiles().contains(t))) {
                        rectangle.setFill(Color.rgb(252, 248, 224,1));//")); //rgb()
                    } else if(this.suggestedPlays != null && this.suggestedPlays.size() > 0) {
                        rectangle.setFill(Color.rgb(252, 248, 224,1));//")); //rgb()
                        ArrayList<Tile> allSuggestedTiles = new ArrayList<>();
                        for(Meld suggestedMeld : this.suggestedPlays){
                            for(Tile suggestedTile : suggestedMeld.getTiles()){
                                allSuggestedTiles.add(suggestedTile);
                            }
                        }
                        for(Tile suggestedTile : allSuggestedTiles) {
                            if(suggestedTile.equals(t)){
                                rectangle.setFill(Color.web("#FAD3C5"));
                            }
                        }
                    } else {
                        rectangle.setFill(Color.rgb(252, 248, 224,0.88));//")); //rgb()
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
                                if(fromMeld == controller.getScrummy().getTable().getMelds().get(0)){
                                    //int meldIndex = controller.getScrummy().getTable().getMelds().indexOf(m);
                                    if(selectedTile.isJoker()){
                                        drawJokerPopup(selectedTile, controller.getPlayerController(currentPlayerIndex).getPlayer().getHand(), fromMeld);
                                    } else {
                                        controller.getPlayerController(currentPlayerIndex).getPlayer().getHand().add(selectedTile);
                                        fromMeld.remove(selectedTile); //TODO use return value here
                                    }

                                    //REMOVE SCORE
                                    controller.getPlayerControllers().get(currentPlayerIndex).setScore(controller.getPlayerControllers().get(currentPlayerIndex).getScore()-selectedTile.getValue());

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
                                        if(selectedTile.isJoker()){
                                            drawJokerPopup(selectedTile, toMeld, fromMeld);
                                        } else {
                                            fromMeld.remove(selectedTile); //TODO: add remove value here
                                            toMeld.add(selectedTile);
                                        }

                                        controller.getScrummy().getTable().checkMeldZeroValidAndAppend();
                                        if(fromMeld == controller.getPlayerControllers().get(currentPlayerIndex).getPlayer().getHand()){
                                            //ADD SCORE if from hand
                                            System.out.println("ADDING SCORE");
                                            controller.getPlayerControllers().get(currentPlayerIndex).setScore(controller.getPlayerControllers().get(currentPlayerIndex).getScore()+selectedTile.getValue());
                                        }

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
            } else if(this.suggestedPlays != null && this.suggestedPlays.size() > 0) {
                rectangle.setFill(Color.rgb(252, 248, 224,1));//")); //rgb()
                ArrayList<Tile> allSuggestedTiles = new ArrayList<>();
                for(Meld suggestedMeld : this.suggestedPlays){
                    for(Tile suggestedTile : suggestedMeld.getTiles()){
                        allSuggestedTiles.add(suggestedTile);
                    }
                }
                for(Tile suggestedTile : allSuggestedTiles) {
                    if(suggestedTile.equals(t)
                            && controller.getPlayerControllers().get(currentPlayerIndex).equals(playerControl)){
                        rectangle.setFill(Color.web("#FAD3C5"));//")); //rgb()
                    }
                }
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
                                if(selectedTile.isJoker()){
                                    drawJokerPopup(selectedTile, controller.getScrummy().getTable().getMelds().get(0),  playerControl.getPlayer().getHand());
                                }  else {
                                    controller.getScrummy().getTable().add(selectedTile); //TODO; add using remove return
                                    playerControl.getPlayer().getHand().remove(selectedTile);
                                }
                                //ADD SCORE
                                playerControl.setScore(playerControl.getScore()+selectedTile.getValue());
                                selectedTile = null;
                            } else {
                                fromMeld = playerControl.getPlayer().getHand();
                            }
                        } else {
                            //YOU HAVE TO SELECT A TILE FIRST
                            if(selectedTile != null && fromMeld == controller.getScrummy().getTable().getMelds().get(0)){ //Only allow player to add back tiles from new meld
                                if(selectedTile.isJoker()){
                                    drawJokerPopup(selectedTile, playerControl.getPlayer().getHand(), fromMeld);
                                } else {
                                    playerControl.getPlayer().getHand().add(selectedTile); //TODO: add using remove return
                                    fromMeld.remove(selectedTile);
                                }

                                //DETRACT SCORE
                                playerControl.setScore(playerControl.getScore()-selectedTile.getValue());
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
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #000000;");
                System.out.println("highlight player 1");
            }
            this.tablePane.setBottom(handLabelPane);
        } else if (index == 1) {
            handLabelPane.setPadding(new Insets(0, 10, 10, 10));
            if(index==this.currentPlayerIndex){
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #000000;");
                System.out.println("highlight player 2");
            }
            this.tablePane.setRight(handLabelPane);
        } else if (index == 2) {
            handLabelPane.setPadding(new Insets(10, 10, 10, 10));
            if(index==this.currentPlayerIndex){
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #000000;");
                System.out.println("highlight player 3");
            }
            this.tablePane.setTop(handLabelPane);
        } else if (index == 3) {
            handLabelPane.setPadding(new Insets(0, 10, 10, 10));
            if(index==this.currentPlayerIndex){
                handLabelPane.setStyle("-fx-border-color: red;-fx-background-color: #000000;");
                System.out.println("highlight player 4");
            }
            this.tablePane.setLeft(handLabelPane);
        }

        BorderPane.setAlignment(handLabelPane, Pos.CENTER);
        //BorderPane.setMargin(handPane, new Insets(12,12,12,12));
    }

    public void drawJokerPopup(Tile t, Meld toMeld, Meld fromMeld){
        System.out.println("Popup");
        Popup popup = new Popup();
        VBox box = new VBox();
        box.setStyle("-fx-background-color: #ffffff");
        box.setMinHeight(200);
        box.setMinHeight(150);

        Text joker = new Text("What tile do you want to set your Joker to?");

        ChoiceBox<Character> colourOption = new ChoiceBox<>(FXCollections.observableArrayList(
                'R', 'G', 'B', 'O')
        );
        colourOption.setOnAction(e -> {
            colour = colourOption.getSelectionModel().getSelectedItem();
        });
        colourOption.getSelectionModel().selectFirst();


        ChoiceBox<Integer> valOption = new ChoiceBox<>(FXCollections.observableArrayList(
                1,2,3,4,5,6,7,8,9,10,11,12,13)
        );
        valOption.setOnAction(e -> {
            value = valOption.getSelectionModel().getSelectedItem();
        });
        valOption.getSelectionModel().selectFirst();


        Button ok = new Button("Add Joker");
        ok.setOnMouseClicked(e->{
            t.setColour(colour);
            t.setValue(value);

            System.out.println(colour + "ww " + t.getColour());
            System.out.println(value + "ww " + t.getValue());
            fromMeld.remove(t);
            toMeld.add(t);
            //TODO: add/ remove
            controller.getPlayerControllers().get(currentPlayerIndex).setScore(controller.getPlayerControllers().get(currentPlayerIndex).getScore() + value);
            draw();
            controller.getScrummy().getTable().checkMeldZeroValidAndAppend();
            popup.hide();
        });

        box.getChildren().add(joker);
        box.getChildren().add(colourOption);
        box.getChildren().add(valOption);
        box.getChildren().add(ok);
        popup.getContent().add(box);
        popup.show(this.gamePane, 100, 100);
        popup.centerOnScreen();
    }

    public void drawSuggestedMelds(){
        if(this.suggestionsEnabled) {
            System.out.println(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getName());
            System.out.println(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getHand());

            suggestionEngine.setScore(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getScore());
            this.suggestedPlays = this.suggestionEngine.getSuggestedPlays(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getHand(), this.controller.getScrummy().getTable());

            Text suggestions = (Text)this.gamePane.getChildren().get(2);
            //TODO: SUGGESTED PLAYS CLEANUP
            /*if (this.topButtons.getChildren().size() >= 2) {
                if (this.topButtons.getChildren().size() == 3) {
                    this.topButtons.getChildren().remove(2);
                }*/
                String suggestedText = "Suggested Melds:\n";
                if (this.suggestionsEnabled && this.controller.isPlayerHuman()) {
                    //ArrayList<Meld> suggestedPlays
                    int index = 0;
                    for (Meld m : this.suggestedPlays) {
                        if (index != 0) {
                            suggestedText += ", ";
                        }
                        suggestedText += m.toString();
                        index++;
                    }
                }
                suggestions.setText(suggestedText);
            //}
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
        System.out.println("CURR PLAYER:  " + controller.getPlayerController(c).getPlayer().getName());
        this.tableBefore = controller.getScrummy().getTable().copy();
        this.handBefore = controller.getPlayerController(c).getPlayer().getHand().copy();
        if(this.limitHumanTime && this.controller.isPlayerHuman()){
            System.out.println("start tiemr");
            this.startTimer();
        }
        draw();
    }

    public void startAILoop() {
        Runnable task = new Runnable(){
            public void run() {
                runAITask();
            }
        };
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void runAITask(){
        this.currentPlayerIndex = this.controller.getCurrentPlayerIndex();
        this.tableBefore = this.controller.getScrummy().getTable().copy();
        this.handBefore = this.controller.getPlayerController(this.currentPlayerIndex).getPlayer().getHand().copy();

        try {
            //draw
            Thread.sleep(1000);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    draw();
                    controller.finishTurn();
                }
            });
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void displayWin() {
        System.out.print("displaying win");
        BorderPane winnerScreen = new BorderPane();
        winnerScreen.setStyle("-fx-background-color: #000000");
        Text winnerText = new Text(this.controller.getPlayerControllers().get(this.currentPlayerIndex).getPlayer().getName()+ " has won the game!");
        winnerText.setFont(Font.font ("Verdana", 20));
        winnerText.setFill(Color.WHITE);
        BorderPane.setAlignment(winnerText, Pos.CENTER);
        winnerScreen.setCenter(winnerText);
        this.root.getChildren().add(winnerScreen);
    }

    public void displayDraw() {
        System.out.print("displaying draw");
        BorderPane winnerScreen = new BorderPane();
        winnerScreen.setStyle("-fx-background-color: #000000");
        Text winnerText = new Text("It's a draw!\n\nThe deck is out of cards, and no one made a move in the last round.");
        winnerText.setFont(Font.font ("Verdana", 20));
        winnerText.setFill(Color.WHITE);
        BorderPane.setAlignment(winnerText, Pos.CENTER);
        winnerScreen.setCenter(winnerText);
        this.root.getChildren().add(winnerScreen);
    }

    class PlayerTimerTask extends TimerTask {
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    controller.finishTurn(true);
                }
            });
            //
        }
    }

    public void startTimer(){
        System.out.println("starting the countdown!");
        this.timer = new Timer();
        long delay = 1000L*60*2;
        this.timer.schedule(new PlayerTimerTask(), delay);
        this.displayerTimer = new Timer();
        this.displayerTimer.schedule(new TimerTask() {
            int timeRemaining = 120;
            public void run() {
                timeRemaining--;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Text time = (Text) topButtons.getChildren().get(topButtons.getChildren().size()-1);
                        time.setText("Seconds remaining: " + timeRemaining);
                        if(timeRemaining == 0){
                            displayerTimer.cancel();
                        }
                    }
                });
                //
            }
        }, 0, 1000);
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

    public ArrayList<Meld> getTableDiff() {
        return tableDiff;
    }

    public void setTableDiff(ArrayList<Meld> tableDiff) {
        this.tableDiff = tableDiff;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}