package pl.edu.agh.kis.project.main.model.coords;

/**
 * Implementation of {@link CellCoordinates} for {@link pl.edu.agh.kis.project.main.model.automaton.Automaton2Dim}
 * @author Kamil Osuch
 * @version 1.0
 */
public class Coords2D implements CellCoordinates {
    public final int x;
    public final int y;

    /**
     * Creates coords based on horizontal and vertical position
     * @param x horizontal position
     * @param y vertical position
     */
    public Coords2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compares {@link Coords2D}
     * @param o {@link Coords2D} for compare
     * @return value lower than 0 if o is greater, greater than 0 if o is lower, 0 if equal
     */
    @Override
    public int compareTo(CellCoordinates o) {
        Coords2D second = (Coords2D) o;
        if(y>second.y)
            return 1;
        else if(y < second.y)
            return -1;
        else
            return x-second.x;
    }

    /**
     * Converts {@link Coords2D} to String
     * @return converted {@link Coords2D}
     */
    @Override
    public String toString() {
        return "["+x+", " + y +"]";
    }

    /**
     * Check equality of {@link Coords2D}
     * @param obj coords to check equality with
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Coords2D))
            return false;
        Coords2D second = (Coords2D) obj;
        return compareTo(second) == 0;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 18;
    }
}
