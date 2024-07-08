package models.tspInstance;

import ai.ACO_TSP;


public class AdjacencyMatrix implements TSPInstance {
    //static information
    private final int numberOfNodes;
    private final int[][] distances;
    private final double[][] attractivity;

    public AdjacencyMatrix(int numberOfNodes){
        this.numberOfNodes = numberOfNodes;
        distances = new int[numberOfNodes][numberOfNodes];
        attractivity = new double[numberOfNodes][numberOfNodes];
    }
    @Override
    public int getNumberOfNodes() {
        return numberOfNodes;
    }
    @Override
    public int getDistance(int firstNodeId, int secondNodeId) {
        return distances[firstNodeId][secondNodeId];
    }
    public double getAttractivity(int firstNodeId,int secondNodeId){
        return attractivity[firstNodeId][secondNodeId];
    }
    @Override
    public void addEdge(int firstNodeId, int secondNodeId, int distance) {
        distances[firstNodeId][secondNodeId] = distance;
        distances[secondNodeId][firstNodeId] = distance;
        attractivity[firstNodeId][secondNodeId] = Math.pow(1.0 / distance,ACO_TSP.BETA);
        attractivity[secondNodeId][firstNodeId] = Math.pow(1.0 / distance,ACO_TSP.BETA);
    }
}
