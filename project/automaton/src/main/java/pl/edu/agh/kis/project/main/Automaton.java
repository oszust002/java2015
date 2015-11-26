package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.states.CellState;

import java.util.Map;
import java.util.Set;

/**
 * Created by Kamil on 23.11.2015.
 */
public abstract class Automaton {

    private Map<CellCoordinates, CellState> Cells;
    CellNeighborhood neighborsStrategy;
    CellStateFactory stateFactory;

    public Automaton nextState(){
        return null;
    }

    public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> structure){

    }

    public CellIterator cellIterator(){
        return null;
    }

    protected abstract Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood);
    protected abstract boolean hasNextCoordinates(CellCoordinates coords);
    protected abstract CellCoordinates initialCoordinates(CellCoordinates coords);
    protected abstract CellCoordinates nextCoordinates(CellCoordinates coords);
    protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighborsStates);

    private Set<Cell> mapCoordinates(Set<CellCoordinates> coords){
        return null;
    }

    public class CellIterator{
        private CellCoordinates currentState;

        public boolean hasNext(){
            return false;
        }

        public Cell next(){
            return null;
        }

        public void setState(CellState state){

        }
    }
}
