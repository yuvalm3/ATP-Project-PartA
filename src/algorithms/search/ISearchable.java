package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    /**
     * Returns the initial state of the search problem.
     */
    AState getInitialState();

    /**
     * Returns the goal state of the search problem.
     */
    AState getGoalState();

    /**
     * Given a state, returns all possible states that can be reached from it.
     */
    ArrayList<AState> getAllPossibleStates(AState s);
}
