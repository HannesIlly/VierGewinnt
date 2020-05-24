package model.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    public static final int DEFAULT_PORT = 46841;
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
        this(DEFAULT_PORT);
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
        isClosed = true;

        clientHandler.close();
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
