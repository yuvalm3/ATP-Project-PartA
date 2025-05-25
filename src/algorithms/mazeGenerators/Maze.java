package algorithms.mazeGenerators;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Represents a 2D maze using a grid of integers.
 * 0 is a free cell
 * 1 is a wall
 */
public class Maze implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int[][] mazeMatrix;
    private final Position startPosition;
    private final Position goalPosition;

    /**
     * Constructor - constructs a new Maze object.
     * @param mazeMatrix      integer grid of the maze
     * @param startPosition   starting point in the maze
     * @param goalPosition    goal point in the maze
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

    public int getValueAt(int row, int col) {
        if (row < 0 || row >= getRows() || col < 0 || col >= getColumns()) {
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
     * Prints the maze to standard output, marking S and E for start/goal.
     */
    public void print() {
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getColumns(); c++) {
                if (r == startPosition.getRowIndex() && c == startPosition.getColumnIndex()) {
                    System.out.print("S");
                } else if (r == goalPosition.getRowIndex() && c == goalPosition.getColumnIndex()) {
                    System.out.print("E");
                } else {
                    System.out.print(mazeMatrix[r][c] == 1 ? "1" : "0");
                }
            }
            System.out.println();
        }
    }

    /**
     * Converts this maze to a byte array including dimensions, start/goal, and matrix.
     * Format: [rows(int)][cols(int)][startRow(int)][startCol(int)][goalRow(int)][goalCol(int)][matrix bytes...]
     * @return byte[] representation of the maze
     */
    public byte[] toByteArray() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(getRows());
            dos.writeInt(getColumns());
            dos.writeInt(startPosition.getRowIndex());
            dos.writeInt(startPosition.getColumnIndex());
            dos.writeInt(goalPosition.getRowIndex());
            dos.writeInt(goalPosition.getColumnIndex());
            for (int r = 0; r < getRows(); r++) {
                for (int c = 0; c < getColumns(); c++) {
                    dos.writeByte(mazeMatrix[r][c]);
                }
            }
            dos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error converting maze to byte array", e);
        }
    }

    /**
     * Constructs a Maze from its byte-array representation.
     * Expects the format produced by toByteArray().
     * @param data byte[] to parse
     */
    public Maze(byte[] data) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             DataInputStream dis = new DataInputStream(bais)) {
            int rows = dis.readInt();
            int cols = dis.readInt();
            Position start = new Position(dis.readInt(), dis.readInt());
            Position goal = new Position(dis.readInt(), dis.readInt());

            int[][] matrix = new int[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    matrix[r][c] = dis.readByte();
                }
            }
            this.mazeMatrix = matrix;
            this.startPosition = start;
            this.goalPosition = goal;
        } catch (IOException e) {
            throw new RuntimeException("Error building maze from byte array", e);
        }
    }
}
