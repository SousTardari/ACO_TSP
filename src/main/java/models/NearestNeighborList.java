package models;

import java.util.Arrays;

public class NearestNeighborList {
    private double[][] distances;
    private int numberOfCities;
    private int[][] nearestNeighbors;
    private static final int NN_COUNT = 10;


    public NearestNeighborList(double[][] distances) {
        this.distances = distances;
        this.numberOfCities = distances.length;
        this.nearestNeighbors = new int[numberOfCities][NN_COUNT];

        computeNearestNeighbors();
        System.out.println(Arrays.deepToString(nearestNeighbors));
    }

    private void computeNearestNeighbors() {
        // Initialize nearest neighbors
        for (int i = 0; i < numberOfCities; i++) {
            // Fill nearest neighbors list for city i
            for (int j = 0; j < numberOfCities; j++) {
                if (i != j) {
                    updateNearestNeighbors(i, j);
                }
            }
        }
    }

    private void updateNearestNeighbors(int cityIndex, int neighborIndex) {
        for (int k = 0; k < NN_COUNT; k++) {
            if (nearestNeighbors[cityIndex][k] == -1) {
                nearestNeighbors[cityIndex][k] = neighborIndex;
                break;
            } else if (distances[cityIndex][neighborIndex] < distances[cityIndex][nearestNeighbors[cityIndex][k]]) {
                // Shift elements to make room for closer neighbor
                for (int l = NN_COUNT - 1; l > k; l--) {
                    nearestNeighbors[cityIndex][l] = nearestNeighbors[cityIndex][l - 1];
                }
                nearestNeighbors[cityIndex][k] = neighborIndex;
                break;
            }
        }
    }

    public int[] getNearestNeighbors(int position) {
        return nearestNeighbors[position];
    }

    public int getNnCount() {
        return NN_COUNT;
    }
}
