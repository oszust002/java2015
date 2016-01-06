package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.neighbourhood.Cell2DimNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.VonNeumanNeighborhood;
import pl.edu.agh.kis.project.main.model.states.*;

import java.util.*;

/**
 * Created by Kamil on 23.11.2015.
 */
public abstract class Automaton implements Iterable<Cell>, Cloneable{

    private TreeMap<CellCoordinates, CellState> cells;
    private CellNeighborhood neighborsStrategy;
    private CellStateFactory stateFactory;

    public Automaton(CellNeighborhood neighboursStrategy, CellStateFactory stateFactory){
        this.neighborsStrategy = neighboursStrategy;
        this.stateFactory = stateFactory;
        cells = new TreeMap<CellCoordinates, CellState>();
    }

    public Automaton nextState(){
        Automaton newCellAutomaton = newInstance(stateFactory,neighborsStrategy);
        CellIterator newAutomatonIterator = newCellAutomaton.iterator();
        for (Cell c: this) {
            newAutomatonIterator.next();
            Set<CellCoordinates> neighbours = neighborsStrategy.cellNeighbors(c.coords);
            Set<Cell> mappedNeighbours = mapCoordinates(neighbours);
            CellState newState = nextCellState(c, mappedNeighbours);
            newAutomatonIterator.setState(newState);
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
    protected abstract CellState nextCellState(Cell current, Set<Cell> neighborsStates);
    public abstract Class<? extends CellState> getCellStateClass();
    public abstract void resize(int width, int height);

    protected CellNeighborhood getNeighbourhood(){
        return neighborsStrategy;
    }

    protected void removeCell(CellCoordinates coordinates){
        cells.remove(coordinates);
    }

    protected CellState getCellState(CellCoordinates coordinates){
        if(cells.containsKey(coordinates)){
            return cells.get(coordinates);
        }
        else
            return null;
    }

    public void advanceCellState(CellCoordinates coordinates){
        CellState cellState = getCellState(coordinates);
        if(cellState != null){
            cells.put(coordinates,cellState.next());
        }
        else
            throw new AssertionError("Coords not exist");
    }

    protected  void putNewCell(CellCoordinates coordinates){
        CellState dead = null;
        Class<? extends CellState> cellStateClass = getCellStateClass();
        if(cellStateClass.equals(BinaryState.class))
            dead = BinaryState.DEAD;
        else if(cellStateClass.equals(QuadState.class))
            dead = QuadState.DEAD;
        else if(cellStateClass.equals(WireElectronState.class))
            dead = WireElectronState.VOID;
        else if(cellStateClass.equals(LangtonCell.class))
            dead = new LangtonCell(BinaryState.DEAD);
        cells.put(coordinates,dead);
    }

    public String getNeighborhoodName(){
        if(neighborsStrategy.getClass().equals(VonNeumanNeighborhood.class))
            return "VonNeumann";
        if(neighborsStrategy.getClass().equals(MoorNeighbourhood.class))
            return "Moore";
        else
            return "";
    }

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

    public boolean getWrap(){
        return neighborsStrategy.getWrap();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Automaton))
            return false;
        Automaton second = (Automaton)obj;
        if(!(neighborsStrategy.equals(second.neighborsStrategy)))
            return false;
        //todo: add state factory equality(problem: comparing automatons in tests)
        CellIterator objIterator = second.iterator();
        for(Cell c: this){
            Cell objCell = objIterator.next();
            if(!c.equals(objCell))
                return false;
        }
        return !objIterator.hasNext();
    }

    public void setWrap(boolean wrap){
        neighborsStrategy.setWrap(wrap);
    }

    public void set2DimNeighborsStrategy(Cell2DimNeighbourhood newNeighbourhood){
        if(neighborsStrategy instanceof Cell2DimNeighbourhood){
            Cell2DimNeighbourhood oldNeighbourhood = (Cell2DimNeighbourhood) neighborsStrategy;
            int radius = oldNeighbourhood.getRadius();
            neighborsStrategy = newNeighbourhood;
            newNeighbourhood.setRadius(radius);
        }
    }

    public void changeRadius(int r){
        if(neighborsStrategy instanceof Cell2DimNeighbourhood)
            ((Cell2DimNeighbourhood) neighborsStrategy).setRadius(r);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }

    protected void setCell(CellCoordinates coordinates,CellState state){
        cells.put(coordinates,state);
    }

    @Override
    public Object clone(){
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Automaton duplicate = newInstance(stateFactory,neighborsStrategy);
        duplicate.cells = new TreeMap<CellCoordinates,CellState>(cells);
        return duplicate;
    }
}

