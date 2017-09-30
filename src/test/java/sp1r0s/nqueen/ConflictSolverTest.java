package sp1r0s.nqueen;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ConflictSolverTest {

    private NQueensSolver solver;

    @Before
    public void setUp() {
        solver = new ConflictSolver();
    }

    @Test
    public void testSolvability() {
        for (int numberOfQueens = 8; numberOfQueens <= 1000; numberOfQueens++) {

            final Chessboard chessboard = new Chessboard(numberOfQueens, numberOfQueens);

            solver.solve(chessboard);

            assertTrue(String.format("chessboard: %dx%d",
                    numberOfQueens,
                    numberOfQueens),
                    chessboard.areQueensSafe());
        }

    }

    @Test
    public void testTimeComplexity() {
        final List<Integer> queensSample = Arrays.asList(8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192);
        for (Integer numberOfQueens : queensSample) {
            final Chessboard chessboard = new Chessboard(numberOfQueens, numberOfQueens);

            solver.solve(chessboard);

            assertTrue(String.format("chessboard: %dx%d",
                    numberOfQueens,
                    numberOfQueens),
                    chessboard.areQueensSafe());
        }

    }

    @Test
    public void test() {
        final int numberOfQueens = 2048;
        final Chessboard chessboard = new Chessboard(numberOfQueens, numberOfQueens);

        solver.solve(chessboard);

        assertTrue(String.format("chessboard: %dx%d",
                numberOfQueens,
                numberOfQueens),
                chessboard.areQueensSafe());

    }

}