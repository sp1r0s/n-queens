package sp1r0s.nqueen;

public interface NQueensSolver {

    void placeQueensInInitialPosition(final Chessboard chessboard);

    void calibrate(final Chessboard chessboard);

    default void solve(final Chessboard chessboard) {
        placeQueensInInitialPosition(chessboard);
        calibrate(chessboard);
    }
}
