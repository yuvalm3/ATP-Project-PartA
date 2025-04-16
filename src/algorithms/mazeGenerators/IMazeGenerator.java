package algorithms.mazeGenerators;

/**
 * An interface for maze generation algorithms.
 * Defines the methods that all maze generators must implement.
 */
public interface IMazeGenerator {

    /**
     * Generates a new maze with the specified number of rows and columns.
     * @return a newly generated Maze object
     * @throws IllegalArgumentException if rows or columns are not positive (automatically by Java).
     */
    Maze generate(int rows, int columns);

    /**
     * Measures the time it takes (in milliseconds) to generate a maze
     * @return the time in milliseconds.
     * @throws IllegalArgumentException if rows or columns are not positive (automatically by Java).
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}
