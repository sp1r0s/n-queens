package sp1r0s.nqueen.oneconflict;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sp1r0s.nqueen.NQueensSolver;
import sp1r0s.nqueen.model.Chessboard;

import static org.junit.Assert.assertTrue;
import static sp1r0s.nqueen.util.Utilities.printChessboard;

public class OneConflictSolverTest {

    private NQueensSolver solver;

    @Before
    public void setUp() {
        solver = new OneConflictSolver();
    }

    @Test
    public void testSolvability() {
        for (int numberOfQueens = 8; numberOfQueens <= 100; numberOfQueens++) {

            final Chessboard chessboard = new Chessboard(numberOfQueens, numberOfQueens);

            solver.solve(chessboard);

            assertTrue(String.format("chessboard: %dx%d", numberOfQueens, numberOfQueens),
                       chessboard.areQueensSafe());
        }

    }

    @Test
    @Ignore
    public void test() {
        final int numberOfQueens = 1000;
        final Chessboard chessboard = new Chessboard(numberOfQueens, numberOfQueens);

        solver.solve(chessboard);
        printChessboard(chessboard);

        assertTrue(String.format("chessboard: %dx%d",
                                 numberOfQueens,
                                 numberOfQueens),
                                 chessboard.areQueensSafe());

    }

}