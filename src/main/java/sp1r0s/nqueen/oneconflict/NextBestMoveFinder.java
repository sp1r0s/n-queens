package sp1r0s.nqueen.oneconflict;

import sp1r0s.nqueen.model.Chessboard;
import sp1r0s.nqueen.model.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sp1r0s.nqueen.util.Utilities.getMostCentralCoordinate;


class NextBestMoveFinder {

    Optional<Coordinates> find(final Chessboard chessboard, final Coordinates queenToMove) {

        final NextBestMove nextBestMoveByMovingUpAndDown = getNextBestMoveByMovingUpAndDown(chessboard, queenToMove);
        if (nextBestMoveByMovingUpAndDown.optimalMove().isPresent()) {
            return Optional.of(nextBestMoveByMovingUpAndDown.optimalMove().get());
        }

        final NextBestMove nextBestMoveByMovingLeftAndRight = getNextBestMoveByMovingLeftAndRight(chessboard, queenToMove);
        if (nextBestMoveByMovingLeftAndRight.optimalMove().isPresent()) {
            return Optional.of(nextBestMoveByMovingLeftAndRight.optimalMove().get());
        }

//        final NextBestMove nextBestMoveByMovingInRightDiagonal = getNextBestMoveByMovingInRightDiagonal(chessboard, queenToMove);
//        if (nextBestMoveByMovingInRightDiagonal.optimalMove().isPresent()) {
//            return Optional.of(nextBestMoveByMovingInRightDiagonal.optimalMove().get());
//        }
//
//        final NextBestMove nextBestMoveByMovingInLeftDiagonal = getNextBestMoveByMovingInLeftDiagonal(chessboard, queenToMove);
//        if (nextBestMoveByMovingInLeftDiagonal.optimalMove().isPresent()) {
//            return Optional.of(nextBestMoveByMovingInLeftDiagonal.optimalMove().get());
//        }

        return getNextBest1ConflictMove(nextBestMoveByMovingUpAndDown,
                                        nextBestMoveByMovingLeftAndRight);
    }

    private NextBestMove getNextBestMoveByMovingUpAndDown(final Chessboard chessboard, final Coordinates queenToMove) {
        final NextBestMove nextBestMove = new NextBestMove();
        Chessboard copy = new Chessboard(chessboard);
        Coordinates movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
        for (int i = 0; i < copy.getNumberOfRows(); i++) {
            Coordinates targetCoordinates = new Coordinates(i, queenToMove.getY());
            if (copy.getQueensLocation().contains(targetCoordinates)
                    || targetCoordinates.equals(queenToMove)) {
                continue;
            }
            copy.move(movingTarget, targetCoordinates);

            if (copy.areQueensSafe()) {
                nextBestMove.setOptimalMove(targetCoordinates);
                break;
            } else if (copy.getConflicts().size() == 1) {
                nextBestMove.addOneConflictMove(targetCoordinates);
            }
            movingTarget = targetCoordinates;
        }
        return nextBestMove;
    }

    private NextBestMove getNextBestMoveByMovingLeftAndRight(final Chessboard chessboard, final Coordinates queenToMove) {
        final NextBestMove nextBestMove = new NextBestMove();
        Chessboard copy = new Chessboard(chessboard);
        Coordinates movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
        for (int j = 0; j < copy.getNumberOfColumns(); j++) {
            Coordinates targetCoordinates = new Coordinates(queenToMove.getX(), j);
            if (copy.getQueensLocation().contains(targetCoordinates)
                    || targetCoordinates.equals(queenToMove)) {
                continue;
            }
            copy.move(movingTarget, targetCoordinates);

            if (copy.areQueensSafe()) {
                nextBestMove.setOptimalMove(targetCoordinates);
                break;
            } else if (copy.getConflicts().size() == 1) {
                nextBestMove.addOneConflictMove(targetCoordinates);
            }
            movingTarget = targetCoordinates;
        }
        return nextBestMove;
    }

    private NextBestMove getNextBestMoveByMovingInRightDiagonal(final Chessboard chessboard, final Coordinates queenToMove) {
        final NextBestMove nextBestMove = new NextBestMove();
        Chessboard copy = new Chessboard(chessboard);
        Coordinates movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
        int i = movingTarget.getX();
        int j = movingTarget.getY();
        while(i < chessboard.getNumberOfRows() - 1 && j > 0) {
            i = i + 1;
            j = j - 1;
        }

        while(i > 0 && j < copy.getNumberOfColumns() - 1) {
            Coordinates targetCoordinates = new Coordinates(i, j);
            if (!copy.getQueensLocation().contains(targetCoordinates)
                    && !targetCoordinates.equals(queenToMove)) {

                copy.move(movingTarget, targetCoordinates);

                if (copy.areQueensSafe()) {
                    nextBestMove.setOptimalMove(targetCoordinates);
                    break;
                } else if (copy.getConflicts().size() == 1) {
                    nextBestMove.addOneConflictMove(targetCoordinates);
                }
                movingTarget = targetCoordinates;
            }

            i = i - 1;
            j = j + 1;
        }
        return nextBestMove;
    }

    private NextBestMove getNextBestMoveByMovingInLeftDiagonal(final Chessboard chessboard, final Coordinates queenToMove) {
        final NextBestMove nextBestMove = new NextBestMove();
        Chessboard copy = new Chessboard(chessboard);
        Coordinates movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
        int i = movingTarget.getX();
        int j = movingTarget.getY();
        while(i > 0 && j > 0) {
            i = i - 1;
            j = j - 1;
        }
        while(i < copy.getNumberOfRows() -1 && j < copy.getNumberOfColumns() - 1) {
            Coordinates targetCoordinates = new Coordinates(i, j);
            if (!copy.getQueensLocation().contains(targetCoordinates)
                    && !targetCoordinates.equals(queenToMove)) {

                copy.move(movingTarget, targetCoordinates);

                if (copy.areQueensSafe()) {
                    nextBestMove.setOptimalMove(targetCoordinates);
                    break;
                } else if (copy.getConflicts().size() == 1) {
                    nextBestMove.addOneConflictMove(targetCoordinates);
                }
                movingTarget = targetCoordinates;
            }

            i = i + 1;
            j = j + 1;
        }
        return nextBestMove;
    }

    private Optional<Coordinates> getNextBest1ConflictMove(NextBestMove... moves) {
        List<Coordinates> bestOneConflictMoves = new ArrayList<>();
        for (NextBestMove move : moves) {
            if (move.bestOneConflictMove().isPresent()) {
                bestOneConflictMoves.add(move.bestOneConflictMove().get());
            }
        }

        if (!bestOneConflictMoves.isEmpty()) {
            return Optional.of(getMostCentralCoordinate(bestOneConflictMoves));
        }
        return Optional.empty();
    }

}
