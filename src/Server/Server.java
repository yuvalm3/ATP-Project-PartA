package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * General-purpose server using a fixed thread pool,
 * delegates each client connection to the given IServerStrategy.
 */
public class Server {
    private final int port;
    private final int threadPoolSize;
    private final IServerStrategy strategy;

    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private ExecutorService pool;

    /**
     * @param port           TCP port to bind
     * @param threadPoolSize number of threads in the pool
     * @param strategy       how to handle each client connection
     */
    public Server(int port, int threadPoolSize, IServerStrategy strategy) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.strategy = strategy;
    }

    /**
     * Starts the server: opens the socket and begins accepting clients in a daemon thread.
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            throw new RuntimeException("Failed to bind to port " + port, e);
        }

        pool = Executors.newFixedThreadPool(threadPoolSize);
        running = true;

        Thread acceptThread = new Thread(() -> {
            while (running && !serverSocket.isClosed()) {
                try {
                    Socket client = serverSocket.accept();
                    System.out.println("[INFO] Client accepted: " + client);
                    pool.submit(() -> handleClient(client));
                } catch (IOException e) {
                    if (running) {
                        e.printStackTrace();
                    }
                }
            }
        });

        acceptThread.setDaemon(true);
        acceptThread.start();
    }

    /**
     * Handles a single accepted client socket.
     */
    private void handleClient(Socket client) {
        try (Socket s = client;
             InputStream in = s.getInputStream();
             OutputStream out = s.getOutputStream()) {
            strategy.applyStrategy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the server: shuts down accept loop and thread pool.
     */
    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pool != null) {
            pool.shutdown();
        }
    }
}
