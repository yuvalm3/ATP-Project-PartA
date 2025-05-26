package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;
import java.util.Arrays;

/**
 * Reads a Maze, caches/solves it, and returns Solution,
 * using the same handshake fix (OOS → OIS).
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void applyStrategy(InputStream inFromClient,
                              OutputStream outToClient) throws Exception {
        // first send header_server
        ObjectOutputStream oos = new ObjectOutputStream(outToClient);
        oos.flush();
        System.out.println("[INFO] Solver server accepted client");

        // read maze from client
        ObjectInputStream ois = new ObjectInputStream(inFromClient);
        Maze maze = (Maze) ois.readObject();

        // build cache path based on maze uniqueness
        byte[] mazeBytes = maze.toByteArray();
        int hash = Arrays.hashCode(mazeBytes);
        String tmpDir = System.getProperty("java.io.tmpdir");
        File cacheFile = new File(tmpDir, "maze_solution_" + hash + ".cache");

        Solution sol;
        if (cacheFile.exists()) {
            // load existing solution
            try (ObjectInputStream fin = new ObjectInputStream(new FileInputStream(cacheFile))) {
                sol = (Solution) fin.readObject();
            }
        } else {
            // solve and cache
            String searchAlg = Configurations.getInstance().getProperty("mazeSearchingAlgorithm");
            @SuppressWarnings("unchecked")
            Class<? extends ISearchingAlgorithm> clazz =
                    (Class<? extends ISearchingAlgorithm>) Class
                            .forName("algorithms.search." + searchAlg);
            ISearchingAlgorithm searcher = clazz.getDeclaredConstructor().newInstance();

            long startTime = System.currentTimeMillis();
            sol = searcher.solve(new SearchableMaze(maze));
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("Maze solved in " + duration + " ms");

            try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(cacheFile))) {
                fout.writeObject(sol);
            }
        }

        // send solution to client
        oos.writeObject(sol);
        oos.flush();
        System.out.println("Solution sent to client.");
    }
}
