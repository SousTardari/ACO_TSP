package models.tspInstance;

import ai.ACO_TSP;

public class TSPInstanceHalfMatrix implements TSPInstance{

    private final int numberOfNodes;
    private final int[] distances;
    private final double[] attractivity;

    public TSPInstanceHalfMatrix(int numberOfNodes){
        this.numberOfNodes = numberOfNodes;
        int size = numberOfNodes * (numberOfNodes - 1) / 2;
        distances = new int[size];
        attractivity = new double[size];
    }
    @Override
    public int getNumberOfNodes() {
        return numberOfNodes;
    }
    @Override
    public int getDistance(int firstNodeId, int secondNodeId) {
        if (firstNodeId > secondNodeId){
            return distances[getIndex(secondNodeId,firstNodeId)];
        }
        else if(secondNodeId > firstNodeId){
            return distances[getIndex(firstNodeId,secondNodeId)];
        }
        return Integer.MIN_VALUE;
    }
    public double getAttractivity(int firstNodeId,int secondNodeId){
        if (firstNodeId > secondNodeId){
            return attractivity[getIndex(secondNodeId,firstNodeId)];
        }
        else if(secondNodeId > firstNodeId){
            return attractivity[getIndex(firstNodeId,secondNodeId)];
        }
        return Double.MIN_VALUE;
    }
    @Override
    public void addEdge(int firstNodeId, int secondNodeId, int distance) {
        if (firstNodeId > secondNodeId){
            int index = getIndex(secondNodeId,firstNodeId);
            distances[index] = distance;
            attractivity[index] = Math.pow(1.0 / distance, ACO_TSP.BETA);
        }
        else if(secondNodeId > firstNodeId){
            int index = getIndex(firstNodeId,secondNodeId);
            distances[index] = distance;
            attractivity[index] = Math.pow(1.0 / distance, ACO_TSP.BETA);
        }
    }

    private int getIndex(int i, int j) {
        return (i * (numberOfNodes-1) - (i * (i + 1)) / 2) + (j - i);
    }
}
