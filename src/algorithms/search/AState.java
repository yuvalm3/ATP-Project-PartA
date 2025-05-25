package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    private static final long serialVersionUID = 1L;
    protected double cost;
    protected AState cameFrom;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }
}
