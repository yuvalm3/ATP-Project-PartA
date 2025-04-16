package algorithms.search;

import java.util.ArrayList;

public class Solution {
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
