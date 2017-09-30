package sp1r0s.nqueen;

public class Move {
    private Coordinates from;
    private Coordinates to;

    public Move(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }

    public Coordinates getFrom() {
        return from;
    }

    public Coordinates getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) {
            return false;
        }
        Move move = (Move) o;
        return move.getFrom().equals(from) && move.getTo().equals(to);
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }
}
