package model.net;

import javafx.event.EventHandler;
import javafx.event.EventType;
import model.action.*;
import view.event.GameInputEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Creates a new client, that manages communication to the server and simulates a game.
 * 
 * @author Hannes Illy
 */
public class Client implements Runnable {
    /**
     * The name of the client/player.
     */
    private String name;
    /**
     * The event handler that handles game input events.
     */
    private EventHandler<GameInputEvent> gameInputHandler;
    /**
     * The {@link Runnable} that can read the {@link InputStream} and decodes the data into actions.
     */
    private ActionInputDecoder in;
    /**
     * This encoder can print actions (encoded) to the given {@link OutputStream}.
     */
    private ActionOutputEncoder out;
    /**
     * Indicates whether the client should stop running.
     */
    private boolean isClosed;
    
    /**
     * Creates a new client that connects to a server and manages data transfer.
     *
     * @param address The ip-address of the server.
     * @param port    The port of the server.
     * @param name    The name of the client.
     * @throws IOException If an exception occurs when creating the connection.
     */
    public Client(InetAddress address, int port, String name, EventHandler<GameInputEvent> gameInputHandler) throws IOException {
        this.name = name;
        this.gameInputHandler = gameInputHandler;

        Socket connection = new Socket(address, port);
        out = new ActionOutputEncoder(connection.getOutputStream());
        in = new ActionInputDecoder(connection.getInputStream());

        NewPlayerAction sendName = new NewPlayerAction(name);
        out.send(sendName);

        new Thread(in).start();
    }

    /**
     * Creates a new client that connects to a server and manages data transfer.
     *
     * @param address The ip-address of the server.
     * @param name    The name of the client.
     * @throws IOException If an exception occurs when creating the connection.
     */
    public Client(InetAddress address, String name, EventHandler<GameInputEvent> gameInputHandler) throws IOException {
        this(address, 46841, name, gameInputHandler);
    }

    @Override
    public void run() {
        Action currentAction;
        while (!isClosed) {
            if ((currentAction = in.getAction()) != null) {
                switch (currentAction.getType()) {
                    case newPlayer:
                        NewPlayerAction newPlayerAction = (NewPlayerAction) currentAction;
                        gameInputHandler.handle(new GameInputEvent(newPlayerAction.getName()));
                        break;
                    case put:
                        PutAction putAction = (PutAction) currentAction;
                        gameInputHandler.handle(new GameInputEvent(putAction.getColumn()));
                        break;
                    case newGame:
                        NewGameAction newGameAction = (NewGameAction) currentAction;
                        break;
                    case exit:
                        ExitAction exitAction = (ExitAction) currentAction;
                        break;
                    case message:
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal action type. type = " + currentAction.getType());
                }
            }
        }
    }

    public void send(GameInputEvent event) {
        EventType type = event.getEventType();
        if (type == GameInputEvent.PUT_PIECE) {
            out.send(new PutAction(event.getColumn(), 0)); // TODO piece number
        } else if (type == GameInputEvent.PLAYER_JOIN) {
            out.send(new NewPlayerAction(event.getPlayerName()));
        }
    }

    /**
     * Sends a close message to all clients and closes this client handler.
     */
    public void close() {
        Action close = new ExitAction(this.name, ExitAction.PLAYER_EXIT);
        out.send(close);
        isClosed = true;
    }

}
