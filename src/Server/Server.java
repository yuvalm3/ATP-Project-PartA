package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * שרת כללי עם ThreadPool שמשתמש ב־IServerStrategy לטיפול בלקוחות.
 */
public class Server {
    private final int port;
    private final int threadPoolSize;
    private final IServerStrategy strategy;
    private ServerSocket serverSocket;
    private ExecutorService pool;

    public Server(int port, int threadPoolSize, IServerStrategy strategy) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.strategy = strategy;
    }

    public void start() {
        pool = Executors.newFixedThreadPool(threadPoolSize);
        new Thread(() -> {
            try (ServerSocket ss = new ServerSocket(port, threadPoolSize)) {
                while (!ss.isClosed()) {
                    Socket client = ss.accept();
                    pool.submit(() -> {
                        try (var in = client.getInputStream();
                             var out = client.getOutputStream()) {
                            strategy.apply(in, out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pool != null) pool.shutdown();
    }
}
