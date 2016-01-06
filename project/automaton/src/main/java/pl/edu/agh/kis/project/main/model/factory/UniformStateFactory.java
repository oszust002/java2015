package pl.edu.agh.kis.project.main.model.factory;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.states.CellState;

/**
 * Created by Kamil on 26.12.2015.
 */
public class UniformStateFactory implements CellStateFactory {
    private CellState state;

    public UniformStateFactory(CellState state){
        this.state = state;
    }

    @Override
    public CellState initialState(CellCoordinates coords) {
        return state;
    }
}
