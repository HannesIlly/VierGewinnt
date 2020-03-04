package view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.VierGewinnt;
import net.Client;
import net.server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class View extends Application {
    // TextFields
    @FXML
    private TextField name;
    @FXML
    private TextField ipAddress;
    
    // Buttons
    @FXML
    private Button getName;
    @FXML
    private Button joinGame;
    @FXML
    private Button createGame;
    
    // other
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label playerName;
    
    private Stage primaryStage;
    private Server server = null;
    private Client client = null;
    
    private Scene menuScene = null;
    
    private Label namePlayer1 = null;
    private Label namePlayer2 = null;
    
    public View() {
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/Menu.fxml"));
        this.primaryStage = primaryStage;
        this.menuScene = new Scene(root);
        
        VierGewinnt game = new VierGewinnt();
        GameViewFX gameView = new GameViewFX(game, e -> System.out.println(""), e -> System.out.println(""));
        game.placePiece(4);
        game.placePiece(2);
        game.placePiece(3);
        game.placePiece(3);
        game.placePiece(5);
        game.placePiece(6);
        game.placePiece(5);
        game.placePiece(2);
        game.placePiece(4);
        game.placePiece(4);
        game.placePiece(3);
        
        primaryStage.setOnCloseRequest(e -> close());
        primaryStage.setTitle("Vier Gewinnt");
        // primaryStage.setScene(menuScene);
        //primaryStage.setScene(gameView.getScene());
        primaryStage.show();
    }
    
    @FXML
    public void name(Event e) {
        playerName.setText(name.getText());
        // primaryStage.setScene(gameScene);
    }
    
    @FXML
    public void joinGame(Event e) {
        InetAddress ipAddress = null;
        
        // Read the ip-address
        try {
            ipAddress = InetAddress.getByName(this.ipAddress.getText());
            System.out.println(ipAddress.getHostAddress());
        } catch (UnknownHostException ex) {
            errorPopup(ex.getMessage());
        }
        
        // Create client (Join the server and start the client thread).
        try {
            Client client = new Client(ipAddress, playerName.getText());
            new Thread(client).start();
        } catch (IOException ex) {
            errorPopup(ex.getMessage());
        }
    }
    
    @FXML
    public void createGame(Event e) {
        // Create the server and start the server thread.
        try {
            Server server = new Server();
            new Thread(server).start();
            
            // Create client (Join the server and start the client thread).
            try {
                Client client = new Client(InetAddress.getLocalHost(), playerName.getText());
                new Thread(client).start();
            } catch (IOException ex) {
                errorPopup(ex.getMessage());
            }
        } catch (IOException ex) {
            errorPopup(ex.getMessage());
        }
    }
    
    /**
     * Closes the server and client.
     */
    private void close() {
        if (server != null)
            server.close();
        if (client != null)
            client.close();
        System.exit(0);
    }
    
    /**
     * Creates a new stage (popup window) that displays the given error message.
     * 
     * @param message
     *            The error message.
     */
    private void errorPopup(String message) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.setWidth(300);
        stage.setHeight(100);
        Label text = new Label(message);
        text.setPadding(new Insets(15));
        text.setFont(new Font("System", 14.0));
        stage.setScene(new Scene(new VBox(text)));
        stage.setTitle("Ein Fehler ist aufgetreten!");
        stage.show();
    }
    
}
