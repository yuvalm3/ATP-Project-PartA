**This repo contains parts A+B  of Football & Java project in src folder**


Overview

ATP Project is a multi-part Java assignment that implements a full maze platform: generating 2D and 3D mazes, solving them with classic search algorithms,
compressing and serializing the mazes, communicating with server components, and presenting a desktop GUI using JavaFX and MVVM. 
The code emphasizes SOLID design, testability, and clean packaging. 

Features

Maze Generation (Part A)
- IMazeGenerator hierarchy with EmptyMazeGenerator, SimpleMazeGenerator, and a custom MyMazeGenerator by prim's algorithm.
- Maze model with start/goal positions and printing utilities.
- Time benchmarking via measureAlgorithmTimeMillis. 
Search Algorithms
- BreadthFirstSearch, DepthFirstSearch, BestFirstSearch over a generic ISearchable.
- SearchableMaze adapter and Solution path output. 
- IMazeGenerator3D, Maze3D, Position3D, SearchableMaze3D. 
Testing 
JUnit 5 tests and runnable demos.

Compression & I/O (Part B)
- RLE-style compression streams: MyCompressorOutputStream, MyDecompressorInputStream (+ simple compressor variants).
- Maze.toByteArray() and byte-array constructor for persistence. 
Client–Server
- Servers: GenerateMaze and SolveSearchProblem strategies; client strategies to request mazes/solutions; caching to disk.
- Concurrency via thread pool. Demos. 
Configuration
- config.properties with threadPoolSize, mazeGeneratingAlgorithm, mazeSearchingAlgorithm. 

GUI (Part C)
- Desktop JavaFX app with MVVM architecture and event-driven programming
- Graph component reminder
- Styling and logging (Maven, Log4j2). 


Tech Stack

Language: Java 15

Desktop: JavaFX, MVVM

Build/Logging: Maven, Log4j2
