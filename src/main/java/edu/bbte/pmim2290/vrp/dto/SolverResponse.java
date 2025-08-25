package edu.bbte.pmim2290.vrp.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverResponse {

    private Map<String, List<Long>> bestRoute;

    public SolverResponse() {
        this.bestRoute = new HashMap<>();
    }

    public Map<String, List<Long>> getBestRoute() {
        return bestRoute;
    }

    public void setBestRoute(Map<String, List<Long>> bestRoute) {
        this.bestRoute = bestRoute;
    }

    @Override
    public String toString() {
        return "SolverResponse{"
                + "bestRoute=" + bestRoute
                + '}';
    }
}
