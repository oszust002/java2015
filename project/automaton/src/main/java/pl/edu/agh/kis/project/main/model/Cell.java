package pl.edu.agh.kis.project.main.model;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.states.CellState;

/**
 * Merged {@link CellCoordinates} and {@link CellState} into one field
 * @author Kamil Osuch
 * @version 1.0
 */
public class Cell implements Comparable<Cell>{
    public final CellState state;
    public final CellCoordinates coords;

    /**
     * Creates new immutable cell
     * @param state state of cell
     * @param coords coordinates of cell
     */
    public Cell(CellState state, CellCoordinates coords) {
        this.state = state;
        this.coords = coords;
    }

    /**
     * Compares {@link Cell}
     * @param second {@link Cell} for compare
     * @return value lower than 0 if second is greater, greater than 0 if second is lower, 0 if equal
     */
    @Override
    public int compareTo(Cell second) {
        return coords.compareTo(second.coords);
    }

    /**
     * Check equality of two {@link Cell} objects
     * @param obj second {@link Cell} to check equality
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Cell))
            return false;
        Cell casted = (Cell) obj;
        return casted.coords.equals(coords) && casted.state.equals(state);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 53;
    }
}
