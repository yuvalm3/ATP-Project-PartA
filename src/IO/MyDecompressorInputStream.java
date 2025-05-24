package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * הפענוח של RLE דחיסה מבוצע על ידי קריאת זוגות [count][value].
 */
public class MyDecompressorInputStream extends InputStream {
    private final InputStream in;
    private int remaining = 0, currentValue = -1;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        if (remaining == 0) {
            int cnt = in.read();
            if (cnt < 0) return -1;
            currentValue = in.read();
            if (currentValue < 0) return -1;
            remaining = cnt;
        }
        remaining--;
        return currentValue;
    }
}
