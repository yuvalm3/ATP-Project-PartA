package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import IO.MyCompressorOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Reads two ints, generates maze via configured generator, compresses and returns bytes.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void apply(InputStream in, OutputStream out) throws Exception {
        DataInputStream din = new DataInputStream(in);
        int rows = din.readInt();
        int cols = din.readInt();

        String genAlg = Configurations.getInstance().getProperty("mazeGeneratingAlgorithm");
        IMazeGenerator generator = (IMazeGenerator) Class
                .forName("algorithms.mazeGenerators." + genAlg)
                .getDeclaredConstructor().newInstance();
        Maze maze = generator.generate(rows, cols);

        byte[] data = maze.toByteArray();
        try (MyCompressorOutputStream cos = new MyCompressorOutputStream(out)) {
            cos.write(data);
        }
    }
}