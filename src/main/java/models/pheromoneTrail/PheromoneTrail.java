package models.pheromoneTrail;

public interface PheromoneTrail {
    double getPheromoneValue(int firstNodeId, int secondNodeId);
    void evaporatePheromones(double evaporationRate);
    void addContributionAt(int position1, int position2, double contribution);
}
