package sp1r0s.nqueen.oneconflict;

import sp1r0s.nqueen.model.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sp1r0s.nqueen.util.Utilities.getMostCentralCoordinate;

public class NextBestMove {
    private Coordinates optimalMove;
    private List<Coordinates> oneConflictMoves;

    NextBestMove() {
        this.oneConflictMoves = new ArrayList<>();
    }

    Optional<Coordinates> optimalMove() {
        return Optional.ofNullable(optimalMove);
    }

    void setOptimalMove(Coordinates optimalMove) {
        this.optimalMove = optimalMove;
    }

    Optional<Coordinates> bestOneConflictMove() {
        if (oneConflictMoves.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getMostCentralCoordinate(oneConflictMoves));
    }

    void addOneConflictMove(Coordinates oneConflictMove) {
        this.oneConflictMoves.add(oneConflictMove);
    }
}
