package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Strategy pattern לשרת: מקבל InputStream ומחזיר התשובה ב־OutputStream.
 */
public interface IServerStrategy {
    void apply(InputStream in, OutputStream out) throws Exception;
}
