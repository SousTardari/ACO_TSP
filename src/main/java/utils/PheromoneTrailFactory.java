package utils;

import models.pheromoneTrail.HalfPheromoneMatrix;
import models.pheromoneTrail.MatrixPheromoneTrail;
import models.pheromoneTrail.PheromoneTrail;

public class PheromoneTrailFactory {
    public static PheromoneTrail getInstance(String implementationIdentifier, int numberOfNodes){
        if (numberOfNodes < 0){
            throw new IllegalArgumentException("Number of nodes should be greater or equal 0");
        }
        switch (implementationIdentifier) {
            case "MATRIX" -> {
                return new MatrixPheromoneTrail(numberOfNodes);
            }
            case "HALF_PH_MATRIX" -> {
                return new HalfPheromoneMatrix(numberOfNodes);
            }
            default -> throw new IllegalArgumentException("Illegal name");
        }
    }
}
