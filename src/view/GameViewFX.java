package view;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import model.VierGewinnt;

public class GameViewFX {
    
    private VierGewinnt game;
    
    public GameViewFX(VierGewinnt game) {
        this.game = game;
    }
    
    public Scene getScene() {
        TilePane gameBoard = new TilePane(Orientation.VERTICAL, 1, 1);
        gameBoard.setStyle("-fx-background-color: black;-fx-padding: 1;");
        
        int width = 100 * game.getBoard().getColumns() + 8;
        int height = 100 * game.getBoard().getRows() + 7;
        gameBoard.setPrefWidth(width);
        gameBoard.setMaxWidth(width);
        gameBoard.setMinWidth(width);
        
        gameBoard.setPrefHeight(height);
        gameBoard.setMaxHeight(height);
        gameBoard.setMinHeight(height);
        
        
        ImageView[][] boardImages = new ImageView[game.getBoard().getColumns()][game.getBoard().getRows()];
        for (int x = 0; x < boardImages.length; x++) {
            for (int y = boardImages[x].length - 1; y >= 0; y--) {
                switch (game.getBoard().getField(x, y)) {
                case 1:
                    boardImages[x][y] = new ImageView(new Image("resources/test/red.png"));
                    break;
                case 2:
                    boardImages[x][y] = new ImageView(new Image("resources/test/yellow.png"));
                    break;
                default:
                    boardImages[x][y] = new ImageView(new Image("resources/test/empty.png"));
                    break;
                }
                //boardImages[x][y].setOnMouseClicked(e -> ((ImageView) e.getSource()).setImage(new Image("resources/test/red.png")));
                gameBoard.getChildren().add(boardImages[x][y]);
            }
        }
        
        BorderPane container = new BorderPane();
        container.setTop(new VBox(new Label("Vier Gewinnt"), new HBox(new Label(game.getPlayers()[0]), new Label(" gegen "), new Label(game.getPlayers()[1]))));
        container.setCenter(gameBoard);
        return new Scene(container);
    }
}
