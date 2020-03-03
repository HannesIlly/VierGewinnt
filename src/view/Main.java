package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.VierGewinnt;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private FXMLLoader loader;

    private GameViewFX gameView;

    // FXML-file: Menu.fxml
    @FXML
    private MenuItem closeMenuBar;
    @FXML
    private MenuItem createMenuBar;
    @FXML
    private MenuItem createLocalMultiplayerMenuBar;
    @FXML
    private MenuItem joinMenuBar;
    @FXML
    private TextField nameInput;
    @FXML
    private Button createButton;
    @FXML
    private Button createLocalMultiplayerButton;
    @FXML
    private Button joinButton;
    @FXML
    private Button closeButton;

    public static void main(String[] args) {
        launch(args);
    }

    public Main() {
        this.loader = new FXMLLoader();
        this.loader.setController(this);

        VierGewinnt game = new VierGewinnt();
        this.gameView = new GameViewFX(game, e -> {
            game.placePiece(e.getColumn());
            this.gameView.drawGame();
        });
        this.gameView.drawGame();
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Parent menu = loadFXML("Menu.fxml");
        Scene contentScene = new Scene(menu);
        contentScene.getStylesheets().add("view/style.css");

        closeMenuBar.setOnAction(e -> close());
        closeButton.setOnAction(e -> close());

        //createMenuBar.setOnAction(e -> popupGameScene());
        //createButton.setOnAction(e -> popupGameScene());

        createLocalMultiplayerMenuBar.setOnAction(e -> popupGameScene());
        createLocalMultiplayerButton.setOnAction(e -> popupGameScene());

        joinMenuBar.setOnAction(e -> popupIPAddress());
        joinButton.setOnAction(e -> popupIPAddress());

        primaryStage.setOnCloseRequest(e -> close());
        primaryStage.setTitle("Vier Gewinnt");
        primaryStage.setScene(contentScene);
        primaryStage.show();
    }

    /**
     * Loads the root parent object from the given fxml-file.
     *
     * @param name The name of the fxml-file, that is loaded.
     * @return The parent
     * @throws IOException If an exception occurs when loading the fxml-file.
     */
    private Parent loadFXML(String name) throws IOException {
        loader.setLocation(getClass().getResource(name));
        return loader.load();
    }

    private void popupIPAddress() {
        Stage ipPopup = new IPAddressPopup(primaryStage, e -> System.out.println(nameInput.getText() + " tritt Spiel bei mit IP-Adresse " + e.getMessage()));
    }

    private void popupGameScene() {
        this.gameView.drawGame();

        Stage popup = new Popup("Vier Gewinnt TEST", primaryStage);
        BorderPane root = new BorderPane();
        root.setCenter(gameView.getCanvas());
        popup.setScene(new Scene(root));
        popup.setWidth(800);
        popup.setHeight(600);
        popup.show();
    }

    public void close() {
        // TODO create close operations
        System.exit(0);
    }
}
