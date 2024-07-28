package models;

import java.util.Arrays;
import java.util.SplittableRandom;

public class Ant {
    private int tourDistance;
    private int nextTourIndex;
    private final boolean[] visited;
    private final int[] tour;
    private int currentPosition;
    private static final SplittableRandom random = new SplittableRandom();
    private final int numberOfNodes;

    public Ant(int numberOfNodes){
        this.numberOfNodes = numberOfNodes;
        tour = new int[this.numberOfNodes +1];
        visited = new boolean[this.numberOfNodes];
    }
    public void resetState(){
        nextTourIndex = 0;
        tourDistance = 0;
        Arrays.fill(visited,false);
        setAtRandomPosition();
    }
    private void setAtRandomPosition(){
        moveTo(random.nextInt(0, numberOfNodes),0);
    }
    public boolean isVisited(int position){
        return visited[position];
    }
    public void moveTo(int newPosition, int distance){
        currentPosition = newPosition;
        tour[nextTourIndex++] = currentPosition;
        visited[currentPosition] = true;
        tourDistance += distance;
    }
    public int getNodeAtPositionInTour(int n){
      return tour[n];
    }
    public int getTourDistance() {
        return tourDistance;
    }
    public int[] getTour() {
        return tour;
    }
    public int getCurrentPosition() {
        return currentPosition;
    }
}
