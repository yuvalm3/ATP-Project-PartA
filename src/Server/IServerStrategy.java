package Server;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * Strategy interface for handling a single client connection on the server.
 * Implementations should:
 *  - read the client’s request data from the provided InputStream,
 *  - process the request,
 *  - write the response to the provided OutputStream.
 */
public interface IServerStrategy {
    void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws Exception;
}
