package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;

public class IPAddressPopup extends Popup {

    @FXML
    private TextField ipAddressInput;
    @FXML
    private Button joinButton;
    @FXML
    private Button cancelButton;

    public IPAddressPopup(Window owner, EventHandler<TextInputEvent> joinAction) {
        super("Spiel beitreten", owner);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("IPAddressPopup.fxml"));
        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            root = new Label("Fehler beim Laden des Menüs!");
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add("view/style.css");

        // close window
        cancelButton.setOnAction(e -> this.close());
        // get text from text field and call the next event handler
        joinButton.setOnAction(e -> {
            joinAction.handle(new TextInputEvent(ipAddressInput.getText()));
            this.close();
        });

        this.setScene(scene);
        this.setWidth(300);
        this.setHeight(200);
        this.show();
    }
}
