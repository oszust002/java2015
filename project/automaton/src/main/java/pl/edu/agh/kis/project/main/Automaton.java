package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.states.CellState;

import java.util.*;

/**
 * Created by Kamil on 23.11.2015.
 */
public abstract class Automaton implements Iterable<Cell>{

    private TreeMap<CellCoordinates, CellState> cells;
    private CellNeighborhood neighborsStrategy;
    private CellStateFactory stateFactory;

    public Automaton(CellNeighborhood neighboursStrategy, CellStateFactory stateFactory){
        this.neighborsStrategy = neighboursStrategy;
        this.stateFactory = stateFactory;
        cells = new TreeMap<CellCoordinates, CellState>();
    }
    //TODO double iterator(foreach for old automaton, normal iterator for newCellAutomaton
    public Automaton nextState(){
        Automaton newCellAutomaton = newInstance(stateFactory,neighborsStrategy);
        for (CellIterator it=new CellIterator();it.hasNext();) {
            Cell c = it.next();
            Set<CellCoordinates> neighbours = neighborsStrategy.cellNeighbors(c.coords);
            Set<Cell> mappedNeighbours = mapCoordinates(neighbours);
            CellState newState = nextCellState(c.state, mappedNeighbours);
            it.setState(newState);
        }
        return newCellAutomaton;
    }

    public void insertStructure(TreeMap<? extends CellCoordinates, ? extends CellState> structure){
        cells.putAll(structure);
    }

    @Override
    public CellIterator iterator(){
        return new CellIterator();
    }

    protected abstract Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood);
    protected abstract boolean hasNextCoordinates(CellCoordinates coords);
    protected abstract CellCoordinates initialCoordinates();
    protected abstract CellCoordinates nextCoordinates(CellCoordinates coords);
    protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighborsStates);

    private Set<Cell> mapCoordinates(Set<CellCoordinates> coords){
        TreeSet<Cell> cellsSet = new TreeSet<Cell>();
        for(CellCoordinates c: coords){
            cellsSet.add(new Cell(cells.get(c),c));
        }
        return cellsSet;
    }

    public class CellIterator implements Iterator<Cell> {
        private CellCoordinates currentState;

        public CellIterator() {
            currentState = initialCoordinates();
        }

        public void setState(CellState state) {
            cells.put(currentState,state);
        }

        @Override
        public boolean hasNext() {
            return hasNextCoordinates(currentState);
        }

        @Override
        public Cell next() {
            if (!hasNext())
                throw new NoSuchElementException();
            currentState=nextCoordinates(currentState);
            return new Cell(cells.get(currentState),currentState);
        }
    }
}
