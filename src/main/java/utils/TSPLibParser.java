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
        System.out.println(numberOfNodes);
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
            for (int j = i; j < numberOfNodes; j++) {
                if (i != j){
                    double[] iNodeCoord = nodeCoordMap.get(i);
                    double[] jNodeCoord = nodeCoordMap.get(j);
                    double value = Math.round(Math.sqrt(
                            Math.pow(iNodeCoord[0] - jNodeCoord[0],2) + Math.pow(iNodeCoord[1] - jNodeCoord[1],2)));

                    TSPInstance.addEdge(i,j,value);
                    TSPInstance.addEdge(j,i,value);
                }
            }
        }
    }
}
