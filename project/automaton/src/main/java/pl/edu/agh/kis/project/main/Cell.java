package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.states.CellState;

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
}
