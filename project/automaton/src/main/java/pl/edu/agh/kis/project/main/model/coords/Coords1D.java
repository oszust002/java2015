package pl.edu.agh.kis.project.main.model.coords;

/**
 * Implementation of {@link CellCoordinates} for {@link pl.edu.agh.kis.project.main.model.automaton.Automaton1Dim}
 * @author Kamil Osuch
 * @version 1.0
 */
public class Coords1D implements CellCoordinates {
    public final int x;

    /**
     * Creates coordinates based on x value
     * @param x horizontal coordinate value
     */
    public Coords1D(int x) {
        this.x = x;
    }

    /**
     * Compares {@link Coords1D}
     * @param o {@link Coords1D} for compare
     * @return value lower than 0 if o is greater, greater than 0 if o is lower, 0 if equal
     */
    @Override
    public int compareTo(CellCoordinates o) {
        Coords1D second = (Coords1D) o;
        return x-second.x;
    }

    /**
     * Check equality of {@link Coords1D}
     * @param obj coords to check equality with
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Coords1D))
            return false;
        Coords1D second = (Coords1D) obj;
        return compareTo(second) == 0;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 17;
    }

    /**
     * Converts {@link Coords1D} to String
     * @return converted {@link Coords1D}
     */
    @Override
    public String toString(){
        return "["+x+"]";
    }
}
