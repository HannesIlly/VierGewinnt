package net.server;

import util.action.Action;
import util.action.ActionInputDecoder;
import util.action.ActionOutputEncoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * This class is used to store connection information for input and output. It provides a {@link ActionInputDecoder} and
 * {@link ActionOutputEncoder} to transfer coded command data.
 *
 * @author Hannes Illy
 */
public class ServerConnection {
    private Socket connection = null;

    private static int connectionCounter = 0;
    private int connectionNumber;
    private String name;

    private ActionInputDecoder in = null;
    private ActionOutputEncoder out = null;

    /**
     * Creates a new ServerConnection with the given socket and assigns a connection number.
     *
     * @param connection the socket that represents this connection
     * @throws IOException if there occurs an exception when creating the ActionInputDecoder and ActionOutputEncoder.
     */
    public ServerConnection(Socket connection) throws IOException {
        // set number and name
        this();
        // set the socket and input/output
        this.setSocket(connection);
    }

    /**
     * Creates a new ServerConnection and assigns a number to it.
     */
    public ServerConnection() {
        // assign connection number and count up
        this.connectionNumber = connectionCounter++;
        this.name = "Connection-" + this.connectionNumber;
    }

    /**
     * Gets the number associated with this connection.
     *
     * @return the connection number
     */
    public int getConnectionNumber() {
        return this.connectionNumber;
    }

    /**
     * Gets the {@link Socket} of this connection, or {@code null} if the socket isn't set. Check {@code isActive()} to
     * see if the socket connection is set.
     *
     * @return the socket of this connection, or {@code null}
     */
    public Socket getSocket() {
        return this.connection;
    }

    /**
     * Sets the socket of this connection and creates the {@link BufferedReader} and {@link BufferedWriter}.
     *
     * @param socket the connection socket
     * @throws IOException if the {@link BufferedReader} or {@link BufferedWriter} couldn't be created
     */
    public void setSocket(Socket socket) throws IOException {
        this.connection = socket;
        this.in = new ActionInputDecoder(socket.getInputStream());
        new Thread(in).start(); // start the reader
        this.out = new ActionOutputEncoder(socket.getOutputStream());
    }

    /**
     * Sets the name of this connection's owner
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this connection's owner
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the connection is active, which means that a socket is associated with it and its input stream is open.
     *
     * @return If the connection is active or not.
     */
    public boolean isActive() {
        return connection != null && !connection.isClosed() && in != null && in.isActive();
    }

    /**
     * Reads an viergewinnt.util.action from the client. If there is no new viergewinnt.util.action the returned viergewinnt.util.action will be {@code null}.
     *
     * @return The viergewinnt.util.action, that is received from the client or {@code null} if there is none.
     */
    public Action readAction() {
        return this.in.getAction();
    }

    /**
     * Writes an viergewinnt.util.action to the client.
     *
     * @param action The viergewinnt.util.action that is sent to the client.
     */
    public void writeAction(Action action) {
        this.out.send(action);
    }

    /**
     * Closes the underlying streams.
     */
    public void close() {
        in.close();
        out.close();
    }
}
