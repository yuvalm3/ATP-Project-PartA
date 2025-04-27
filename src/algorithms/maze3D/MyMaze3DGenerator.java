package algorithms.maze3D;

import java.util.BitSet;
import java.util.SplittableRandom;

/**
 * 3-D maze generator – iterative back-tracker, BitSet packed, tuned for speed
 * Start and goal positions are randomly selected on the outer border.
 */
public class MyMaze3DGenerator extends AMaze3DGenerator {
    private static final SplittableRandom RNG = new SplittableRandom();

    /* 6 neighbour directions (Δd,Δr,Δc) */
    private static final int[][] DIR = {
            {-1,0,0}, {1,0,0},
            {0,-1,0}, {0,1,0},
            {0,0,-1}, {0,0,1}
    };

    @Override
    public Maze3D generate(int depth, int rows, int cols) {
        if (depth <= 0 || rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("positive dimensions required");

        int D = depth * 2 + 1;
        int R = rows  * 2 + 1;
        int C = cols  * 2 + 1;
        int totalBits = D * R * C;

        // initialize all walls
        BitSet map = new BitSet(totalBits);
        map.set(0, totalBits);

        // carve all logical cell centers
        for (int d = 0; d < depth; d++)
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++)
                    map.clear(flatIndex(d*2+1, r*2+1, c*2+1, R, C));

        // visited set for logical cells
        BitSet visited = new BitSet(depth * rows * cols);
        int[] stack = new int[depth * rows * cols * 3];
        int top = 0;

        // push random start logical cell
        int sd = RNG.nextInt(depth), sr = RNG.nextInt(rows), sc = RNG.nextInt(cols);
        push(stack, sd, sr, sc, top); top += 3;
        visited.set(cellId(sd, sr, sc, rows, cols));

        int[] buf = new int[18];
        while (top > 0) {
            int cc = stack[--top];
            int cr = stack[--top];
            int cd = stack[--top];

            int nCnt = 0;
            for (int[] d : DIR) {
                int nd = cd + d[0], nr = cr + d[1], nc = cc + d[2];
                if (nd >= 0 && nd < depth && nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && !visited.get(cellId(nd, nr, nc, rows, cols))) {
                    buf[nCnt*3]   = nd;
                    buf[nCnt*3+1] = nr;
                    buf[nCnt*3+2] = nc;
                    nCnt++;
                }
            }
            if (nCnt == 0) continue;

            push(stack, cd, cr, cc, top); top += 3;
            int pick = RNG.nextInt(nCnt);
            int nd = buf[pick*3], nr = buf[pick*3+1], nc = buf[pick*3+2];

            // carve wall between cells
            map.clear(flatIndex(cd+nd+1, cr+nr+1, cc+nc+1, R, C));

            visited.set(cellId(nd, nr, nc, rows, cols));
            push(stack, nd, nr, nc, top); top += 3;
        }

        // choose random start & goal on border
        Position3D startPos = randomBorderPosition(depth, rows, cols, D, R, C);
        Position3D goalPos;
        do {
            goalPos = randomBorderPosition(depth, rows, cols, D, R, C);
        } while (goalPos.equals(startPos));

        // carve openings
        map.clear(flatIndex(startPos.getDepthIndex(), startPos.getRowIndex(), startPos.getColumnIndex(), R, C));
        map.clear(flatIndex(goalPos.getDepthIndex(),  goalPos.getRowIndex(),  goalPos.getColumnIndex(),  R, C));

        return new Maze3D(depth, rows, cols, map, startPos, goalPos);
    }

    // generate a random Position3D on the outer border (cell centers only)
    private static Position3D randomBorderPosition(int depth, int rows, int cols,
                                                   int D, int R, int C) {
        int axis = RNG.nextInt(3);
        boolean minSide = RNG.nextBoolean();
        int d, r, c;
        switch (axis) {
            case 0: // depth face
                d = minSide ? 0 : D-1;
                r = 1 + 2 * RNG.nextInt(rows);
                c = 1 + 2 * RNG.nextInt(cols);
                break;
            case 1: // row face
                r = minSide ? 0 : R-1;
                d = 1 + 2 * RNG.nextInt(depth);
                c = 1 + 2 * RNG.nextInt(cols);
                break;
            default: // column face
                c = minSide ? 0 : C-1;
                d = 1 + 2 * RNG.nextInt(depth);
                r = 1 + 2 * RNG.nextInt(rows);
        }
        return new Position3D(d, r, c);
    }

    private static int flatIndex(int d, int r, int c, int R, int C) {
        return (d * R + r) * C + c;
    }

    private static int cellId(int d, int r, int c, int rows, int cols) {
        return (d * rows + r) * cols + c;
    }

    private static void push(int[] stack, int d, int r, int c, int top) {
        stack[top]   = d;
        stack[top+1] = r;
        stack[top+2] = c;
    }
}
