package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.VierGewinnt;
import net.Client;
import net.server.Server;
import view.event.GameInputEvent;

import java.io.IOException;
import java.net.InetAddress;

public class Main extends Application {

    private Stage primaryStage;

    private VierGewinnt game;
    private GameViewFX gameView;

    Server server;
    Client client;

    private Parent menu;

    // FXML-file: Frame.fxml
    @FXML
    private BorderPane contentPane;

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
        try {
            menu = loadFXML("gui/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent frame = loadFXML("gui/Frame.fxml");
        contentPane.setLeft(menu);
        Scene contentScene = new Scene(frame);
        contentScene.getStylesheets().add("view/gui/style.css");

        closeMenuBar.setOnAction(e -> close());
        closeButton.setOnAction(e -> close());

        createMenuBar.setOnAction(e -> createGameServer());
        createButton.setOnAction(e -> createGameServer());

        EventHandler<ActionEvent> createGameHandler = a -> {
            game = new VierGewinnt();
            gameView(e -> game.placePiece(e.getColumn()));
        };
        createLocalMultiplayerMenuBar.setOnAction(createGameHandler);
        createLocalMultiplayerButton.setOnAction(createGameHandler);

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
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource(name));
        return loader.load();
    }

    private void popupIPAddress() {
        Stage ipPopup = new IPAddressPopup(primaryStage, e -> {
            System.out.println(nameInput.getText() + " tritt Spiel bei mit IP-Adresse " + e.getMessage());
            joinGameServer(e.getMessage());
        });
    }

    private void gameView(EventHandler<GameInputEvent> gameInputHandler) {
        this.gameView = new GameViewFX(game, gameInputHandler, e -> menuView());
        this.gameView.drawGame();

        contentPane.setLeft(gameView.getGameView());
    }

    private void menuView() {
        if (client != null) {
            client.close();
        }
        if (server != null) {
            server.close();
        }

        contentPane.setLeft(menu);
    }

    private void createGameServer() {
        try {
            server = new Server();
            new Thread(server).start();

            joinGameServer("127.0.0.1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void joinGameServer(String ipAddress) {
        try {
            client = new Client(InetAddress.getByName(ipAddress), this.nameInput.getText(), e -> {
                // when the client receives an action from the server
                if (e.getEventType() == GameInputEvent.PUT_PIECE) {
                    game.placePiece(e.getColumn(), 2);
                }
                gameView.drawGame();
            });
            new Thread(client).start();

            game = new VierGewinnt();
            gameView(e -> {
                // when the player places a piece
                game.placePiece(e.getColumn(), 1);
                client.send(e);
            });
        } catch (IOException e) {
            Popup errorPopup = new Popup("Ein Fehler ist aufgetreten!", primaryStage);
            errorPopup.setScene(new Scene(new Label(e.getMessage())));
            errorPopup.setWidth(300);
            errorPopup.setHeight(200);
            errorPopup.show();

            e.printStackTrace();
        }
    }

    public void close() {
        // TODO create close operations
        System.exit(0);
    }
}
