package algorithms.maze3D;

public class Maze3D {
    private final int[][][] map;
    private final Position3D start, goal;

    public Maze3D(int[][][] map, Position3D start, Position3D goal) {
        if (map == null || start == null || goal == null)
            throw new IllegalArgumentException("Null arguments not allowed");
        this.map = map;
        this.start = start;
        this.goal = goal;
    }

    public int getDepth()   { return map.length; }
    public int getRows()    { return map[0].length; }
    public int getColumns() { return map[0][0].length; }

    public int getValueAt(int d, int r, int c) {
        // check bounds
        if (d<0||d>=getDepth() || r<0||r>=getRows() || c<0||c>=getColumns())
            throw new IndexOutOfBoundsException();
        return map[d][r][c];
    }

    public Position3D getStartPosition() { return start; }
    public Position3D getGoalPosition()  { return goal; }

    // print one 2D layer of the maze
    public void printLayer(int layer) {
        System.out.println("Layer " + layer + ":");
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getColumns(); c++) {
                if (layer == start.getDepthIndex() &&
                        r     == start.getRowIndex()    &&
                        c     == start.getColumnIndex())
                    System.out.print("S");
                else if (layer == goal.getDepthIndex() &&
                        r     == goal.getRowIndex()      &&
                        c     == goal.getColumnIndex())
                    System.out.print("E");
                else
                    System.out.print(map[layer][r][c] == 1 ? "█" : " ");
            }
            System.out.println();
        }
    }
}
