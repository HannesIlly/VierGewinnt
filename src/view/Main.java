package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @FXML
    private TextField name;
    @FXML
    private TextField ipAddress;

    private Stage ipPopup;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent menu = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene contentScene = new Scene(menu);
        contentScene.getStylesheets().add("view/style.css");

        stage.setOnCloseRequest(e -> close());
        stage.setTitle("Vier Gewinnt");
        stage.setScene(contentScene);
        stage.show();
    }


    public void createNewGame() {
        System.out.println("Spiel erstellen");
    }

    public void joinGame() throws IOException {
        System.out.println("Spiel beitreten");
        ipPopup = new Stage(StageStyle.DECORATED);
        Parent menu;// = FXMLLoader.load(getClass().getResource("PopupIPAddress.fxml"));

        // load and set controller
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource("PopupIPAddress.fxml"));
        menu = loader.load();

        Scene scene = new Scene(menu);
        scene.getStylesheets().add("view/style.css");

        ipPopup.setTitle("Spiel beitreten");
        ipPopup.initModality(Modality.APPLICATION_MODAL);
        ipPopup.setWidth(300);
        ipPopup.setHeight(200);
        ipPopup.setScene(scene);
        ipPopup.show();
    }

    public void close() {
        // TODO create close operations
        System.exit(0);
    }

    public void joinGameIPAddress() {
        // TODO oder doch lieber durch Quelltext erzeugen?
        String playerName = (name != null) ? name.getText() : "Player";
        String ipAddress = this.ipAddress.getText();
        System.out.println(playerName + " hat ein Spiel betreten mit IP-Adresse: " + ipAddress);
        if (ipPopup != null) ipPopup.close();
        else System.out.println("Fehler");
    }
}
