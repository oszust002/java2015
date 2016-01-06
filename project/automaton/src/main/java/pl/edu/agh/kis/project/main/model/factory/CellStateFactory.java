package pl.edu.agh.kis.project.main.model.factory;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.states.CellState;

/**
 * Created by Kamil on 23.11.2015.
 */
public interface CellStateFactory {
    CellState initialState(CellCoordinates coords);
}
