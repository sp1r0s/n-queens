package sp1r0s.nqueen.model;

public class Coordinates implements Comparable<Coordinates> {

    private int x;
    private int y;

    public Coordinates(int xCoord, int yCoord) {
        x = xCoord;
        y = yCoord;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(final Coordinates coordinates) {
        if (coordinates.getX() == x && coordinates.getY() == y) {
            return 0;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) {
            return false;
        }
        Coordinates coordinates = (Coordinates) o;
        return coordinates.getX() == x && coordinates.getY() == y;
    }

    @Override
    public int hashCode() {
        return 31 * String.valueOf(x+y).hashCode() + x + y;
    }
}