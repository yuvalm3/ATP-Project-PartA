package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<AState> solutionPath;
    public Solution(ArrayList<AState> solutionPath) {
        this.solutionPath = solutionPath;
    }

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AState state : solutionPath) {
            sb.append(state.toString()).append(" -> ");
        }
        return sb.toString();
    }
}
