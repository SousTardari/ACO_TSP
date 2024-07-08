package models.tspInstance;

import java.util.Arrays;

public class AdjacencyMatrix implements TSPInstance {
    //static information
    private final int numberOfNodes;
    private final double[][] distances;
    private final double[][] attractivity;

    public AdjacencyMatrix(int numberOfNodes){
        this.numberOfNodes = numberOfNodes;
        distances = new double[numberOfNodes][numberOfNodes];
        attractivity = new double[numberOfNodes][numberOfNodes];
    }
    @Override
    public int getNumberOfNodes() {
        return numberOfNodes;
    }
    @Override
    public double getDistance(int firstNodeId, int secondNodeId) {
        return distances[firstNodeId][secondNodeId];
    }
    public double getAttractivity(int firstNodeId,int secondNodeId){
        return attractivity[firstNodeId][secondNodeId];
    }

    @Override
    public void addEdge(int firstNodeId, int secondNodeId, double distance) {
        distances[firstNodeId][secondNodeId] = distance;
        attractivity[firstNodeId][secondNodeId] = 1.0 / distance;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(distances);
    }
}
