package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.exceptions.InvalidCoordsException;
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
 *
 * Main class of application, every Automaton have to extend it.
 * @author Kamil Osuch
 * @version 1.0
 *
 */
public abstract class Automaton implements Iterable<Cell>, Cloneable{

    private TreeMap<CellCoordinates, CellState> cells;
    private CellNeighborhood neighborsStrategy;
    private CellStateFactory stateFactory;

    /**
     * Creates Automaton with specified {@link CellNeighborhood} and {@link CellStateFactory}
     * @param neighboursStrategy strategy of getting neighbours of cell
     * @param stateFactory factory of initial states of cells
     */
    public Automaton(CellNeighborhood neighboursStrategy, CellStateFactory stateFactory){
        this.neighborsStrategy = neighboursStrategy;
        this.stateFactory = stateFactory;
        cells = new TreeMap<CellCoordinates, CellState>();
    }

    /**
     * Creates new instance of Automaton advanced by single tick
     * @return New Automaton advanced by single tick
     */
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

    /**
     * Sets specified cells in Automaton
     * @param structure Map of CellCoordinates and CellState that corresponds to them.
     * @see CellCoordinates
     * @see CellState
     */
    public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> structure){
        cells.putAll(structure);
    }

    /**
     *
     * @return {@link CellIterator} which iterates through all {@link Cell} in Automaton
     */
    @Override
    public CellIterator iterator(){
        return new CellIterator();
    }

    /**
     * Creates new instance of concrete Automaton.
     * Does not copy cell states from old Automaton.
     * @param factory old {@link CellStateFactory} which determines initial {@link CellState}
     * @param neighbourhood old {@link CellNeighborhood} which determines neighbours of cell
     * @return new instance of concrete Automaton
     */
    protected abstract Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood);

    /**
     * Checks if next {@link CellCoordinates} exist. Used by {@link CellIterator}
     * @param coords current coordinates
     * @return True if next coordinates exist, False otherwise
     */
    protected abstract boolean hasNextCoordinates(CellCoordinates coords);

    /**
     * Used by {@link CellIterator} to get coordinates before first.
     * @return CellCoordinates before first coordinates in Automaton
     */
    protected abstract CellCoordinates initialCoordinates();

    /**
     * Used by {@link CellIterator} to get next {@link CellCoordinates}
     * @param coords current coordinates
     * @return CellCoordinates following given coordinates
     */
    protected abstract CellCoordinates nextCoordinates(CellCoordinates coords);

    /**
     * Calculates new {@link CellState} based on current cell and its neighbours
     * @param current currently considered cell
     * @param neighborsStates set of currently considered cell neighbours
     * @return new CellState of current cell
     */
    protected abstract CellState nextCellState(Cell current, Set<Cell> neighborsStates);

    /**
     * Gets class of CellState used in current Automaton
     * @return cell state class
     */
    public abstract Class<? extends CellState> getCellStateClass();

    /**
     * Changes size of current Automaton
     * @param width new width of Automaton
     * @param height new height of Automaton(used only in {@link Automaton2Dim})
     */
    public abstract void resize(int width, int height);

    /**
     * Gets {@link CellNeighborhood} used in current Automaton
     * @return
     */
    protected CellNeighborhood getNeighbourhood(){
        return neighborsStrategy;
    }

    /**
     * Removes cell on specified {@link CellCoordinates}
     * @param coordinates coordinates of the removed cell
     */
    protected void removeCell(CellCoordinates coordinates){
        cells.remove(coordinates);
    }

    /**
     * Gets {@link CellState} from specified coordinates
     * @param coordinates coordinates of cell with searched CellState
     * @return CellState if cell exist, null otherwise
     */
    protected CellState getCellState(CellCoordinates coordinates){
        if(cells.containsKey(coordinates)){
            return cells.get(coordinates);
        }
        else
            return null;
    }

    /**
     * Advances {@link CellState} of cell on specified coordinates to next possible state
     * @param coordinates coordinates of cell to advance
     * @throws InvalidCoordsException if cooordinates not exist in Automaton
     */
    public void advanceCellState(CellCoordinates coordinates){
        CellState cellState = getCellState(coordinates);
        if(cellState != null){
            cells.put(coordinates,cellState.next());
        }
        else
            throw new InvalidCoordsException("Coords not exist ");
    }

    /**
     * Puts new cell to current Automaton with specified "dead" {@link CellState} on specified coordinates
     * @param coordinates coordinates added to Automaton with "dead" CellState
     */
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

    /**
     * Gets name of {@link CellNeighborhood} used in current Automaton.
     * Used only for {@link Cell2DimNeighbourhood}
     * @return String name of neighbourhood
     */
    public String getNeighborhoodName(){
        if(neighborsStrategy.getClass().equals(VonNeumanNeighborhood.class))
            return "VonNeumann";
        if(neighborsStrategy.getClass().equals(MoorNeighbourhood.class))
            return "Moore";
        else
            return "";
    }

    /**
     * Merge set of {@link CellCoordinates} with {@link CellState} that corresponds to them into {@link Cell}
     * @param coords set of {@link CellCoordinates} to map
     * @return set of {@link Cell}
     */
    private Set<Cell> mapCoordinates(Set<CellCoordinates> coords){
        TreeSet<Cell> cellsSet = new TreeSet<Cell>();
        for(CellCoordinates c: coords){
            cellsSet.add(new Cell(cells.get(c),c));
        }
        return cellsSet;
    }

    /**
     * Class of iterator that iterates through all cells in {@link Automaton}
     */
    public class CellIterator implements Iterator<Cell> {
        private CellCoordinates currentState;

        public CellIterator() {
            currentState = initialCoordinates();
        }

        /**
         * Set state of cell on which iterator is pointing
         * @param state {@link CellState} that will be set to cell
         */
        public void setState(CellState state) {
            cells.put(currentState,state);
        }
        /**
         *{@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return hasNextCoordinates(currentState);
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public Cell next() {
            if (!hasNext())
                throw new NoSuchElementException();
            currentState=nextCoordinates(currentState);
            return new Cell(cells.get(currentState),currentState);
        }
    }

    /**
     * Gets if board is wrapped or not
     * @return True if board is wrapped, False otherwise
     */
    public boolean getWrap(){
        return neighborsStrategy.getWrap();
    }

    /**
     * Check equality of Automaton
     * @param obj Automaton to compare to
     * @return True if equal, False otherwise
     */
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

    /**
     * Sets wrapping of the board
     * @param wrap decision if wrap(True) board or not(False)
     */
    public void setWrap(boolean wrap){
        neighborsStrategy.setWrap(wrap);
    }

    /**
     * Sets new {@link Cell2DimNeighbourhood} to Automaton. Used only with {@link Automaton2Dim}
     * @param newNeighbourhood new neighbourhood
     */
    public void set2DimNeighborsStrategy(Cell2DimNeighbourhood newNeighbourhood){
        if(neighborsStrategy instanceof Cell2DimNeighbourhood){
            Cell2DimNeighbourhood oldNeighbourhood = (Cell2DimNeighbourhood) neighborsStrategy;
            int radius = oldNeighbourhood.getRadius();
            neighborsStrategy = newNeighbourhood;
            newNeighbourhood.setRadius(radius);
        }
    }

    /**
     * Sets new radius of getting neighbours of cell
     * @param r new radius value
     */
    public void changeRadius(int r){
        if(neighborsStrategy instanceof Cell2DimNeighbourhood)
            ((Cell2DimNeighbourhood) neighborsStrategy).setRadius(r);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }

    /**
     * Sets specified {@link CellState} on specified {@link CellCoordinates}
     * @param coordinates {@link CellCoordinates} on which new state is assigned
     * @param state {@link CellState} which will be put
     */
    protected void setCell(CellCoordinates coordinates,CellState state){
        cells.put(coordinates,state);
    }

    /**
     * Total copy of Automaton, with states
     * @return duplicated Automaton
     */
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

