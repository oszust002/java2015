package pl.edu.agh.kis.project.main.model;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.states.CellState;

/**
 * Created by Kamil on 23.11.2015.
 */
public class Cell implements Comparable<Cell>{
    public final CellState state;
    public final CellCoordinates coords;

    public Cell(CellState state, CellCoordinates coords) {
        this.state = state;
        this.coords = coords;
    }

    @Override
    public int compareTo(Cell second) {
        return coords.compareTo(second.coords);
    }

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
