package ai;

import models.Ant;
import models.pheromoneTrail.PheromoneTrail;
import models.tspInstance.TSPInstance;
import utils.PheromoneTrailFactory;
import utils.TSPLibParser;

import java.io.IOException;
import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class ACO_TSP {
    private final PheromoneTrail pheromoneTrails;
    private final TSPInstance tspInstance;
    private final int numberOfNodes;
    private final Ant[] ants;
    private int[] bestTour;
    private int bestDistance;
    private static final SplittableRandom rn = new SplittableRandom();

    //Parameters
    private final int numberOfIterations;
    public static double ALPHA = 1;
    public static double BETA = 5;
    public static double EVAPORATION_RATE = 0.5;
    private static final int NO_IMPROVEMENT_LIMIT = 30;
    private static final double ACCEPTED_IMPROVEMENT_RATE = 0.995;

    public ACO_TSP(String tspInstancePath, String tspImplementationIdentifier, String pheromoneTrailImplementationIdentifier, int numberOfAnts, int numberOfIterations) throws IOException {
        TSPLibParser tspLibParser = new TSPLibParser();
        tspInstance = tspLibParser.parseTSPInstanceFromFile(tspInstancePath, tspImplementationIdentifier);
        numberOfNodes = tspInstance.getNumberOfNodes();
        pheromoneTrails = PheromoneTrailFactory.getInstance(pheromoneTrailImplementationIdentifier, numberOfNodes);
        ants = initAntColony(numberOfAnts);
        this.numberOfIterations = numberOfIterations;
        // compute memory usage of virtual machine
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        System.out.printf("Speicherauslastung: %d\n",(rt.totalMemory() - rt.freeMemory()) / 1000);
    }

    public void compute() {

        // the bestTour is computed only once
        if (bestTour == null) {
            bestTour = new int[numberOfNodes + 1];
            bestDistance = Integer.MAX_VALUE;

            int iteration = 0;
            int noImprovementCount = 0;
            Ant bestAntInIteration;
            while(iteration++ < numberOfIterations  && noImprovementCount < NO_IMPROVEMENT_LIMIT){
                bestAntInIteration = null;
                for (Ant ant : ants) {
                    performMoves(ant);
                    if (bestAntInIteration == null || ant.getTourDistance() < bestAntInIteration.getTourDistance()) {
                        bestAntInIteration = ant;
                    }
                }
                assert bestAntInIteration != null;
                int bestIterationTour = bestAntInIteration.getTourDistance();

                if (bestIterationTour < bestDistance) {
                    if (((double) bestIterationTour / bestDistance) > ACCEPTED_IMPROVEMENT_RATE){
                        noImprovementCount++;
                    }
                    else {
                        noImprovementCount = 0;
                    }
                    bestDistance = bestIterationTour;
                    System.arraycopy(bestAntInIteration.getTour(), 0, bestTour, 0, numberOfNodes + 1);
                }
                else {
                    noImprovementCount++;
                }
                updatePheromones();
            }
        }
    }
    private Ant[] initAntColony(int numberOfAnts) {
        Ant[] ants = new Ant[numberOfAnts];
        int numberOfNodes = tspInstance.getNumberOfNodes();
        for (int i = 0; i < numberOfAnts; i++) {
            ants[i] = new Ant(numberOfNodes);
        }
        return ants;
    }
    //builds a complete solution for an ant
    private void performMoves(Ant ant) {
        ant.resetState();
        for (int step = 1; step < numberOfNodes; step++) {
            int nextPosition = selectNextPosition(ant);
            ant.moveTo(nextPosition, tspInstance.getDistance(ant.getCurrentPosition(), nextPosition));
        }
        int startPosition = ant.getNodeAtPositionInTour(0);
        ant.moveTo(startPosition, tspInstance.getDistance(ant.getCurrentPosition(), startPosition));
    }
    private int selectNextPosition(Ant ant){
        double[] selectionProbabilities = calculateAndNormalizeProbabilitiesFor(ant);

        double upperBound = rn.nextDouble();
        double sumProbabilities = 0;


        for (int i = 0; i < numberOfNodes; i++) {
            sumProbabilities += selectionProbabilities[i];
            if (upperBound <= sumProbabilities){
                return i;
            }
        }

        return IntStream.range(0,numberOfNodes)
                .filter(position -> !ant.isVisited(position))
                .findFirst()
                .orElse(-1);
    }
    private double[] calculateAndNormalizeProbabilitiesFor(Ant ant) {

        int currentPosition = ant.getCurrentPosition();
        double sumProbabilities = 0.0;
        double[] selectionProbabilities = new double[numberOfNodes];


        for (int i = 0; i < numberOfNodes; i++) {
            if (!ant.isVisited(i)){
                selectionProbabilities[i] = tspInstance.getAttractivity(currentPosition,i) * (Math.pow(pheromoneTrails.getPheromoneValue(currentPosition,i),ALPHA));
                sumProbabilities += selectionProbabilities[i];
            }
        }

        if (sumProbabilities > 0){
            for (int i = 0; i < numberOfNodes; i++) {
                if (!ant.isVisited(i)){
                    selectionProbabilities[i] /= sumProbabilities;
                }
            }
        }
        return selectionProbabilities;
    }
    private void updatePheromones(){
        pheromoneTrails.evaporatePheromones(EVAPORATION_RATE);

        for(Ant ant: ants){
            double contribution = 1.0 / ant.getTourDistance();
            for (int i = 0; i < numberOfNodes - 1; i++) {
                int from = ant.getNodeAtPositionInTour(i);
                int to = ant.getNodeAtPositionInTour(i+1);
                pheromoneTrails.addContributionAt(to,from,contribution,EVAPORATION_RATE);
            }
            int to = ant.getNodeAtPositionInTour(0);
            int from = ant.getNodeAtPositionInTour(numberOfNodes-1);
            pheromoneTrails.addContributionAt(to,from,contribution,EVAPORATION_RATE);
        }
    }
    public int[] getBestTour() {
        return bestTour;
    }
    public double getBestDistance() {
        return bestDistance;
    }
}
