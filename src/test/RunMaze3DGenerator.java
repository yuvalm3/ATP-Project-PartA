package test;

import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;

public class RunMaze3DGenerator {
    public static void main(String[] args) {
        IMaze3DGenerator gen = new MyMaze3DGenerator();
        long t = gen.measureAlgorithmTimeMillis(500,500,500);
        System.out.println("3D Maze gen time (ms): " + t);
        Maze3D mz = gen.generate(10,10,10);
        // Print first 3 layers
        for(int d=0; d<3; d++){
            System.out.println("Layer " + d + ":");
            mz.printLayer(d);
        }
    }
}
