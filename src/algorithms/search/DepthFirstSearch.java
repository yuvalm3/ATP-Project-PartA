package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {
    private String name = "Depth First Search";

    @Override
    public Solution solve(ISearchable domain) {
        numberOfNodesEvaluated = 0;
        Stack<AState> openStack = new Stack<>();
        HashSet<AState> closedSet = new HashSet<>();

        AState start = domain.getInitialState();
        AState goal = domain.getGoalState();

        openStack.push(start);
        numberOfNodesEvaluated++;

        while (!openStack.isEmpty()) {
            AState current = openStack.pop();
            closedSet.add(current);

            if (current.equals(goal)) {
                return backTrace(current);
            }

            ArrayList<AState> neighbors = domain.getAllPossibleStates(current);
            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor) && !openStack.contains(neighbor)) {
                    neighbor.setCameFrom(current);
                    openStack.push(neighbor);
                    numberOfNodesEvaluated++;
                }
            }
        }
        return null;
    }

    private Solution backTrace(AState goal) {
        ArrayList<AState> path = new ArrayList<>();
        AState curr = goal;
        while (curr != null) {
            path.add(0, curr);
            curr = curr.getCameFrom();
        }
        return new Solution(path);
    }

    @Override
    public String getName() {
        return name;
    }
}
