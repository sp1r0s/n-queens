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

//    private NextBestMove getNextBestMoveByMovingInRightDiagonal(final Chessboard chessboard, final Coordinates queenToMove) {
//        final NextBestMove nextBestMove = new NextBestMove();
//        Chessboard copy = new Chessboard(chessboard);
//        Coordinates movingTarget = new Coordinates(queenToMove.getX(), queenToMove.getY());
//        for (int j = 0; j < copy.getNumberOfColumns(); j++) {
//            Coordinates targetCoordinates = new Coordinates(copy.getNumberOfRows() - 1, j);
//            if (copy.getQueensLocation().contains(targetCoordinates)
//                    || targetCoordinates.equals(queenToMove)) {
//                continue;
//            }
//            copy.move(movingTarget, targetCoordinates);
//
//            if (copy.areQueensSafe()) {
//                nextBestMove.setOptimalMove(targetCoordinates);
//                break;
//            } else if (copy.getConflicts().size() == 1) {
//                nextBestMove.addOneConflictMove(targetCoordinates);
//            }
//            movingTarget = targetCoordinates;
//        }
//        return nextBestMove;
//    }

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
