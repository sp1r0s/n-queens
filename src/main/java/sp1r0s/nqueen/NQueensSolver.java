package sp1r0s.nqueen;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

public interface NQueensSolver {

    void placeQueensInInitialPosition(final Chessboard chessboard);

    void calibrate(final Chessboard chessboard);

    default void solve(final Chessboard chessboard) {
        final Instant started = Instant.now();
        placeQueensInInitialPosition(chessboard);
        calibrate(chessboard);
        final Instant ended = Instant.now();
        if (chessboard.areQueensSafe()) {
            final Logger logger = Logger.getLogger(NQueensSolver.class.getName());
            logger.info(String.format("Time to solve %d queens : %s",
                    chessboard.getNumberOfRows(),
                    Duration.between(started, ended)));
        }

    }
}
