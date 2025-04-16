package algorithms.mazeGenerators;

import java.util.*;

/**
 * A maze generator using the randomized version of Prim's algorithm.
 * Works on an expanded grid where each cell (0) is surrounded by walls (1).
 * This expanded grid takes the original size of the Maze (e.g. 5X5)
 * and expands it to 11X11 (5 * 2 + 1 = 11).
 * This method fits to the structure of Prim's algorithm.
 */
public class MyMazeGenerator extends AMazeGenerator {

    private final Random random = new Random();

    /**
     * Generates a maze using Prim's algorithm on an expanded grid.
     * @param rows    number of logical rows (not including the added walls)
     * @param columns number of logical columns (not including the added)
     * @return Maze object with start and goal points
     */
    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 0 || columns <= 0)
            throw new IllegalArgumentException("Rows and columns must be positive.");

        // The actual size of the Maze (each cell (0) is surrounded by walls (1))
        int genRows = rows * 2 + 1;
        int genCols = columns * 2 + 1;
        int[][] maze = new int[genRows][genCols];
        for (int[] row : maze)
            Arrays.fill(row, 1); // Start with all walls (1)

        boolean[][] visited = new boolean[rows][columns]; // If visited
        List<int[]> wallList = new ArrayList<>(); // Create Wall's list we'll use

        // Choose random starting cell
        int startR = random.nextInt(rows);
        int startC = random.nextInt(columns);
        maze[startR * 2 + 1][startC * 2 + 1] = 0;
        visited[startR][startC] = true;

        // Add the walls of starting cell to the Wall's list
        addWalls(startR, startC, rows, columns, wallList, visited);

        // According to Prim - while walls List not empty - keep creating the Maze
        while (!wallList.isEmpty()) {
            int[] wall = wallList.remove(random.nextInt(wallList.size()));

            int r1 = wall[0], c1 = wall[1];
            int r2 = wall[2], c2 = wall[3];

            boolean visited1 = visited[r1][c1];
            boolean visited2 = visited[r2][c2];

            // Check Prim condition: only one of the two cells is visited
            if (visited1 ^ visited2) {
                int wallRow = r1 + r2 + 1;
                int wallCol = c1 + c2 + 1;
                maze[wallRow][wallCol] = 0; // Break the wall

                // Activate the new cell to be part of the Maze and add its walls to the wall List
                if (!visited2) {
                    maze[r2 * 2 + 1][c2 * 2 + 1] = 0;
                    visited[r2][c2] = true;
                    addWalls(r2, c2, rows, columns, wallList, visited);
                } else {
                    maze[r1 * 2 + 1][c1 * 2 + 1] = 0;
                    visited[r1][c1] = true;
                    addWalls(r1, c1, rows, columns, wallList, visited);
                }
            }
        }

        // Set the start and exit points of the Maze
        Position start = new Position(1, 1);
        Position goal = new Position(genRows - 2, genCols - 2);
        maze[goal.getRowIndex()][goal.getColumnIndex()] = 0; // Activate exit point to 0

        return new Maze(maze, start, goal); // Return the Maze
    }

    /**
     * Adds all unvisited neighbor walls of a given cell to the wall list.
     *
     * @param currRow         current cell row
     * @param currCol         current cell column
     * @param rowsBound      total logical rows
     * @param colsBound      total logical columns
     * @param wallList  list of walls - represented as [r1, c1, r2, c2]
     * @param visited   visited grid (rows × cols)
     */
    private void addWalls(int currRow, int currCol, int rowsBound, int colsBound, List<int[]> wallList, boolean[][] visited) {
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        for (int[] dir : directions) {
            int nr = currRow + dir[0];
            int nc = currCol + dir[1];
            if (nr >= 0 && nr < rowsBound && nc >= 0 && nc < colsBound && !visited[nr][nc]) {
                wallList.add(new int[]{currRow, currCol, nr, nc});
            }
        }
    }
}

