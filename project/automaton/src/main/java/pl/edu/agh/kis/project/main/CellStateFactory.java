package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.states.CellState;

/**
 * Created by Kamil on 23.11.2015.
 */
public interface CellStateFactory {
    CellState initialState(CellCoordinates coords);
}
