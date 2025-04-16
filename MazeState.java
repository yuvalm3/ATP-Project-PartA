package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    private Position position;

    public MazeState(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof MazeState)) return false;
        MazeState other = (MazeState) obj;
        return position.equals(other.position);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
