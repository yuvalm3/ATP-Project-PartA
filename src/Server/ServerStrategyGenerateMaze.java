package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import IO.MyCompressorOutputStream;

import java.io.*;
import java.util.Arrays;

/**
 * Server strategy for "generate maze":
 *  1. send header_server via ObjectOutputStream
 *  2. read int[2] dims via ObjectInputStream
 *  3. generate maze, compress into byte[]
 *  4. send compressed byte[] back via the same ObjectOutputStream
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient,
                              OutputStream outToClient) throws Exception {

        ObjectOutputStream oos = new ObjectOutputStream(outToClient);
        oos.flush();


        ObjectInputStream ois = new ObjectInputStream(inFromClient);
        int[] dims = (int[]) ois.readObject();
        int rows = dims[0], cols = dims[1];

        // Create maze + compress
        String genAlg = Configurations.getInstance().getProperty("mazeGeneratingAlgorithm");
        @SuppressWarnings("unchecked")
        Class<? extends IMazeGenerator> genClass =
                (Class<? extends IMazeGenerator>) Class
                        .forName("algorithms.mazeGenerators." + genAlg);
        IMazeGenerator generator = genClass.getDeclaredConstructor().newInstance();

        Maze maze = generator.generate(rows, cols);
        byte[] raw = maze.toByteArray();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (MyCompressorOutputStream cos = new MyCompressorOutputStream(baos)) {
            cos.write(raw);
        }
        byte[] compressed = baos.toByteArray();

        // send back in the same OOS
        oos.writeObject(compressed);
        oos.flush();
    }
}
