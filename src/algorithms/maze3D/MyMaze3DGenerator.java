package algorithms.maze3D;

import java.util.BitSet;
import java.util.SplittableRandom;

/** 3‑D maze generator – iterative back‑tracker, BitSet packed, tuned for speed */
public class MyMaze3DGenerator extends AMaze3DGenerator {
    private static final SplittableRandom RNG = new SplittableRandom();

    /* 6 neighbour directions (Δd,Δr,Δc) */
    private static final int[][] DIR = {
            {-1,0,0}, {1,0,0},
            {0,-1,0}, {0,1,0},
            {0,0,-1}, {0,0,1}
    };

    /* ---------- public API ---------- */
    @Override
    public Maze3D generate(int depth, int rows, int cols) {
        if (depth<=0 || rows<=0 || cols<=0)
            throw new IllegalArgumentException("positive dimensions required");

        /* expanded grid sizes */
        int D = depth*2 + 1;
        int R = rows *2 + 1;
        int C = cols *2 + 1;
        int totalBits = D * R * C;

        /* BitSet map : 1 = wall */
        BitSet map = new BitSet(totalBits);
        map.set(0, totalBits);

        /* carve all logical cell centres (set bit = 0) */
        for (int d=0; d<depth; d++)
            for (int r=0; r<rows; r++)
                for (int c=0; c<cols; c++)
                    map.clear(flatIndex(d*2+1, r*2+1, c*2+1, R, C));

        /* visited BitSet for logical cells */
        BitSet visited = new BitSet(depth*rows*cols);

        /* iterative DFS stack : flat int array, 3 ints per entry */
        int[] stack = new int[depth*rows*cols*3];
        int top = 0; // points to next free slot

        /* push random start cell */
        int sd = RNG.nextInt(depth),
                sr = RNG.nextInt(rows),
                sc = RNG.nextInt(cols);
        push(stack, sd, sr, sc, top);  top += 3;
        visited.set(cellId(sd,sr,sc, rows,cols));

        /* neighbour buffer (max 6 neighbours × 3 ints) */
        int[] buf = new int[18];

        /* ----- main loop ----- */
        while (top > 0) {
            /* pop current cell */
            int cc = stack[--top];
            int cr = stack[--top];
            int cd = stack[--top];

            /* collect unvisited neighbours */
            int nCnt = 0;
            for (int[] d : DIR) {
                int nd = cd+d[0], nr = cr+d[1], nc = cc+d[2];
                if (nd>=0&&nd<depth && nr>=0&&nr<rows && nc>=0&&nc<cols &&
                        !visited.get(cellId(nd,nr,nc, rows,cols))) {
                    buf[nCnt*3]   = nd;
                    buf[nCnt*3+1] = nr;
                    buf[nCnt*3+2] = nc;
                    nCnt++;
                }
            }
            if (nCnt == 0) continue;              // dead end

            /* push current back (branch point) */
            push(stack, cd, cr, cc, top);  top += 3;

            /* choose random neighbour */
            int pick = RNG.nextInt(nCnt);
            int nd = buf[pick*3],
                    nr = buf[pick*3+1],
                    nc = buf[pick*3+2];

            /* carve wall between current ↔ neighbour */
            map.clear(flatIndex(cd+nd+1, cr+nr+1, cc+nc+1, R, C));

            /* mark & push neighbour */
            visited.set(cellId(nd,nr,nc, rows,cols));
            push(stack, nd, nr, nc, top);  top += 3;
        }

        /* start & goal */
        map.clear(flatIndex(1,1,1, R,C));
        map.clear(flatIndex(D-2, R-2, C-2, R,C));

        return new Maze3D(depth, rows, cols, map,
                new Position3D(1,1,1),
                new Position3D(D-2, R-2, C-2));
    }

    /* ---------- helpers ---------- */

    private static int flatIndex(int d, int r, int c, int R, int C) {
        return (d * R + r) * C + c;
    }

    private static int cellId(int d, int r, int c, int rows, int cols) {
        return (d * rows + r) * cols + c;
    }

    private static void push(int[] stack, int d, int r, int c, int top) {
        stack[top  ] = d;
        stack[top+1] = r;
        stack[top+2] = c;
    }
}
