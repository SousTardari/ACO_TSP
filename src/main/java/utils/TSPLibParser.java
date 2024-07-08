package utils;

import models.tspInstance.TSPInstance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TSPLibParser {
    private int numberOfNodes;
    private Map<Integer,double[]> nodeCoordMap;

    public TSPLibParser(){

    }
    public TSPInstance parseTCPInstanceFromFile(String filePath, String implementation) throws IOException {
        initDataBeforeParsing();
        readDataFromFile(filePath);
        TSPInstance TSPInstance = TSPInstanceFactory.getInstance(implementation,numberOfNodes);
        fillData(TSPInstance);
        return TSPInstance;
    }

    private void initDataBeforeParsing(){
        numberOfNodes = 0;
        nodeCoordMap = new HashMap<>();
    }

    private void readDataFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String currentLine;
        boolean readingNodes = false;

        while((currentLine = reader.readLine()) != null){
            currentLine = currentLine.trim();
            if (currentLine.startsWith("DIMENSION")){
                numberOfNodes = Integer.parseInt(currentLine.split(":")[1].trim());
            } else if (currentLine.startsWith("NODE_COORD_SECTION")) {
                readingNodes = true;
            } else if (currentLine.startsWith("EOF")) {
                break;
            } else if (readingNodes) {
                String[] parts = currentLine.split("\\s+");
                int nodeId = Integer.parseInt(parts[0])-1;
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                nodeCoordMap.put(nodeId, new double[]{x,y});
            }
        }
        reader.close();
    }


    private void fillData(TSPInstance TSPInstance){
        for (int i = 0; i < numberOfNodes; i++) {
            double[] iNodeCoord = nodeCoordMap.get(i);
            for (int j = i; j < numberOfNodes; j++) {
                if (i != j){
                    double[] jNodeCoord = nodeCoordMap.get(j);
                    int value = computeDistance(iNodeCoord,jNodeCoord);

                    TSPInstance.addEdge(i,j,value);
                }
            }
        }
    }
    private int computeDistance(double[] coord1, double[] coord2) {
        double dx = coord1[0] - coord2[0];
        double dy = coord1[1] - coord2[1];
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }


}
