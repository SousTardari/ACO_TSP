package models.pheromoneTrail;


public class PheromoneTrailMatrix implements PheromoneTrail {
    private final double[][] pheromoneMatrix;
    private final int numberOfNodes;


    public PheromoneTrailMatrix(int numberOfNodes){
        this.numberOfNodes = numberOfNodes;
        pheromoneMatrix = new double[numberOfNodes][numberOfNodes];
        initializePheromones();
    }

    private void initializePheromones(){
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = i; j < numberOfNodes; j++) {
                pheromoneMatrix[i][j] = 1.0;
                pheromoneMatrix[j][i] = 1.0;
            }
        }
    }

    @Override
    public double getPheromoneValue(int firstNodeId, int secondNodeId) {
        return pheromoneMatrix[firstNodeId][secondNodeId];
    }

    @Override
    public void evaporatePheromones(double evaporationRate) {
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = i; j < numberOfNodes; j++) {
                pheromoneMatrix[i][j] *= (1-evaporationRate);
                pheromoneMatrix[j][i] = pheromoneMatrix[i][j];
            }
        }
    }

    @Override
    public void addContributionAt(int position1, int position2,double contribution,double evaporationRate) {
        pheromoneMatrix[position1][position2] += (contribution * evaporationRate);
        pheromoneMatrix[position2][position1] = pheromoneMatrix[position1][position2];
    }
}
