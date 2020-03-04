package net;

import model.VierGewinnt;
import util.action.*;

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
     * The connection to the server.
     */
    private Socket connection;
    /**
     * The name of the client/player.
     */
    private String name;
    /**
     * The simulation of the game "vier gewinnt".
     */
    private VierGewinnt game;
    /**
     * The {@link Runnable} that can read the {@link InputStream} and decodes the data into actions.
     */
    private ActionInputDecoder in;
    /**
     * This encoder can print actions (encoded) to the given {@link OutputStream}.
     */
    private ActionOutputEncoder out;
    /*
     * Indicates if the client should stop running.
     */
    private boolean isClosed;
    
    /**
     * Creates a new client that connects to a server and manages data transfer.
     * 
     * @param address
     *            The ip-address of the server.
     * @param port
     *            The port of the server.
     * @param name
     *            The name of the client.
     * @throws IOException
     *             If an exception occurs when creating the connection.
     */
    public Client(InetAddress address, int port, String name) throws IOException {
        this.name = name;
        connection = new Socket(address, port);
        out = new ActionOutputEncoder(connection.getOutputStream());
        in = new ActionInputDecoder(connection.getInputStream());
        
        NewPlayerAction sendName = new NewPlayerAction(name);
        out.send(sendName);
        
        new Thread(in).start();
    }
    
    /**
     * Creates a new client that connects to a server and manages data transfer.
     * 
     * @param address
     *            The ip-address of the server.
     * @param name
     *            The name of the client.
     * @throws IOException
     *             If an exception occurs when creating the connection.
     */
    public Client(InetAddress address, String name) throws IOException {
        this(address, 46841, name);
    }
    
    @Override
    public void run() {
        Action currentAction;
        while (!isClosed) {
            // XXX continue here
            if ((currentAction = in.getAction()) != null) {
                switch (currentAction.getType()) {
                case newPlayer:
                    NewPlayerAction newPlayerAction = (NewPlayerAction) currentAction;
                    newPlayerAction.getName();
                    break;
                case put:
                    PutAction putAction = (PutAction) currentAction;
                    break;
                case newGame:
                    NewGameAction newGameAction = (NewGameAction) currentAction;
                    break;
                case exit:
                    ExitAction exitAction = (ExitAction) currentAction;
                    break;
                case undo:
                    break;
                case message:
                    break;
                case error:
                    break;
                default:
                    throw new IllegalArgumentException("Illegal action type. type = " + currentAction.getType());
                }
            }
        }
    }
    
    /**
     * Sends a close message to all clients and closes this client handler.
     */
    public void close() {
        Action close = new ExitAction("Server", ExitAction.PLAYER_EXIT);
        out.send(close);
        isClosed = true;
    }

    public VierGewinnt getGame() {
        return this.game;
    }
}
