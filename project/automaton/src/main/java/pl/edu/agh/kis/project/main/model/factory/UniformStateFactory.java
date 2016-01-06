package pl.edu.agh.kis.project.main.model.factory;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.states.CellState;

/**
 * Implementation of {@link CellStateFactory} where all cells have one initial state
 * @author Kamil Osuch
 * @version 1.0
 */
public class UniformStateFactory implements CellStateFactory {
    private CellState state;

    /**
     * Creates state factory based on {@link CellState}
     * @param state initial state
     */
    public UniformStateFactory(CellState state){
        this.state = state;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public CellState initialState(CellCoordinates coords) {
        return state;
    }
}
