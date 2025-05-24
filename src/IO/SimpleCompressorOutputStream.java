package IO;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * מימוש “דחיסה” טריוויאלי: כותב לאורך הבייטים, כולל אינפורמציה על מספר הבתים.
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private final DataOutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    @Override
    public void write(int b) throws IOException {
        out.writeByte(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        out.writeInt(b.length);
        out.write(b);
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
