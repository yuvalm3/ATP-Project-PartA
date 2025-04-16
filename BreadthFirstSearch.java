package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    private String name = "Breadth First Search";

    @Override
    public Solution solve(ISearchable domain) {
        numberOfNodesEvaluated = 0;
        Queue<AState> openQueue = new LinkedList<>();
        HashSet<AState> closedSet = new HashSet<>();

        AState start = domain.getInitialState();
        AState goal = domain.getGoalState();

        openQueue.add(start);
        numberOfNodesEvaluated++;

        while (!openQueue.isEmpty()) {
            AState current = openQueue.poll();
            closedSet.add(current);

            if (current.equals(goal)) {
                return backTrace(current);
            }

            ArrayList<AState> neighbors = domain.getAllPossibleStates(current);
            for (AState neighbor : neighbors) {
                if (!closedSet.contains(neighbor) && !openQueue.contains(neighbor)) {
                    neighbor.setCameFrom(current);
                    openQueue.add(neighbor);
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
