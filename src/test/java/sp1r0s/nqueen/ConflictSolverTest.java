package sp1r0s.nqueen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConflictSolverTest {

    private NQueensSolver solver;

    @Before
    public void setUp() {
        solver = new ConflictSolver();
    }

    @Test
    public void testFrom8x8To100x100() {
        for (int i = 8; i <= 100; i++) {
            final int numberOfRows = i;
            final int numberOfColumns = i;
            final Chessboard chessboard = new Chessboard(numberOfRows, numberOfColumns);

            solver.solve(chessboard);

            assertTrue(String.format("chessboard: %dx%d",
                                     numberOfRows,
                                     numberOfColumns),
                       chessboard.areQueensSafe());
        }
    }

    @Test
    public void test1000x1000() {
        final Chessboard chessboard = new Chessboard(1000, 1000);

        solver.solve(chessboard);

        assertTrue(chessboard.areQueensSafe());
    }


}