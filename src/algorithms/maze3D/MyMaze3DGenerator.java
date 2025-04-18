package algorithms.maze3D;

import java.util.*;

public class MyMaze3DGenerator extends AMaze3DGenerator {
    private final Random rand = new Random();

    @Override
    public Maze3D generate(int depth, int rows, int columns) {
        // expand logical grid into cells+walls
        int D = depth*2+1, R = rows*2+1, C = columns*2+1;
        int[][][] map = new int[D][R][C];
        for (int d=0; d<D; d++)
            for (int r=0; r<R; r++)
                Arrays.fill(map[d][r], 1);  // fill all walls

        boolean[][][] visited = new boolean[depth][rows][columns];
        List<int[]> walls = new ArrayList<>();

        // choose random starting cell
        int sd = rand.nextInt(depth), sr = rand.nextInt(rows), sc = rand.nextInt(columns);
        map[sd*2+1][sr*2+1][sc*2+1] = 0;
        visited[sd][sr][sc] = true;
        addWalls(sd, sr, sc, depth, rows, columns, walls, visited);

        // carve passages until walls list is empty
        while (!walls.isEmpty()) {
            int[] w = walls.remove(rand.nextInt(walls.size()));
            int d1=w[0],r1=w[1],c1=w[2], d2=w[3],r2=w[4],c2=w[5];
            if (visited[d1][r1][c1] ^ visited[d2][r2][c2]) {
                // remove wall between
                map[d1+d2+1][r1+r2+1][c1+c2+1] = 0;
                // move into unvisited cell
                int nd = visited[d1][r1][c1] ? d2 : d1;
                int nr = visited[d1][r1][c1] ? r2 : r1;
                int nc = visited[d1][r1][c1] ? c2 : c1;
                map[nd*2+1][nr*2+1][nc*2+1] = 0;
                visited[nd][nr][nc] = true;
                addWalls(nd, nr, nc, depth, rows, columns, walls, visited);
            }
        }

        Position3D start = new Position3D(1,1,1);
        Position3D goal  = new Position3D(D-2,R-2,C-2);
        map[goal.getDepthIndex()][goal.getRowIndex()][goal.getColumnIndex()] = 0;
        return new Maze3D(map, start, goal);
    }

    private void addWalls(int cd, int cr, int cc,
                          int maxD, int maxR, int maxC,
                          List<int[]> walls, boolean[][][] visited) {
        // six directions in 3D
        int[][] dirs = {{-1,0,0},{1,0,0},{0,-1,0},{0,1,0},{0,0,-1},{0,0,1}};
        for (int[] dir : dirs) {
            int nd = cd + dir[0], nr = cr + dir[1], nc = cc + dir[2];
            if (nd>=0&&nd<maxD && nr>=0&&nr<maxR && nc>=0&&nc<maxC && !visited[nd][nr][nc]) {
                walls.add(new int[]{cd,cr,cc, nd,nr,nc});
            }
        }
    }
}
