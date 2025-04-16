package algorithms.search;

public interface ISearchingAlgorithm {
    /**
     * Solves the given searching problem.
     * @param domain The search domain.
     * @return A Solution object representing the path from the initial state to the goal state.
     */
    Solution solve(ISearchable domain);

    /**
     * Returns the number of nodes evaluated during the search.
     */
    int getNumberOfNodesEvaluated();

    /**
     * Returns the name of the search algorithm.
     */
    String getName();
}
