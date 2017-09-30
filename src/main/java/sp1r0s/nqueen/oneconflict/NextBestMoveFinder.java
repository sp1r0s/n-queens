package sp1r0s.nqueen.oneconflict;

import sp1r0s.nqueen.model.Chessboard;
import sp1r0s.nqueen.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

class NextBestMoveFinder {

    Coordinates find(final Chessboard chessboard, final Coordinates queenToMove) {

        final List<Coordinates> next1ConflictTargets = new ArrayList<>();
        Chessboard copy;
        Coordinates movingTarget;

        // Try moving up or down
        copy = new Chessboard(chessboard);
        movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
        for (int i = 0; i < copy.getNumberOfRows(); i++) {
            Coordinates targetCoordinates = new Coordinates(i, queenToMove.getY());
            if (copy.getQueensLocation().contains(targetCoordinates)
                    || targetCoordinates.equals(queenToMove)) {
                continue;
            }
            copy.move(movingTarget, targetCoordinates);

            if (copy.areQueensSafe()) {
                return targetCoordinates;
            } else if (copy.getConflicts().size() == 1) {
                next1ConflictTargets.add(targetCoordinates);
            }
            movingTarget = targetCoordinates;
        }

        // Try moving left or right
        copy = new Chessboard(chessboard);
        movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
        for (int j = 0; j < copy.getNumberOfColumns(); j++) {
            Coordinates targetCoordinates = new Coordinates(queenToMove.getX(), j);
            if (copy.getQueensLocation().contains(targetCoordinates)
                    || targetCoordinates.equals(queenToMove)) {
                continue;
            }
            copy.move(movingTarget, targetCoordinates);

            if (copy.areQueensSafe()) {
                return targetCoordinates;
            } else if (copy.getConflicts().size() == 1) {
                next1ConflictTargets.add(targetCoordinates);
            }
            movingTarget = targetCoordinates;
        }

        // Decide on next best move
        if (!next1ConflictTargets.isEmpty()) {
            /*
             * Avoid potential infinite loops, by returning a move to a position
             * closest to the center of the board.
             */
            return getNextBest1ConflictMove(next1ConflictTargets);
        }

        return null;
    }

    private Coordinates getNextBest1ConflictMove(final List<Coordinates> next1ConflictTargets) {
        Coordinates returnTarget = next1ConflictTargets.get(0);
        for (Coordinates next1ConflictTarget : next1ConflictTargets) {
            if (Math.abs(next1ConflictTarget.getX() - next1ConflictTarget.getY()) < Math.abs(returnTarget.getX() - returnTarget.getY())) {
                returnTarget = next1ConflictTarget;
            }
        }
        return returnTarget;
    }

}
