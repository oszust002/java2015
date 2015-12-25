package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.states.CellState;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Created by Kamil on 09.12.2015.
 */
public class GeneralStateFactory implements CellStateFactory{
    private TreeMap<CellCoordinates, CellState> states;

    public GeneralStateFactory(Map<CellCoordinates,CellState> map){
        states = new TreeMap<CellCoordinates,CellState>(map);
    }

    public CellState initialState(CellCoordinates coords){
        if(!(states.containsKey(coords)))
            throw new NoSuchElementException();
        return states.get(coords);
    }

}
