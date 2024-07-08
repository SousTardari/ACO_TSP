package ai;

import models.Ant;
import models.pheromoneTrail.PheromoneTrail;
import models.tspInstance.TSPInstance;
import utils.PheromoneTrailFactory;
import utils.TSPLibParser;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

public class ACO_TSP {
    private final PheromoneTrail pheromoneTrails;
    private final TSPInstance tspInstance;
    private final int numberOfNodes;
    private final Ant[] ants;
    private int[] bestTour;
    private double bestDistance;
    private static final Random rn = new Random();

    //Parameters
    private int numberOfIterations;
    public static final double ALPHA = 1;
    public static final double BETA = 4;
    public static final double EVAPORATION_RATE = 0.1;

    //TODO add a possibility to choose the implementation for TSPInstance, PheromoneTrails and filePath to the TSPTestData in the constructor
    public ACO_TSP(String tspInstancePath, String tspImplementationIdentifier, String pheromoneTrailImplementationIdentifier,int numberOfAnts, int numberOfIterations) throws IOException {
        TSPLibParser tspLibParser = new TSPLibParser();
        tspInstance = tspLibParser.parseTCPInstanceFromFile(tspInstancePath,tspImplementationIdentifier);
        numberOfNodes = tspInstance.getNumberOfNodes();
        pheromoneTrails = PheromoneTrailFactory.getInstance(pheromoneTrailImplementationIdentifier, numberOfNodes);
        ants = initAntColony(numberOfAnts);
        this.numberOfIterations = numberOfIterations;
    }
    public void compute(){
        if (bestTour == null){
            bestTour = new int[numberOfNodes+1];
            bestDistance = Double.POSITIVE_INFINITY;


            for (int iteration = 0; iteration < numberOfIterations; iteration++) {
                for(Ant ant: ants){
                    performMoves(ant);
                }
                updatePheromones();
            }
        }
    }
    private Ant[] initAntColony(int numberOfAnts){
        Ant[] ants = new Ant[numberOfAnts];
        int numberOfNodes = tspInstance.getNumberOfNodes();
        for (int i = 0; i < numberOfAnts; i++) {
            ants[i] = new Ant(numberOfNodes);
        }
        return ants;
    }
    private void performMoves(Ant ant){
        ant.resetState();
        for (int step = 1; step < numberOfNodes; step++) {
            int nextPosition = selectNextPosition(ant);
            ant.moveTo(nextPosition,tspInstance.getDistance(ant.getCurrentPosition(), nextPosition));
        }
        int startPosition = ant.getNodeAtPositionInTour(0);
        ant.moveTo(startPosition,tspInstance.getDistance(ant.getCurrentPosition(),startPosition));

        updateBestScore(ant);
    }
    private void updateBestScore(Ant ant){
        if (ant.getTourDistance() < bestDistance){
            bestDistance = ant.getTourDistance();
            System.arraycopy(ant.getTour(),0,bestTour,0,numberOfNodes+1);
        }
    }
    private int selectNextPosition(Ant ant){
        double[] selectionProbabilities = calculateAndNormalizeProbabilitiesFor(ant);

        double upperBound = rn.nextDouble();
        double sumProbabilities = 0;
        //picking next position
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
    private double[] calculateAndNormalizeProbabilitiesFor(Ant ant){
        int currentPosition = ant.getCurrentPosition();
        double sumProbabilities = 0.0;
        double[] selectionProbabilities = new double[numberOfNodes];


        for (int i = 0; i < numberOfNodes; i++) {
            if (ant.isVisited(i)){
                selectionProbabilities[i] = 0.0;
            }
            else{
                double attractivity = Math.pow(tspInstance.getAttractivity(currentPosition,i), BETA);
                double pheromons = Math.pow(pheromoneTrails.getPheromoneValue(currentPosition,i),ALPHA);

                selectionProbabilities[i] = attractivity * pheromons;
                sumProbabilities += selectionProbabilities[i];
            }
        }

        if (sumProbabilities > 0){
            for (int i = 0; i < numberOfNodes; i++) {
                if (ant.isVisited(i)){
                    selectionProbabilities[i] = 0.0;
                }
                else {
                    selectionProbabilities[i] /= sumProbabilities;
                }
            }
        }
        return selectionProbabilities;
    }
    private void updatePheromones(){
        pheromoneTrails.evaporatePheromones(EVAPORATION_RATE);

        for(Ant ant: ants){
            double contribution = 1/ant.getTourDistance();
            for (int i = 0; i < numberOfNodes - 1; i++) {
                int from = ant.getNodeAtPositionInTour(i);
                int to = ant.getNodeAtPositionInTour(i+1);
                pheromoneTrails.addContributionAt(to,from,contribution);
            }
            int from = ant.getNodeAtPositionInTour(0);
            int to = ant.getNodeAtPositionInTour(numberOfNodes-1);
            pheromoneTrails.addContributionAt(to,from,contribution);
        }
    }
    public int[] getBestTour() {
        return bestTour;
    }
    public double getBestDistance() {
        return bestDistance;
    }
}
