package utils;

import models.tspInstance.TSPInstanceMatrix;
import models.tspInstance.TSPInstanceHalfMatrix;
import models.tspInstance.TSPInstance;
import models.tspInstance.UndirectedGraph;

public class TSPInstanceFactory {
    public static TSPInstance getInstance(String implementationIdentifier, int numberOfNodes){
        if (numberOfNodes < 0){
            throw new IllegalArgumentException("Number of nodes should be greater or equal 0");
        }
        switch (implementationIdentifier) {
            case "MATRIX" -> {
                return new TSPInstanceMatrix(numberOfNodes);
            }
            case "GRAPH" -> {
                return new UndirectedGraph(numberOfNodes);
            }
            case "HALF_MATRIX" -> {
                return new TSPInstanceHalfMatrix(numberOfNodes);
            }
            default -> throw new IllegalArgumentException("Illegal name");
        }
    }
}
