package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.states.CellState;

/**
 * Created by Kamil on 26.12.2015.
 */
public class UniformStateFactory implements CellStateFactory{
    private CellState state;

    public UniformStateFactory(CellState state){
        this.state = state;
    }

    @Override
    public CellState initialState(CellCoordinates coords) {
        return state;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof UniformStateFactory))
            return false;
        UniformStateFactory second = (UniformStateFactory) obj;
        return state.equals(second.state);

    }
}
