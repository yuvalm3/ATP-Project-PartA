package algorithms.mazeGenerators;

import java.util.*;

/**
 * MyMazeGenerator - builds a maze using a tweaked version of Prim's algorithm.
 * The idea is to start with all walls (1), and slowly carve out paths (0)
 * in a way that ensures an interesting layout with twists and branches.
 *
 * The result is a maze of the same size as the input,
 * with start and goal points randomly placed on the outer frame,
 * and a guaranteed valid path between them.
 */
public class MyMazeGenerator extends AMazeGenerator {
    private final Random random = new Random();

    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows and columns must be positive");
        }

        int[][] maze = new int[rows][cols];
        for (int[] row : maze) Arrays.fill(row, 1); // Fill with walls

        boolean[][] visited = new boolean[rows][cols];
        List<int[]> frontier = new ArrayList<>();
        //int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Choose a random start cell on the frame
        List<Position> border = getBorderPositions(rows, cols);
        Position start = border.get(random.nextInt(border.size()));
        int sr = start.getRowIndex(), sc = start.getColumnIndex();

        maze[sr][sc] = 0;
        visited[sr][sc] = true;
        addFrontier(sr, sc, visited, frontier, rows, cols); // Add the walls of the cell to the wall list.

        int openCount = 1;
        int targetOpen = (rows * cols) / 2; // We want ~50% open to make an interesting maze

        // While loop according to Prim's algorithm, but with a twist:
        // Only open cells if they have <= 1 open neighbor (prevents the case that all the cells become 0)
        while (!frontier.isEmpty() && openCount < targetOpen) {
            int[] edge = frontier.remove(random.nextInt(frontier.size()));
            int r1 = edge[0], c1 = edge[1], r2 = edge[2], c2 = edge[3];

            if (visited[r2][c2] && !visited[r1][c1]) {
                r1 = edge[2]; c1 = edge[3];
                r2 = edge[0]; c2 = edge[1];
            }

            if (!visited[r2][c2] && countOpenNeighbors(r2, c2, maze, rows, cols) <= 1) {
                visited[r2][c2] = true;
                maze[r2][c2] = 0;
                openCount++;
                addFrontier(r2, c2, visited, frontier, rows, cols);
            }
        }

        // Choose exit point on frame that is not the start, and also open - ensure valid solution
        List<Position> openBorder = getOpenBorderPositions(maze);
        if (openBorder.isEmpty()) {
            throw new IllegalStateException("No open border cells available to choose goal");
        }
        Position goal;
        do {
            goal = openBorder.get(random.nextInt(openBorder.size()));
        } while (goal.equals(start));

        return new Maze(maze, start, goal);
    }

    /**
     * Adds all the unvisited neighbors of a cell to the frontier list.
     *
     * @param r         row index
     * @param c         column index
     * @param visited   matrix of visited cells
     * @param frontier  list where we store potential walls to break
     * @param rows      maze height
     * @param cols      maze width
     */
    private void addFrontier(int r, int c, boolean[][] visited,
                             List<int[]> frontier, int rows, int cols) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc]) {
                frontier.add(new int[]{r, c, nr, nc});
            }
        }
    }

    /**
     * Counts how many neighbors of a cell are already open.
     *
     * @param r     current row
     * @param c     current column
     * @param maze  the maze grid
     * @param rows  maze height
     * @param cols  maze width
     * @return number of neighbors with value 0
     */
    private int countOpenNeighbors(int r, int c, int[][] maze, int rows, int cols) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int count = 0;
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && maze[nr][nc] == 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets all the positions on the frame of the maze.
     */
    private List<Position> getBorderPositions(int rows, int cols) {
        List<Position> positions = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            positions.add(new Position(0, c));
            positions.add(new Position(rows - 1, c));
        }
        for (int r = 1; r < rows - 1; r++) {
            positions.add(new Position(r, 0));
            positions.add(new Position(r, cols - 1));
        }
        return positions;
    }

    /**
     * Gets all the frame positions that are open (0) so we can choose exit point from them.
     */
    private List<Position> getOpenBorderPositions(int[][] maze) {
        int rows = maze.length, cols = maze[0].length;
        List<Position> positions = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            if (maze[0][c] == 0) positions.add(new Position(0, c));
            if (maze[rows - 1][c] == 0) positions.add(new Position(rows - 1, c));
        }
        for (int r = 1; r < rows - 1; r++) {
            if (maze[r][0] == 0) positions.add(new Position(r, 0));
            if (maze[r][cols - 1] == 0) positions.add(new Position(r, cols - 1));
        }
        return positions;
    }
}
