package algorithms.search;

import java.util.*;

public class BestFirstSearch extends ASearchingAlgorithm {
    private String name = "Best First Search";

    @Override
    public Solution solve(ISearchable domain) {
        numberOfNodesEvaluated = 0;
        PriorityQueue<AState> openQueue = new PriorityQueue<>(new Comparator<AState>() {
            @Override
            public int compare(AState s1, AState s2) {
                return Double.compare(s1.getCost(), s2.getCost());
            }
        });
        HashSet<AState> closedSet = new HashSet<>();

        AState start = domain.getInitialState();
        AState goal = domain.getGoalState();

        start.setCost(heuristic(start, goal));
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
                if (!closedSet.contains(neighbor)) {
                    double cost = current.getCost() - heuristic(current, goal) + 1 + heuristic(neighbor, goal);
                    // מחשבים עלות שמורכבת מעלות המעבר והערכה (heuristic)
                    if (neighbor.getCost() == 0 || cost < neighbor.getCost()) {
                        neighbor.setCost(cost);
                        neighbor.setCameFrom(current);
                        if (!openQueue.contains(neighbor)) {
                            openQueue.add(neighbor);
                            numberOfNodesEvaluated++;
                        }
                    }
                }
            }
        }
        return null;
    }

    private double heuristic(AState state, AState goal) {
        if (state instanceof MazeState && goal instanceof MazeState) {
            MazeState s = (MazeState) state;
            MazeState g = (MazeState) goal;
            int rowDiff = Math.abs(s.getPosition().getRowIndex() - g.getPosition().getRowIndex());
            int colDiff = Math.abs(s.getPosition().getColumnIndex() - g.getPosition().getColumnIndex());
            return rowDiff + colDiff;
        }
        return 0;
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
