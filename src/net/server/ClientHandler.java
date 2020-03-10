package net.server;

import model.VierGewinnt;
import util.action.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Creates a new client handler that will manage client communication and simulation of the game.
 *
 * @author Hannes Illy
 */
public class ClientHandler implements Runnable {
    /**
     * The connections for the players.
     */
    private ServerConnection[] connections;
    /**
     * The game simulation.
     */
    private VierGewinnt game;
    /**
     * Indicates to the thread whether to keep running or not.
     */
    private boolean isClosed;

    /**
     * Creates a new {@link Runnable}, that can execute the server game {@link VierGewinnt}.
     */
    public ClientHandler() {
        connections = new ServerConnection[2]; // two clients
        for (int i = 0; i < connections.length; i++) {
            connections[i] = new ServerConnection();
        }
        game = new VierGewinnt();
    }

    @Override
    public void run() {
        Action currentAction;
        int currentConnectionNumber;
        while (!isClosed) {
            for (ServerConnection c : connections) {
                synchronized (c) {
                    if (c.isActive()) {
                        currentConnectionNumber = c.getConnectionNumber();
                        if ((currentAction = c.readAction()) != null) {
                            switch (currentAction.getType()) {
                                case newPlayer:
                                    NewPlayerAction newPlayerAction = (NewPlayerAction) currentAction;
                                    connections[currentConnectionNumber].setName(newPlayerAction.getName());
                                    game.setPlayerName(currentConnectionNumber, newPlayerAction.getName());
                                    for (int i = 0; i < connections.length; i++) {
                                        if (i != currentConnectionNumber) {
                                            c.writeAction(currentAction);
                                        }
                                    }
                                    break;
                                case put:
                                    PutAction putAction = (PutAction) currentAction;
                                    if (game != null && game.placePiece(putAction.getColumn(), currentConnectionNumber + 1)) {
                                        for (int i = 0; i < connections.length; i++) {
                                            if (i != currentConnectionNumber) {
                                                c.writeAction(currentAction);
                                            }
                                        }
                                    } else {
                                        // TODO error
                                    }
                                    break;
                                case newGame:
                                    for (int i = 0; i < connections.length; i++) {
                                        if (i != currentConnectionNumber) {
                                            c.writeAction(currentAction);
                                        }
                                    }
                                    this.game.reset();
                                    break;
                                case exit:
                                    //ExitAction exitAction = (ExitAction) currentAction;
                                    for (int i = 0; i < connections.length; i++) {
                                        if (i != currentConnectionNumber) {
                                            c.writeAction(currentAction);
                                        }
                                    }
                                    this.close();
                                    break;
                                case message:
                                    break;
                                default:
                                    throw new IllegalArgumentException("Illegal action type. type = " + currentAction.getType());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Tries to add a socket to the server. Returns, if the attempt was successful.
     *
     * @param connection The socket that will be added.
     * @return If the socket could be added successfully.
     */
    public boolean addConnection(Socket connection) {
        synchronized (connections) {
            for (ServerConnection c : this.connections) {
                synchronized (c) {
                    if (!c.isActive()) {
                        try {
                            c.setSocket(connection);
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Sends a close message to all clients and closes this client handler.
     */
    public void close() {
        Action close = new ExitAction("Server", ExitAction.SERVER_CLOSED);
        for (ServerConnection c : connections) {
            // can be called from other threads, so it has to be thread-safe
            synchronized (c) {
                if (c.isActive()) {
                    c.writeAction(close);
                    c.close();
                }
            }
        }
        isClosed = true;
    }

}
