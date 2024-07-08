package ai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class ACOResults {
    //public static final String DATA_PATH = "./src/data/p654.tsp";
    //public static final String DATA_PATH = "./src/data/d1291.tsp";
    public static final String DATA_PATH = "./src/data/berlin52.tsp";

    //public static final int EXPECTED_RESULT = 7542;
    //public static final int EXPECTED_RESULT = 2500;
    //public static final int EXPECTED_RESULT = 50801;
    //public static final int EXPECTED_RESULT = 34643;

    // public static final int EXPECTED_NODES = 281;
    public static final int EXPECTED_NODES = 53;
    //public static final int EXPECTED_NODES = 655;
    //public static final int EXPECTED_NODES = 1292;

    //public static final int EXPECTED_RESULT = 50801;
    //public static final int EXPECTED_RESULT = 34643;

    public static final int NUMBER_OF_ITERATIONS = 500;
    public static final int NUMBER_OF_ANTS = 30;
    private static final String[] pheromoneTrailImplementations = new String[]{"MATRIX","HALF_PH_MATRIX"};
    private static final String[] TSPInstanceImplementations = new String[]{"ADJ_MATRIX","HALF_ADJ_MATRIX"};

    static Stream<Object[]> implementationProvider() {
        return Stream.of(
                Stream.of(pheromoneTrailImplementations)
                        .flatMap(p -> Stream.of(TSPInstanceImplementations)
                                .map(t -> new Object[] { p, t }))
                        .toArray(Object[][]::new)
        );
    }
    @ParameterizedTest
    @MethodSource("implementationProvider")
    public void testResultOnDuplicatesCycledTourAndNumberOfNodes(String pheromoneImplementation, String tspInstanceImplementation) throws IOException {

        long start = System.currentTimeMillis();
        ACO_TSP acoTsp = new ACO_TSP(DATA_PATH,tspInstanceImplementation,pheromoneImplementation,NUMBER_OF_ANTS,NUMBER_OF_ITERATIONS);
        acoTsp.compute();
        int[] tour = acoTsp.getBestTour();
        System.out.println((System.currentTimeMillis() - start) / 1000);
        Assertions.assertEquals(tour[0], tour[tour.length - 1],"Tour is not cycled");
        Assertions.assertEquals(EXPECTED_NODES, tour.length,"Tour has invalid length");
        Assertions.assertTrue(checkOnDuplicates(tour), "Tour contains duplicates between start and end position");

    }
    private boolean checkOnDuplicates(int[] tour){
        Set<Integer> testSet = new HashSet<>();
        for (int i = 1; i < tour.length; i++) {
            if (!testSet.add(i)) {
                return false;
            }
        }
        return true;
    }

}
