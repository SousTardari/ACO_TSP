package models.tspInstance;

import ai.ACO_TSP;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class UndirectedGraph implements TSPInstance {
    // experimental Implementation
    private final Graph graph;
    private final int numberOfNodes;
    private static final String DISTANCE_ATTRIBUTE = "distance";
    private static final String ATTRACTIVITY_ATTRIBUTE = "attractivity";
    private static final String edgeIdFormat = "%s-%s";

    public UndirectedGraph(int numberOfNodes){
        this.graph = new SingleGraph("TSPInstance");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        this.numberOfNodes = numberOfNodes;
    }

    @Override
    public void addEdge(int firstNodeId, int secondNodeId, int distance) {
        String nodeId1 = String.valueOf(firstNodeId);
        String nodeId2 = String.valueOf(secondNodeId);
        Edge edge = graph.addEdge(String.format(edgeIdFormat,nodeId1,nodeId2),nodeId1,nodeId2);
        edge.setAttribute(DISTANCE_ATTRIBUTE,distance);
        edge.setAttribute(ATTRACTIVITY_ATTRIBUTE,Math.pow(1.0/distance, ACO_TSP.BETA));
    }

    @Override
    public int getDistance(int firstNodeId, int secondNodeId) {
        Edge edge;
        if ((edge = graph.getEdge(String.format("%s-%s",firstNodeId,secondNodeId))) != null){
            return (int) edge.getAttribute(DISTANCE_ATTRIBUTE);
        }
        else{
            return (int) graph.getEdge((String.format("%s-%s",secondNodeId,firstNodeId))).getAttribute(DISTANCE_ATTRIBUTE);
        }
    }

    @Override
    public int getNumberOfNodes() {
        return graph.getNodeCount();
    }

    @Override
    public double getAttractivity(int firstNodeId, int secondNodeId) {
        Edge edge;
        if ((edge = graph.getEdge(String.format("%s-%s",firstNodeId,secondNodeId))) != null){
            return (double) edge.getAttribute(ATTRACTIVITY_ATTRIBUTE);
        }
        else{
            return (double) graph.getEdge((String.format("%s-%s",secondNodeId,firstNodeId))).getAttribute(ATTRACTIVITY_ATTRIBUTE);
        }
    }
}
