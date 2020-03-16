package viergewinnt.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import viergewinnt.model.VierGewinnt;
import viergewinnt.view.event.GameInputEvent;

public class GameViewFX {

    private VierGewinnt game;

    private Parent gameView;

    private Canvas canvas;
    private GraphicsContext g;

    private int hightlightColumn = -1;

    private static final int FIELD_SIZE = 75;
    private static final int FIELD_MARGIN = 5;

    public GameViewFX(VierGewinnt game, EventHandler<GameInputEvent> gameInputHandler, EventHandler<ActionEvent> quitGame) {
        this.game = game;

        // initialize canvas
        int width = game.getBoard().getColumns() * (FIELD_SIZE + FIELD_MARGIN) - FIELD_MARGIN;
        int height = game.getBoard().getRows() * (FIELD_SIZE + FIELD_MARGIN) - FIELD_MARGIN;
        this.canvas = new Canvas(width, height);
        g = canvas.getGraphicsContext2D();

        // create the game view Parent
        VBox root = new VBox();
        root.getStyleClass().add("container");
        Label title = new Label(game.getPlayers()[0] + " gegen " + game.getPlayers()[1]);
        title.getStyleClass().add("h3");
        root.getChildren().add(title);
        root.getChildren().add(canvas);
        Button quitGameButton = new Button("Spiel beenden");
        root.getChildren().add(quitGameButton);
        this.gameView = root;

        // set event handlers
        quitGameButton.setOnAction(quitGame);
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            if (!game.hasEnded()) {
                int column = (int) e.getX() / (FIELD_SIZE + FIELD_MARGIN);
                int columnWithMargin = ((int) e.getX() + FIELD_MARGIN) / (FIELD_SIZE + FIELD_MARGIN);
                if (column == columnWithMargin) {
                    hightlightColumn = column;
                } else {
                    hightlightColumn = -1;
                }
            } else {
                hightlightColumn = -1;
            }
            drawGame();
        });
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int column = (int) e.getX() / (FIELD_SIZE + FIELD_MARGIN);
            int columnWithMargin = ((int) e.getX() + FIELD_MARGIN) / (FIELD_SIZE + FIELD_MARGIN);
            if (column == columnWithMargin) {
                GameInputEvent event = new GameInputEvent(column + 1);
                gameInputHandler.handle(event);
                if (game.hasEnded()) {
                    hightlightColumn = -1;
                }
                drawGame();
            }
        });
    }

    /**
     * Gets the canvas of this game view. To display the game the getGameView() method should be used.
     *
     * @return The canvas on which the game board is drawn.
     */
    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * Gets the view element of this game.
     *
     * @return The {@link Parent} which contains the view on the game.
     */
    public Parent getGameView() {
        return this.gameView;
    }

    /**
     * Clears the canvas of all drawings.
     */
    private void clearCanvas() {
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawGame() {
        clearCanvas();

        int width = game.getBoard().getColumns();
        int height = game.getBoard().getRows();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == hightlightColumn) {
                    drawField(x, y, true);
                } else {
                    drawField(x, y);
                }
            }
        }

        // highlight winning pieces
        if (game.hasEnded()) {
            int[][] winningPieces = game.getWinningPieces();
            for (int[] piece : winningPieces) {
                drawField(piece[0], piece[1], true);
            }
        }
    }

    private void drawField(int column, int row) {
        drawField(column, row, false);
    }

    private void drawField(int column, int row, boolean highlight) {
        g.setStroke(Color.BLACK);
        int x = (FIELD_SIZE + FIELD_MARGIN) * column;
        int y = (int) canvas.getHeight() - (FIELD_SIZE + FIELD_MARGIN) * (row + 1) + FIELD_MARGIN;
        int size = FIELD_SIZE;
        switch (game.getBoard().getField(column, row)) {
            case 1:
                g.setFill(Color.RED);
                g.fillOval(x, y, size, size);
                break;
            case 2:
                g.setFill(Color.YELLOW);
                g.fillOval(x, y, size, size);
                break;
            default:
        }
        if (highlight) {
            g.strokeOval(x, y, size, size);
            g.strokeOval(x + 1, y + 1, size - 2, size - 2);
            g.strokeOval(x + 2, y + 2, size - 4, size - 4);
        } else {
            g.strokeOval(x, y, size, size);
        }
    }

}
