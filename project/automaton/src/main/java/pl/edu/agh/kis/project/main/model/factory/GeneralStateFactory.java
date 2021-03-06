package pl.edu.agh.kis.project.main.model.factory;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.coords.Coords1D;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.states.CellState;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Implementation of {@link CellStateFactory} where initial states are held in container
 * @author Kamil Osuch
 * @version 1.0
 */
public class GeneralStateFactory implements CellStateFactory {
    private TreeMap<CellCoordinates, CellState> states;

    /**
     * Creates state factory based on map of {@link CellCoordinates} and {@link CellState}
     * @param map map of {@link CellCoordinates} and {@link CellState}
     */
    public GeneralStateFactory(Map<CellCoordinates,CellState> map){
        states = new TreeMap<CellCoordinates,CellState>(map);
    }

    /**
     * Creates state factory based on array. Used for {@link pl.edu.agh.kis.project.main.model.automaton.Automaton1Dim}
     * inital states
     * @param initialStates array of initial states
     */
    public GeneralStateFactory(CellState[] initialStates){
        states = new TreeMap<CellCoordinates, CellState>();
        for(int i=0;i<initialStates.length;i++){
            states.put(new Coords1D(i),initialStates[i]);
        }
    }

    /**
     * Creates state factory based on array. Used for {@link pl.edu.agh.kis.project.main.model.automaton.Automaton2Dim}
     * inital states
     * @param initialStates array of initial states
     */
    public GeneralStateFactory(CellState[][] initialStates){
        states = new TreeMap<CellCoordinates, CellState>();
        for(int i=0;i<initialStates.length;i++)
            for(int j=0;j<initialStates[0].length;j++)
                states.put(new Coords2D(j,i),initialStates[i][j]);
    }

    /**
     *{@inheritDoc}
     */
    public CellState initialState(CellCoordinates coords){
        if(!(states.containsKey(coords)))
            throw new NoSuchElementException();
        return states.get(coords);
    }

}
