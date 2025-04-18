package algorithms.maze3D;

import algorithms.search.AState;

public class Maze3DState extends AState {
    private final Position3D position;

    public Maze3DState(Position3D pos) {
        this.position = pos;
    }

    public Position3D getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Maze3DState)) return false;
        return position.equals(((Maze3DState)o).position);
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
