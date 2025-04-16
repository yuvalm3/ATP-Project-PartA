package algorithms.mazeGenerators;

/**
 * Abstract class for maze generators.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Generates a new maze with the specified dimensions.
     * This method must be implemented by concrete subclasses - because it's 'abstract'.
     * @return an object of Maze Class.
     * @throws IllegalArgumentException if rows or columns are not positive (automatically by Java).
     */
    @Override
    public abstract Maze generate(int rows, int columns);

    /**
     * Measures the time (in milliseconds) it takes to generate a maze
     * @return the time in milliseconds it took to generate the maze - long instead of int.
     * @throws IllegalArgumentException if rows or columns are not positive (automatically by Java).
     *
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Rows and columns must be positive integers.");
        }
        long startTime = System.currentTimeMillis();
        // Call the method we want to measure
        generate(rows, columns);
        long endTime = System.currentTimeMillis();
        // Result
        return endTime - startTime;
    }
}
