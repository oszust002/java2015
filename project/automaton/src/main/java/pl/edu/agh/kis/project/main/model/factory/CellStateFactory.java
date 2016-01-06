package pl.edu.agh.kis.project.main.model.factory;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.states.CellState;

/**
 * Sets initial states for coords
 * @author Kamil Osuch
 * @version 1.0
 */
public interface CellStateFactory {
    /**
     * Sets initial state of cell on specified {@link CellCoordinates}
     * @param coords coords to set state
     * @return initial {@link CellState}
     */
    CellState initialState(CellCoordinates coords);
}
