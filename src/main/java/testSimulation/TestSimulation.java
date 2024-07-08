package testSimulation;

import ai.ACO_TSP;

import java.io.IOException;
import java.util.Arrays;

public class TestSimulation {
    public static final String TSP_INSTANCE_IMPLEMENTATION_IDENTIFIER = "ADJ_MATRIX";
    public static final String PHEROMONE_TRAIL_IMPLEMENTATION_IDENTIFIER = "MATRIX";
    public static final String DATA_PATH = "./src/data/bier127.tsp";
    public static final int NUMBER_OF_ITERATIONS = 250;
    public static final int NUMBER_OF_ANTS = 25;
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        ACO_TSP acoTsp = new ACO_TSP(DATA_PATH,TSP_INSTANCE_IMPLEMENTATION_IDENTIFIER,PHEROMONE_TRAIL_IMPLEMENTATION_IDENTIFIER,NUMBER_OF_ANTS,NUMBER_OF_ITERATIONS);
        acoTsp.compute();
        System.out.println((System.currentTimeMillis() - start) / 1000);
        System.out.printf("BestTour: %s\n", Arrays.toString(acoTsp.getBestTour()));
        System.out.printf("BestDistance: %.0f\n", acoTsp.getBestDistance());
    }
}
