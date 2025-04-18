package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BestFirstSearchTest {

    private BestFirstSearch bfs;

    /**
     * Initializes a new instance of BestFirstSearch before each test.
     */
    @BeforeEach
    public void setUp() {
        bfs = new BestFirstSearch();
    }

    /**
     * Tests that getName() returns the correct name.
     */
    @Test
    public void testGetName() {
        String name = bfs.getName();
        assertEquals("Best First Search", name);
    }

    /**
     * Tests that solve() returns a valid solution on a simple maze without walls.
     */
    @Test
    public void testSolveSimpleMaze() {
        int[][] mazeMatrix = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        Position start = new Position(0, 0);
        Position goal = new Position(2, 2);
        Maze maze = new Maze(mazeMatrix, start, goal);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        Solution solution = bfs.solve(searchableMaze);

        assertNotNull(solution);
        assertFalse(solution.getSolutionPath().isEmpty());
    }

    /**
     * Tests that solve() throws an exception when input is null.
     */
    @Test
    public void testSolveWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> bfs.solve(null));
    }

    /**
     * Tests that numberOfNodesEvaluated is greater than 0 after solving the maze.
     */
    @Test
    public void testNumberOfNodesEvaluatedPositive() {
        int[][] mazeMatrix = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        Position start = new Position(0, 0);
        Position goal = new Position(4, 4);
        Maze maze = new Maze(mazeMatrix, start, goal);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        bfs.solve(searchableMaze);
        int evaluated = bfs.getNumberOfNodesEvaluated();

        assertTrue(evaluated > 0);
    }

    /**
     * Tests solve() when start and goal positions are the same position.
     */
    @Test
    public void testStartEqualsGoal() {
        int[][] mazeMatrix = {
                {0, 0},
                {0, 0}
        };
        Position SameStartSameGoal = new Position(0, 0);
        Maze maze = new Maze(mazeMatrix, SameStartSameGoal, SameStartSameGoal);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        Solution solution = bfs.solve(searchableMaze);

        assertNotNull(solution);
        assertEquals(1, solution.getSolutionPath().size());
    }

    /**
     * Tests solve() on a maze with no solution.
     */
    @Test
    public void testUnsolvableMaze() {
        int[][] mazeMatrix = {
                {0, 1, 0},
                {1, 1, 1},
                {0, 1, 0}
        };
        Position start = new Position(0, 0);
        Position goal = new Position(2, 2);
        Maze maze = new Maze(mazeMatrix, start, goal);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        Solution solution = bfs.solve(searchableMaze);

        assertNull(solution); // No solution - Null expected
    }
}
