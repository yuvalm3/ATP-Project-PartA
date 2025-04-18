package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    private Maze maze;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getInitialState() {
        return new MazeState(maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(maze.getGoalPosition());
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> neighbors = new ArrayList<>();
        MazeState current = (MazeState) s;
        int row = current.getPosition().getRowIndex();
        int col = current.getPosition().getColumnIndex();
        int rows = maze.getRows();
        int cols = maze.getColumns();

        // תנועות אפשריות: למעלה, למטה, שמאלה, ימינה וכן תנועות אלכסוניות.
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                // נניח ש-0 מסמן מסלול (ולא קיר)
                if (maze.getValueAt(newRow, newCol) == 0) {
                    neighbors.add(new MazeState(new Position(newRow, newCol)));
                }
            }
        }
        return neighbors;
    }
}
