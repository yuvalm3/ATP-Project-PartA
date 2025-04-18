package test;

import algorithms.maze3D.SearchableMaze3D;
import algorithms.search.*;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.Maze3D;

public class RunSearchOnMaze3D {
    public static void main(String[] args) {
        Maze3D maze = new MyMaze3DGenerator().generate(10,10,10);
        SearchableMaze3D sm = new SearchableMaze3D(maze);

        solve("BFS", sm, new BreadthFirstSearch());
        solve("DFS", sm, new DepthFirstSearch());
        solve("BestFS", sm, new BestFirstSearch());
    }

    private static void solve(String name, ISearchable domain, ISearchingAlgorithm alg) {
        Solution sol = alg.solve(domain);
        System.out.printf("%s: evaluated=%d, path length=%d%n",
                name, alg.getNumberOfNodesEvaluated(), sol.getSolutionPath().size());
    }
}
