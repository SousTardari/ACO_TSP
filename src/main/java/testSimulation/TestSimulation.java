package testSimulation;

import ai.ACO_TSP;

import java.io.IOException;

public class TestSimulation {
    public static final String TSP_INSTANCE_IMPLEMENTATION_IDENTIFIER = "MATRIX";
    public static final String PHEROMONE_TRAIL_IMPLEMENTATION_IDENTIFIER = "MATRIX";
    public static final String DATA_PATH = "./src/data/a280.tsp";
    public static final int NUMBER_OF_ITERATIONS = 400;
    public static final int ITERATIONS = 5;
    public static final int NUMBER_OF_ANTS = 25;
    public static void main(String[] args) throws IOException {
        long avgTime = 0;
        long avgBestDistance = 0;

        for (int i = 0; i < ITERATIONS; i++) {
            long start = System.currentTimeMillis();
            ACO_TSP acoTsp = new ACO_TSP(DATA_PATH,TSP_INSTANCE_IMPLEMENTATION_IDENTIFIER,PHEROMONE_TRAIL_IMPLEMENTATION_IDENTIFIER,NUMBER_OF_ANTS,NUMBER_OF_ITERATIONS);
            acoTsp.compute();
            avgTime += (System.currentTimeMillis() - start);
            //System.out.printf("BestTour: %f\n", Arrays.toString(acoTsp.getBestTour()));
            System.out.println(acoTsp.getBestDistance());
            avgBestDistance+=acoTsp.getBestDistance();
        }
        avgTime /= ITERATIONS;
        avgBestDistance /= ITERATIONS;
        System.out.printf("Laufzeit: %d\n", avgTime);
        System.out.printf("BestDistance: %d\n", avgBestDistance);
    }
}
