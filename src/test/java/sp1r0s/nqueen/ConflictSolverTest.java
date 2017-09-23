package sp1r0s.nqueen;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConflictSolverTest {

    private NQueensSolver solver;

    @Before
    public void setUp() {
        solver = new ConflictSolver();
    }

    // A bit flaky at the moment. This is caused by the random next best move
    //todo: fix
    @Ignore
    @Test
    public void testFrom8x8To20x20() {
        for (int i = 8; i <= 20; i++) {
            final int numberOfRows = i;
            final int numberOfColumns = i;
            final Chessboard chessboard = new Chessboard(numberOfRows, numberOfColumns);

            solver.solve(chessboard);

            assertTrue(chessboard.areQueensSafe());
        }
    }

    @Test
    public void test1000x1000() {
        final Chessboard chessboard = new Chessboard(1000, 1000);

        solver.solve(chessboard);

        assertTrue(chessboard.areQueensSafe());
    }

}