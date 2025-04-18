package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    private final Maze3D maze;

    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
    }

    @Override
    public AState getInitialState() {
        return new Maze3DState(maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new Maze3DState(maze.getGoalPosition());
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        Maze3DState cur = (Maze3DState)s;
        Position3D p = cur.getPosition();
        ArrayList<AState> neighbors = new ArrayList<>();

        int[][] dirs = {
                {-1,0,0},{1,0,0},
                {0,-1,0},{0,1,0},
                {0,0,-1},{0,0,1},
        };

        for (int[] d:dirs) {
            int nd = p.getDepthIndex()  + d[0],
                    nr = p.getRowIndex()    + d[1],
                    nc = p.getColumnIndex() + d[2];
            if (nd>=0&&nd<maze.getDepth() &&
                    nr>=0&&nr<maze.getRows() &&
                    nc>=0&&nc<maze.getColumns() &&
                    maze.getValueAt(nd,nr,nc)==0) {
                neighbors.add(new Maze3DState(
                        new Position3D(nd,nr,nc)));
            }
        }
        return neighbors;
    }
}
