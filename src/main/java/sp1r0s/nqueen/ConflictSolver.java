package sp1r0s.nqueen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    private Coordinates findNextBestPosition(Chessboard chessboard, Coordinates coordinates) {
        final Chessboard copy = new Chessboard(chessboard);
        final List<Coordinates>  newCoordinatesWith1Conflict = new ArrayList<>();
        Coordinates movingTarget = new Coordinates(coordinates.getX(), coordinates.getY());
        for (int i = 0; i < copy.getNumberOfRows(); i++) {
            for (int j = 0; j < copy.getNumberOfColumns(); j++) {
                Coordinates targetCoordinates = new Coordinates(i, j);
                if (copy.getQueensLocation().contains(targetCoordinates)
                        || targetCoordinates.equals(coordinates)) {
                    continue;
                }
                copy.move(movingTarget, targetCoordinates);

                if (copy.areQueensSafe()) {
                    return targetCoordinates;
                } else if (copy.getConflicts().size() == 1) {
                    newCoordinatesWith1Conflict.add(targetCoordinates);
                }
                movingTarget = targetCoordinates;
            }
        }

        if (!newCoordinatesWith1Conflict.isEmpty()) {
            // Avoid potential infinite loops, by returning a random 1-conflict solution
            return newCoordinatesWith1Conflict.get(new Random().nextInt(newCoordinatesWith1Conflict.size()));
        }

        return null;
    }

}