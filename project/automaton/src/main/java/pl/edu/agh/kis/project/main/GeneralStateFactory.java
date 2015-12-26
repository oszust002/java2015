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

    public GeneralStateFactory(CellState[][] initialStates){
        states = new TreeMap<CellCoordinates, CellState>();
        for(int i=0;i<initialStates.length;i++)
            for(int j=0;j<initialStates[0].length;j++)
                states.put(new Coords2D(j,i),initialStates[i][j]);
    }

    public CellState initialState(CellCoordinates coords){
        if(!(states.containsKey(coords)))
            throw new NoSuchElementException();
        return states.get(coords);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof GeneralStateFactory))
            return false;
        GeneralStateFactory second = (GeneralStateFactory) obj;
        return states.equals(second.states);
    }

}
