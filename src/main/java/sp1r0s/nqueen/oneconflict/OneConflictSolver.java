package sp1r0s.nqueen.oneconflict;

import sp1r0s.nqueen.NQueensSolver;
import sp1r0s.nqueen.model.Chessboard;
import sp1r0s.nqueen.model.Conflict;
import sp1r0s.nqueen.model.Coordinates;

import java.util.Optional;
import java.util.Set;

public class OneConflictSolver implements NQueensSolver {

    private static final int START_FROM_FIRST_ROW = 0;
    private static final int START_FROM_SECOND_ROW = 1;

    @Override
    public void placeQueensInInitialPosition(final Chessboard chessboard) {
        placeQueens(chessboard, START_FROM_FIRST_ROW);
        if (chessboard.getConflicts().size() != 1) {
            chessboard.reset();
            placeQueens(chessboard, START_FROM_SECOND_ROW);
        }
    }

    @Override
    public void calibrate(final Chessboard chessboard) {
        final NextBestMoveFinder finder = new NextBestMoveFinder();
        Set<Conflict> conflicts;
        while (chessboard.getConflicts().size() == 1) {
            conflicts = chessboard.getConflicts();
            final Conflict conflict = conflicts.iterator().next();
            Optional<Coordinates> nextBestPositionOptional = finder.find(chessboard, conflict.getB());
            if (nextBestPositionOptional.isPresent()) {
                chessboard.move(conflict.getB(), nextBestPositionOptional.get());
            } else {
                nextBestPositionOptional = finder.find(chessboard, conflict.getA());
                if (nextBestPositionOptional.isPresent()) {
                    chessboard.move(conflict.getA(), nextBestPositionOptional.get());
                } else {
                    break;
                }
            }
        }
    }

    private void placeQueens(final Chessboard chessboard, final int startingRowIndex) {
        int numberOfRows = chessboard.getNumberOfRows();
        int numberOfColumns = chessboard.getNumberOfColumns();

        int i = startingRowIndex;
        int j = 0;
        while(j < numberOfColumns / 2) {
            chessboard.placeQueen(i, j);
            i = i + 2;
            j = j + 1;
        }

        i = (startingRowIndex == START_FROM_FIRST_ROW) ? START_FROM_SECOND_ROW : START_FROM_FIRST_ROW;
        while(i < numberOfRows && j < numberOfColumns) {
            chessboard.placeQueen(i, j);
            i = i + 2;
            j = j + 1;
        }
    }

}
