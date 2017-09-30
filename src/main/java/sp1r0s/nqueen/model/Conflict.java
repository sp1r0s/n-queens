package sp1r0s.nqueen.model;

public class Conflict {
    private Coordinates a;
    private Coordinates b;

    public Conflict(Coordinates a, Coordinates b) {
        this.a = a;
        this.b = b;
    }

    public Coordinates getA() {
        return a;
    }

    public Coordinates getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conflict)) {
            return false;
        }
        Conflict conflict = (Conflict) o;
        return conflict.getA().equals(a) || conflict.getA().equals(b)
                && conflict.getB().equals(b) || conflict.getB().equals(a);
    }

    @Override
    public int hashCode() {
        return a.hashCode() + b.hashCode();
    }
}
