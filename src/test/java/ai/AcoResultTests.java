package ai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class AcoResultTests {
    public static final String DATA_PATH1 = "./src/data/berlin52.tsp";
    public static final String DATA_PATH2 = "./src/data/a280.tsp";
    public static final String DATA_PATH3 = "./src/data/p654.tsp";
    public static final String DATA_PATH4 = "./src/data/d1291.tsp";

    public static final String[] dataPaths = new String[]{DATA_PATH1,DATA_PATH2,DATA_PATH3,DATA_PATH4};

    public static final int EXPECTED_NODES1 = 53;
    public static final int EXPECTED_NODES2 = 281;
    public static final int EXPECTED_NODES3 = 655;
    public static final int EXPECTED_NODES4 = 1292;

    public static final int[] expectedNodes = new int[]{EXPECTED_NODES1,EXPECTED_NODES2,EXPECTED_NODES3,EXPECTED_NODES4};

    public static final int NUMBER_OF_ITERATIONS = 400;
    public static final int NUMBER_OF_ANTS = 25;
    private static final String[] pheromoneTrailImplementations = new String[]{"MATRIX","HALF_MATRIX"};
    private static final String[] TSPInstanceImplementations = new String[]{"MATRIX","HALF_MATRIX"};

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

        for (int i = 0; i < dataPaths.length; i++) {
            ACO_TSP acoTsp = new ACO_TSP(dataPaths[i], tspInstanceImplementation,pheromoneImplementation,NUMBER_OF_ANTS,NUMBER_OF_ITERATIONS);
            acoTsp.compute();
            int[] tour = acoTsp.getBestTour();

            Assertions.assertEquals(tour[0], tour[tour.length - 1],"Tour is not cycled");
            Assertions.assertEquals(expectedNodes[i], tour.length,"Tour has invalid length");
            Assertions.assertTrue(checkOnDuplicates(tour), "Tour contains duplicates between start and end position");
        }
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
