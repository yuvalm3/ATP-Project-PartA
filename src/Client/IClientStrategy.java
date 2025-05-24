package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Strategy interface for client request-response.
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
