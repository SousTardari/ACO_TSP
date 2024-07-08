package models.tspInstance;

public interface TSPInstance {
    //void addNode(int nodeId);
    void addEdge(int firstNodeId, int secondNodeId, double distance);
    double getDistance(int firstNodeId, int secondNodeId);
    int getNumberOfNodes();
    double getAttractivity(int firstNodeId, int secondNodeId);
    double[][] getDistances();


}
