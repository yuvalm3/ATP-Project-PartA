package algorithms.mazeGenerators;

/**
 * A maze generator that creates an empty maze - only empty cells (0).
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze with no walls at all.
     * All cells will be set to 0.
     *
     * @return a Maze object representing this specific Maze.
     * @throws IllegalArgumentException if rows or columns are not positive (automatically by Java).
     */
    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Rows and columns must be positive.");
        }
        // All cells default to 0 in Java.
        int[][] mazeMatrix = new int[rows][columns];

        Position start = new Position(0, 0);
        Position goal = new Position(rows - 1, columns - 1);

        return new Maze(mazeMatrix, start, goal);
    }
}
