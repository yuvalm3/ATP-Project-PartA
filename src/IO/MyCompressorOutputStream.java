package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * דחיסה פשוטה ב־RLE: בכל רצף של ערך אחד כותבים [count][value]
 */
public class MyCompressorOutputStream extends OutputStream {
    private final OutputStream out;
    private int lastByte = -1, count = 0;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        if (lastByte == -1) {
            lastByte = b;
            count = 1;
        } else if (b == lastByte && count < 255) {
            count++;
        } else {
            // flush run
            out.write(count);
            out.write(lastByte);
            lastByte = b;
            count = 1;
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        for (byte val : b) write(val & 0xFF);
    }

    @Override
    public void close() throws IOException {
        if (count > 0) {
            out.write(count);
            out.write(lastByte);
        }
        out.close();
    }
}

