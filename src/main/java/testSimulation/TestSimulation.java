package testSimulation;

import ai.ACO_TSP;
import models.pheromoneTrail.HalfPheromoneMatrix;
import models.pheromoneTrail.MatrixPheromoneTrail;

import java.io.IOException;

public class TestSimulation {
    public static final String TSP_INSTANCE_IMPLEMENTATION_IDENTIFIER = "ADJ_MATRIX";
    public static final String PHEROMONE_TRAIL_IMPLEMENTATION_IDENTIFIER = "MATRIX";
    public static final String DATA_PATH = "./src/data/berlin52.tsp";
    public static final int NUMBER_OF_ITERATIONS = 150;
    public static final int NUMBER_OF_ANTS = 25;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        ACO_TSP acoTsp = new ACO_TSP(DATA_PATH,TSP_INSTANCE_IMPLEMENTATION_IDENTIFIER,PHEROMONE_TRAIL_IMPLEMENTATION_IDENTIFIER,NUMBER_OF_ANTS,NUMBER_OF_ITERATIONS);
        acoTsp.compute();
        System.out.println((System.currentTimeMillis() - start) / 1000);
        //System.out.printf("BestTour: %f\n", Arrays.toString(acoTsp.getBestTour()));
        System.out.printf("BestDistance: %.0f\n", acoTsp.getBestDistance());

        HalfPheromoneMatrix halfPheromoneMatrix = new HalfPheromoneMatrix(5);
        halfPheromoneMatrix.evaporatePheromones(0.5);
        halfPheromoneMatrix.addContributionAt(4,4,5,0.5);
        System.out.println(halfPheromoneMatrix.getPheromoneValue(4,4));

        MatrixPheromoneTrail pheromoneMatrix = new MatrixPheromoneTrail(5);
        pheromoneMatrix.evaporatePheromones(0.5);
        pheromoneMatrix.addContributionAt(4,4,5,0.5);
        System.out.println(pheromoneMatrix.getPheromoneValue(4,4));





    }
}
