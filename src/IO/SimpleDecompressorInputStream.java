package IO;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * מפענח את ה-“דחיסה” הטריוויאלית של SimpleCompressor.
 */
public class SimpleDecompressorInputStream extends InputStream {
    private final DataInputStream in;
    private byte[] buffer = null;
    private int pos = 0;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = new DataInputStream(in);
    }

    @Override
    public int read() throws IOException {
        if (buffer == null) {
            int len = in.readInt();
            buffer = new byte[len];
            in.readFully(buffer);
        }
        if (pos >= buffer.length) return -1;
        return buffer[pos++] & 0xFF;
    }
}
