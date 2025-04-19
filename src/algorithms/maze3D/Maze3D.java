package algorithms.maze3D;

import java.util.BitSet;

public class Maze3D {
    private final BitSet bits;
    private final int D, R, C;         // expanded dims: D=2*depth+1, etc.
    private final Position3D start, goal;

    /**
     * @param depth   logical depth
     * @param rows    logical rows
     * @param columns logical columns
     * @param bits    bitset of size D*R*C, where bit==1→wall, bit==0→path
     */
    public Maze3D(int depth, int rows, int columns,
                  BitSet bits, Position3D start, Position3D goal) {
        this.D = depth*2 + 1;
        this.R = rows *2 + 1;
        this.C = columns*2 + 1;
        if (bits == null || bits.length() > D*R*C)
            throw new IllegalArgumentException("Invalid BitSet size");
        this.bits  = bits;
        this.start = start;
        this.goal  = goal;
    }

    public int getDepth()   { return D; }
    public int getRows()    { return R; }
    public int getColumns() { return C; }

    // compute flat index in BitSet
    private int idx(int d, int r, int c) {
        return (d * R + r) * C + c;
    }

    /** returns 0 for path, 1 for wall */
    public int getValueAt(int d, int r, int c) {
        if (d<0||d>=D||r<0||r>=R||c<0||c>=C)
            throw new IndexOutOfBoundsException();
        return bits.get(idx(d,r,c)) ? 1 : 0;
    }

    public Position3D getStartPosition() { return start; }
    public Position3D getGoalPosition()  { return goal; }

    /** print a single 2D slice */
    public void printLayer(int layer) {
        System.out.println("Layer " + layer + ":");
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (layer == start.getDepthIndex()
                        && r     == start.getRowIndex()
                        && c     == start.getColumnIndex()) {
                    System.out.print("S");
                } else if (layer == goal.getDepthIndex()
                        && r     == goal.getRowIndex()
                        && c     == goal.getColumnIndex()) {
                    System.out.print("E");
                } else {
                    System.out.print(getValueAt(layer, r, c) == 1 ? "█" : " ");
                }
            }
            System.out.println();
        }
    }
}
