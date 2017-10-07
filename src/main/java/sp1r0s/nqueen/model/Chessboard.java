package sp1r0s.nqueen.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Chessboard {

//    private static final Logger LOGGER = Logger.getLogger(Chessboard.class.getName());

    private static final String QUEEN_MARK = "Q";

    private final Set<Coordinates> queensLocation;
    private String[][] squares;
    private int numberOfRows;
    private int numberOfColumns;

    public Chessboard(int numberOfRows, int numberOfColumns) {
        squares = new String[numberOfRows][numberOfColumns];
        queensLocation = new LinkedHashSet<>();
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public Chessboard(final Chessboard chessboard) {
        this.numberOfRows = chessboard.getNumberOfRows();
        this.numberOfColumns = chessboard.getNumberOfColumns();
        this.squares = new String[this.numberOfRows][this.numberOfColumns];
        this.queensLocation = new LinkedHashSet<>();
        for (Coordinates coordinates : chessboard.getQueensLocation()) {
            placeQueen(coordinates.getX(), coordinates.getY());
        }
    }

    public boolean placeQueen(int x, int y) {
        if (squares[x][y] != null && squares[x][y].equals(QUEEN_MARK)) {
            return false;
        }
        squares[x][y] = QUEEN_MARK;
        queensLocation.add(new Coordinates(x, y));
        return true;
    }

    public boolean areQueensSafe() {
        final Set<Conflict> conflicts = new HashSet<>();

        for (Coordinates coordinates : queensLocation) {
            checkDown(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
            checkUp(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
            checkLeft(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
            checkRight(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
            checkUpLeftDiagonal(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
            checkDownLeftDiagonal(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
            checkUpRightDiagonal(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
            checkDownRightDiagonal(coordinates, conflicts);
            if (!conflicts.isEmpty()) {
                return false;
            }
        }

        return true;

//        if (!areSafe) {
//            for (Conflict conflict : conflicts) {
//                LOGGER.log(Level.SEVERE, String.format("Queen in position [%d,%d] threatens queen in position [%d,%d]",
//                        conflict.getB().getX(),
//                        conflict.getB().getY(),
//                        conflict.getA().getX(),
//                        conflict.getA().getY()));
//            }
//        }
    }

    public Set<Conflict> getConflicts() {

        final Set<Conflict> conflicts = new HashSet<>();

        for (Coordinates coordinates : queensLocation) {
            checkDown(coordinates, conflicts);
            checkUp(coordinates, conflicts);
            checkLeft(coordinates, conflicts);
            checkRight(coordinates, conflicts);
            checkUpLeftDiagonal(coordinates, conflicts);
            checkDownLeftDiagonal(coordinates, conflicts);
            checkUpRightDiagonal(coordinates, conflicts);
            checkDownRightDiagonal(coordinates, conflicts);
        }

        return conflicts;
    }

    public void reset() {
        squares = new String[numberOfRows][numberOfColumns];
        queensLocation.clear();
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public Set<Coordinates> getQueensLocation() {
        return queensLocation;
    }

    public void move(Coordinates from, Coordinates to) throws IllegalStateException{
        if (squares[to.getX()][to.getY()] != null) {
            throw new IllegalStateException();
        }
        squares[from.getX()][from.getY()] = null;
        queensLocation.remove(from);
        placeQueen(to.getX(), to.getY());
    }

    private void checkDown(final Coordinates coordinates, final Set<Conflict> conflicts) {
        for (int i = coordinates.getX() + 1; i < squares[coordinates.getX()].length; i++) {
            if (squares[i][coordinates.getY()] != null) {
                conflicts.add(new Conflict(new Coordinates(i, coordinates.getY()), coordinates));
            }
        }
    }

    private void checkUp(final Coordinates coordinates, final Set<Conflict> conflicts) {
        for (int i = coordinates.getX() - 1; i >= 0; i--) {
            if (squares[i][coordinates.getY()] != null) {
                conflicts.add(new Conflict(new Coordinates(i, coordinates.getY()), coordinates));
            }
        }
    }

    private void checkLeft(final Coordinates coordinates, final Set<Conflict> conflicts) {
        for (int i = coordinates.getY() - 1; i >= 0; i--) {
            if (squares[coordinates.getX()][i] != null) {
                conflicts.add(new Conflict(new Coordinates(coordinates.getX(), i), coordinates));
            }
        }
    }

    private void checkRight(final Coordinates coordinates, final Set<Conflict> conflicts) {
        for (int i = coordinates.getY() + 1; i < squares[coordinates.getX()].length; i++) {
            if (squares[coordinates.getX()][i] != null) {
                conflicts.add(new Conflict(new Coordinates(coordinates.getX(), i), coordinates));
            }
        }
    }

    private void checkUpLeftDiagonal(final Coordinates coordinates, final Set<Conflict> conflicts) {
        int i = coordinates.getX() - 1;
        int j = coordinates.getY() - 1;
        while(i >= 0 && j >= 0) {
            if (squares[i][j] != null) {
                conflicts.add(new Conflict(new Coordinates(i, j), coordinates));
            }
            i--;
            j--;
        }
    }

    private void checkDownLeftDiagonal(final Coordinates coordinates, final Set<Conflict> conflicts) {
        int i = coordinates.getX() + 1;
        int j = coordinates.getY() - 1;
        while(i < getNumberOfRows() && j >= 0) {
            if (squares[i][j] != null) {
                conflicts.add(new Conflict(new Coordinates(i, j), coordinates));
            }
            i++;
            j--;
        }
    }

    private void checkUpRightDiagonal(final Coordinates coordinates, final Set<Conflict> conflicts) {
        int i = coordinates.getX() - 1;
        int j = coordinates.getY() + 1;
        while(i >= 0 && j < getNumberOfColumns()) {
            if (squares[i][j] != null) {
                conflicts.add(new Conflict(new Coordinates(i, j), coordinates));
            }
            i--;
            j++;
        }
    }

    private void checkDownRightDiagonal(final Coordinates coordinates, final Set<Conflict> conflicts) {
        int i = coordinates.getX() + 1;
        int j = coordinates.getY() + 1;
        while(i < getNumberOfRows() && j < getNumberOfColumns()) {
            if (squares[i][j] != null) {
                conflicts.add(new Conflict(new Coordinates(i, j), coordinates));
            }
            i++;
            j++;
        }
    }

}
