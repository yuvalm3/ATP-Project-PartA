package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Reads Maze object, solves via configured searcher, and returns Solution object.
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void apply(InputStream in, OutputStream out) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(in);
        Maze maze = (Maze) ois.readObject();

        String searchAlg = Configurations.getInstance().getProperty("mazeSearchingAlgorithm");
        ISearchingAlgorithm searcher = (ISearchingAlgorithm) Class
                .forName("algorithms.search." + searchAlg)
                .getDeclaredConstructor().newInstance();
        Solution sol = searcher.solve(new SearchableMaze(maze));

        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(sol);
        oos.flush();
    }
}