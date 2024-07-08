package utils;

import models.tspInstance.AdjacencyMatrix;
import models.tspInstance.TSPInstance;

public class TSPInstanceFactory {
    public static TSPInstance getInstance(String implementationIdentifier, int numberOfNodes){
        if (numberOfNodes < 0){
            throw new IllegalArgumentException("Number of nodes should be greater or equal 0");
        }
        switch (implementationIdentifier) {
            case "ADJ_MATRIX" -> {
                return new AdjacencyMatrix(numberOfNodes);
            }
            default -> throw new IllegalArgumentException("Illegal name");
        }

    }
}
