import algorithms.mazeGenerators.*;


public class Main {
    public static void main(String[] args) {
        IMazeGenerator generator = new EmptyMazeGenerator();
        Maze maze = generator.generate(5, 5);
        maze.print();

        long time = generator.measureAlgorithmTimeMillis(5, 5);
        System.out.println("Time to generate: " + time + "ms");
    }
}
