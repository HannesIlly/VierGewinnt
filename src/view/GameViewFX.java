package view;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.VierGewinnt;

public class GameViewFX {

    private VierGewinnt game;

    private Canvas canvas;
    private GraphicsContext g;

    private static final int FIELD_SIZE = 75;
    private static final int FIELD_MARGIN = 5;

    public GameViewFX(VierGewinnt game, EventHandler<GameInputEvent> gameInputHandler) {
        this.game = game;

        int width = game.getBoard().getColumns() * (FIELD_SIZE + FIELD_MARGIN) - FIELD_MARGIN;
        int height = game.getBoard().getRows() * (FIELD_SIZE + FIELD_MARGIN) - FIELD_MARGIN;
        this.canvas = new Canvas(width, height);
        g = canvas.getGraphicsContext2D();

        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, e -> System.out.println("x: " + e.getX() + " y: " + e.getY()));
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int column = (int) e.getX() / (FIELD_SIZE + FIELD_MARGIN);
            int columnWithMargin = ((int) e.getX() + FIELD_MARGIN) / (FIELD_SIZE + FIELD_MARGIN);
            if (column == columnWithMargin) {
                GameInputEvent event = new GameInputEvent(column + 1);
                gameInputHandler.handle(event);
            }
        });
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void drawGame() {
        int width = game.getBoard().getColumns();
        int height = game.getBoard().getRows();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                drawField(x, y);
            }
        }
    }

    private void drawField(int column, int row) {
        drawField(column, row, false);
    }

    private void drawField(int column, int row, boolean highlight) {
        g.setStroke(Color.BLACK);
        int x = (FIELD_SIZE + FIELD_MARGIN) * column;
        int y = (int)canvas.getHeight() - (FIELD_SIZE + FIELD_MARGIN) * (row + 1) + FIELD_MARGIN;
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
        g.strokeOval(x, y, size, size);
    }

}
