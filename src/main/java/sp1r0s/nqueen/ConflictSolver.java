package sp1r0s.nqueen;

import java.util.*;

public class ConflictSolver implements NQueensSolver {

    @Override
    public void placeQueensInInitialPosition(final Chessboard chessboard) {
        placeQueens(chessboard, 0);
        if (chessboard.getConflicts().size() > 1) {
            chessboard.reset();
            placeQueens(chessboard, 1);
        }
    }

    @Override
    public void calibrate(final Chessboard chessboard) {
        Set<Conflict> conflicts = chessboard.getConflicts();
        if (conflicts.size() == 1) {
            final Conflict conflict = conflicts.iterator().next();
            Coordinates nextBestPosition = findNextBestPosition(chessboard, conflict.getA());
            if (nextBestPosition != null) {
                chessboard.move(conflict.getA(), nextBestPosition);
            }
        }
        while (!chessboard.areQueensSafe()) {
            conflicts = chessboard.getConflicts();
            if (conflicts.size() == 1) {
                final Conflict conflict = conflicts.iterator().next();
                Coordinates nextBestPosition = findNextBestPosition(chessboard, conflict.getB());

                if (nextBestPosition != null) {
                    chessboard.move(conflict.getB(), nextBestPosition);
                } else {
                    nextBestPosition = findNextBestPosition(chessboard, conflict.getA());
                    if (nextBestPosition != null) {
                        chessboard.move(conflict.getA(), nextBestPosition);
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    private void placeQueens(Chessboard chessboard, int startingRowIndex) {
        int numberOfColumns = chessboard.getNumberOfColumns();

        int i = startingRowIndex;
        int j = 0;
        while(j < numberOfColumns / 2) {
            chessboard.placeQueen(i, j);
            i = i + 2;
            j = j + 1;
        }

        if (startingRowIndex == 0) {
            i = startingRowIndex + 1;
        } else if (startingRowIndex == 1) {
            i = 0;
        }
        while(i < numberOfColumns && j < numberOfColumns) {
            chessboard.placeQueen(i, j);
            i = i + 2;
            j = j + 1;
        }
    }

    private Coordinates findNextBestPosition(Chessboard chessboard, Coordinates queenToMove) {
        Chessboard copy = new Chessboard(chessboard);
        Coordinates movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
        List<Coordinates> next1ConflictTargets = new ArrayList<>();
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

        if (!next1ConflictTargets.isEmpty()) {
            // Avoid potential infinite loops, by returning a random 1-conflict solution
            Coordinates returnTarget = next1ConflictTargets.get(0);
            for (Coordinates next1ConflictTarget : next1ConflictTargets) {
                if (Math.abs(next1ConflictTarget.getX() - next1ConflictTarget.getY()) < Math.abs(returnTarget.getX() - returnTarget.getY())) {
                    returnTarget = next1ConflictTarget;
                }
            }
            return returnTarget;
        }

        return null;
    }

}
