package algorithms.mazeGenerators;

/**
 * Represents a 2D maze using a grid of integers.
 * 0 is a free cell
 * 1 is a wall
 */
public class Maze {
    private final int[][] mazeMatrix;
    private final Position startPosition;
    private final Position goalPosition;

    /**
     * Constructor - constructs a new Maze object.
     * @param mazeMatrix          an integers array representing the maze
     * @param startPosition the starting point in the maze
     * @param goalPosition  the goal (exit) point in the maze
     */
    public Maze(int[][] mazeMatrix, Position startPosition, Position goalPosition) {
        if (mazeMatrix == null || startPosition == null || goalPosition == null)
            throw new IllegalArgumentException("Null parameters.");
        this.mazeMatrix = mazeMatrix;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
    }

    public int getRows() {
        return mazeMatrix.length;
    }

    public int getColumns() {
        return mazeMatrix[0].length;
    }

    /**
     * Returns the value of a specified cell.
     * @return 0 if the cell is a path, 1 if it's a wall
     * @throws IndexOutOfBoundsException if indices are out of bounds (by Java, I override it for clarification
     */
    public int getValueAt(int row, int col) {
        if (row < 0 || row >= mazeMatrix.length || col < 0 || col >= mazeMatrix[0].length) {
            throw new IndexOutOfBoundsException("Invalid cell coordinates: (" + row + "," + col + ")");
        }
        return mazeMatrix[row][col];
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Prints the maze
     */
    public void print() {
        for (int r = 0; r < mazeMatrix.length; r++) {
            for (int c = 0; c < mazeMatrix[0].length; c++) {
                if (r == startPosition.getRowIndex() && c == startPosition.getColumnIndex()) {
                    System.out.print("S");
                } else if (r == goalPosition.getRowIndex() && c == goalPosition.getColumnIndex()) {
                    System.out.print("E");
                } else {
                    System.out.print(mazeMatrix[r][c] == 1 ? "█" : " ");
                }
            }
            System.out.println();
        }
    }
}

