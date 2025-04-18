package algorithms.maze3D;

public class Position3D {
    private final int depth, row, column;

    public Position3D(int depth, int row, int column) {
        this.depth  = depth;
        this.row    = row;
        this.column = column;
    }

    public int getDepthIndex()  { return depth; }
    public int getRowIndex()    { return row; }
    public int getColumnIndex() { return column; }

    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + column + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position3D)) return false;
        Position3D p = (Position3D) o;
        return depth==p.depth && row==p.row && column==p.column;
    }

    @Override
    public int hashCode() {
        return 31 * (31 * depth + row) + column;
    }
}
