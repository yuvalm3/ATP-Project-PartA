package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Represents a position in a 2D maze using row and column indices.
 * Used for defining the start and goal Positions.
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int row;
    private final int column;

    /**
     * Constructor - constructs a new Position with two args.
     * @param row the row index
     * @param column the column index
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }


    /**
     * Override methods of Class 'Object'.
     */
    @Override
    public String toString() {
        return "{" + column + "," + row + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return row == other.row && column == other.column;
    }

    @Override
    public int hashCode() {
        return 31 * row + column;
    }
}
