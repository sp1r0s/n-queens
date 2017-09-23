package sp1r0s.nqueen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChessboardTest {

    private Chessboard chessboard;

    @Before
    public void setUp() {
        chessboard = new Chessboard(8, 8);
    }

    @Test
    public void testPlaceQueen() {
        chessboard.placeQueen(1,1);

        final Coordinates queenCoordinates = chessboard.getQueensLocation().iterator().next();
        assertEquals(1, queenCoordinates.getX());
        assertEquals(1, queenCoordinates.getY());
    }

    @Test
    public void testGetChessboardDimensions() {
        chessboard = new Chessboard(1,1);

        assertEquals(1, chessboard.getNumberOfRows());
        assertEquals(1, chessboard.getNumberOfColumns());
    }

    @Test
    public void testValidation() {
        final Chessboard solution8x8 = generate8x8SymmetricalSolution();
        assertTrue(solution8x8.areQueensSafe());

        final Chessboard solution10x10 = generate10x10SymmetricalSolution();
        assertTrue(solution10x10.areQueensSafe());

        final Chessboard solution12x12 = generate12x12SymmetricalSolution();
        assertTrue(solution12x12.areQueensSafe());

        final Chessboard nonSolution = generateNonSolution();
        assertTrue(nonSolution.areQueensSafe());
    }

    @Test
    public void testCopyConstructor() {
        final Chessboard chessboard = generate8x8SymmetricalSolution();
        final Chessboard copy = new Chessboard(chessboard);
        chessboard.placeQueen(0, 0);
        assertFalse(copy.getQueensLocation().contains(new Coordinates(0, 0)));
    }

    private Chessboard generate8x8SymmetricalSolution() {
        Chessboard chessboard = new Chessboard(8, 8);
        chessboard.placeQueen(3,0);
        chessboard.placeQueen(5,1);
        chessboard.placeQueen(7,2);
        chessboard.placeQueen(6,4);
        chessboard.placeQueen(1,3);
        chessboard.placeQueen(0,5);
        chessboard.placeQueen(2,6);
        chessboard.placeQueen(4,7);
        return chessboard;
    }

    private Chessboard generate10x10SymmetricalSolution() {
        Chessboard chessboard = new Chessboard(10, 10);
        chessboard.placeQueen(1,0);
        chessboard.placeQueen(3,1);
        chessboard.placeQueen(5,2);
        chessboard.placeQueen(7,3);
        chessboard.placeQueen(9,4);
        chessboard.placeQueen(0,5);
        chessboard.placeQueen(2,6);
        chessboard.placeQueen(4,7);
        chessboard.placeQueen(6,8);
        chessboard.placeQueen(8,9);
        return chessboard;
    }

    private Chessboard generate12x12SymmetricalSolution() {
        Chessboard chessboard = new Chessboard(12, 12);
        chessboard.placeQueen(1,0);
        chessboard.placeQueen(3,1);
        chessboard.placeQueen(5,2);
        chessboard.placeQueen(7,3);
        chessboard.placeQueen(9,4);
        chessboard.placeQueen(11,5);
        chessboard.placeQueen(0,6);
        chessboard.placeQueen(2,7);
        chessboard.placeQueen(4,8);
        chessboard.placeQueen(6,9);
        chessboard.placeQueen(8,10);
        chessboard.placeQueen(10,11);
        return chessboard;
    }

    private Chessboard generateNonSolution() {
        Chessboard chessboard = new Chessboard(8, 8);
        chessboard.placeQueen(1,0);
        chessboard.placeQueen(1,1);
        return chessboard;
    }
}