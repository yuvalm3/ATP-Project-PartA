package algorithms.maze3D;

public interface IMaze3DGenerator {
    Maze3D generate(int depth, int rows, int columns);  // generate a new 3D maze
    long measureAlgorithmTimeMillis(int depth, int rows, int columns);  // measure generation time
}
