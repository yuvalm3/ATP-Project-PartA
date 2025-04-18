package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int depth, int rows, int columns) {
        if (depth <= 0 || rows <= 0 || columns <= 0)
            throw new IllegalArgumentException("Dimensions must be positive");
        long start = System.currentTimeMillis();
        generate(depth, rows, columns);
        return System.currentTimeMillis() - start;
    }

    @Override
    public abstract Maze3D generate(int depth, int rows, int columns);
}
