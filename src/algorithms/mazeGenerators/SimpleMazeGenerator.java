package algorithms.mazeGenerators;

import java.util.Random;

/**
 * A simple maze generator - ensures at least one path.
 * Adds extra paths randomly for multiple possible solutions.
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    private final Random random = new Random();

    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 0 || columns <= 0)
            throw new IllegalArgumentException("Rows and columns must be positive.");

        int[][] maze = new int[rows][columns];

        // Fill with 1s
        for (int[] row : maze) {
            java.util.Arrays.fill(row, 1);
        }

        // Create a  path from start to goal in zigzag - ensure a solution to the Maze
        int r = 0, c = 0;
        maze[r][c] = 0;
        while (c < columns - 1) {
            c++;
            maze[r][c] = 0;
        }
        while (r < rows - 1) {
            r++;
            maze[r][c] = 0;
        }

        // Randomly open other cells to allow multiple paths
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (maze[i][j] == 1 && random.nextDouble() < 0.3) { // 30% chance to open a cell
                    maze[i][j] = 0;
                }
            }
        }

        // Define start and exit positions
        Position start = new Position(0, 0);
        Position goal = new Position(rows - 1, columns - 1);

        // Return the Maze
        return new Maze(maze, start, goal);
    }
}
