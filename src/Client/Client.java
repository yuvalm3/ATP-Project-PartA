package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Generic TCP client invoking IClientStrategy.
 */
public class Client {
    private final InetAddress host;
    private final int port;
    private final IClientStrategy strategy;

    public Client(InetAddress host, int port, IClientStrategy strategy) {
        this.host = host;
        this.port = port;
        this.strategy = strategy;
    }

    public void communicateWithServer() {
        try (Socket sock = new Socket(host, port);
             InputStream in = sock.getInputStream();
             OutputStream out = sock.getOutputStream()) {
            strategy.clientStrategy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
