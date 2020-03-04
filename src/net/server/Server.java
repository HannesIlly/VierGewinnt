package net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    /**
     * The server that can accept incoming connection attempts.
     */
    private ServerSocket server;
    /**
     * The {@link Runnable} that will manage the data transfer and calculations.
     */
    private ClientHandler clientHandler;
    /**
     * Indicates if the server should stop running.
     */
    private boolean isClosed;
    
    /**
     * Creates a new server with a server socket and a client handler that manages the calculations.
     * 
     * @param port
     *            The port on which the server is opened.
     * @throws IOException
     *             If an {@link IOException} occurs when creating the server socket.
     */
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        clientHandler = new ClientHandler();
        
        new Thread(clientHandler).start();
    }
    
    /**
     * Creates a new server with a server socket and a client handler that manages the calculations.
     * 
     * @throws IOException
     *             If an {@link IOException} occurs when creating the server socket.
     */
    public Server() throws IOException {
        server = new ServerSocket(46841);
        clientHandler = new ClientHandler();
        
        new Thread(clientHandler).start();
    }
    
    @Override
    public void run() {
        while (!isClosed) {
            try {
                Socket socket = server.accept();
                clientHandler.addConnection(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Indicates to the server, that it should stop.
     */
    public void close() {
        clientHandler.close();
        isClosed = true;
    }
    
}
