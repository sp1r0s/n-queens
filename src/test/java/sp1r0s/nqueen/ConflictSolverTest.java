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
        for (int numberOfQueens = 8; numberOfQueens <= 100; numberOfQueens++) {

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
        final List<Integer> queensSample = Arrays.asList(8, 100, 200, 400, 800, 1600, 3200, 6400, 12800);
        for (Integer numberOfQueens : queensSample) {
            final Chessboard chessboard = new Chessboard(numberOfQueens, numberOfQueens);

            solver.solve(chessboard);

            assertTrue(String.format("chessboard: %dx%d",
                    numberOfQueens,
                    numberOfQueens),
                    chessboard.areQueensSafe());
        }

    }

}