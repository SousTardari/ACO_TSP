package models.pheromoneTrail;


import java.util.Arrays;

public class PheromoneTrailHalfMatrix implements PheromoneTrail{

    private final double[] pheromoneMatrix;
    private final int numberOfNodes;

    public PheromoneTrailHalfMatrix(int numberOfNodes){
        this.numberOfNodes = numberOfNodes;
        int size = numberOfNodes * (numberOfNodes - 1) / 2;
        pheromoneMatrix = new double[size];
        Arrays.fill(pheromoneMatrix, 1.0);
    }
    @Override
    public double getPheromoneValue(int firstNodeId, int secondNodeId) {
        if (firstNodeId < secondNodeId){
            return pheromoneMatrix[getIndex(firstNodeId,secondNodeId)];
        }
        else if(secondNodeId < firstNodeId){
            return pheromoneMatrix[getIndex(secondNodeId,firstNodeId)];
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public void evaporatePheromones(double evaporationRate) {
        for (int i = 0; i < pheromoneMatrix.length; i++) {
            pheromoneMatrix[i] *= (1 - evaporationRate);
        }
    }
    @Override
    public void addContributionAt(int position1, int position2, double contribution, double evaporationRate) {
        if (position1 < position2){
            int index = getIndex(position1,position2);
            pheromoneMatrix[index] += (contribution * evaporationRate);
        }
        else if(position2 < position1){
            int index = getIndex(position2,position1);
            pheromoneMatrix[index] += (contribution * evaporationRate);
        }
    }

    private int getIndex(int i, int j) {
        if (i >= numberOfNodes || j >= numberOfNodes) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return i * (numberOfNodes - 1) - (i * (i + 1)) / 2 + (j - i);
    }

}
