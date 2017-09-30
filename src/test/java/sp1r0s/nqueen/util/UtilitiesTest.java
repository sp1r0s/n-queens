package sp1r0s.nqueen.util;

import org.junit.Ignore;
import org.junit.Test;
import sp1r0s.nqueen.model.Chessboard;

@Ignore
public class UtilitiesTest {

    @Test
    public void testVisualisation() {
        final Chessboard chessboard = new Chessboard(8, 8);
        Utilities.showChessboard(chessboard);
    }

    @Test
    public void testVisualisationLargeChessBoard() {
        final Chessboard chessboard = new Chessboard(20, 20);
        Utilities.showChessboard(chessboard);
    }
}