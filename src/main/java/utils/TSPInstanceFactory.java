package utils;

import models.tspInstance.AdjacencyMatrix;
import models.tspInstance.HalfAdjacencyMatrix;
import models.tspInstance.TSPInstance;
import models.tspInstance.UndirectedGraph;

public class TSPInstanceFactory {
    public static TSPInstance getInstance(String implementationIdentifier, int numberOfNodes){
        if (numberOfNodes < 0){
            throw new IllegalArgumentException("Number of nodes should be greater or equal 0");
        }
        switch (implementationIdentifier) {
            case "ADJ_MATRIX" -> {
                return new AdjacencyMatrix(numberOfNodes);
            }
            case "GRAPH" -> {
                return new UndirectedGraph(numberOfNodes);
            }
            case "HALF_ADJ_MATRIX" -> {
                return new HalfAdjacencyMatrix(numberOfNodes);
            }
            default -> throw new IllegalArgumentException("Illegal name");
        }

    }
}
