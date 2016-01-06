package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.VonNeumanNeighborhood;
import pl.edu.agh.kis.project.main.model.states.AntState;
import pl.edu.agh.kis.project.main.model.states.BinaryState;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.LangtonCell;

import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of Langton Ant Automaton
 * @author Kamil Osuch
 * @version 1.0
 */
public class LangtonAnt extends Automaton2Dim{
    private int antCounter = 0;

    /**
     * Creates Automaton based on {@link CellStateFactory}, width and height with wrapping
     * @param stateFactory {@link CellStateFactory} of initial states
     * @param width width of Automaton
     * @param height height of Automaton
     * @param wrap True if should wrap board, False otherwise
     */
    public LangtonAnt(CellStateFactory stateFactory, int width, int height, boolean wrap) {
        super(new VonNeumanNeighborhood(1,width,height, wrap), stateFactory, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new LangtonAnt(factory,getWidth(),getHeight(),getWrap());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellState nextCellState(Cell current, Set<Cell> neighborsStates) {
        Coords2D currentCoords = (Coords2D) current.coords;
        LangtonCell currentState = (LangtonCell) current.state;
        BinaryState newCellState = currentState.cellState;
        Set<Cell> antNeighbours = getAntsGoingToCoord(currentCoords,neighborsStates);
        if(currentState.hasAnt()){
            newCellState = (BinaryState) newCellState.next();
        }
        if(antNeighbours.size() == 0 || antNeighbours.size()>1)
            return new LangtonCell(newCellState);
        else {
            Cell ant = antNeighbours.iterator().next();
            LangtonCell state = (LangtonCell) ant.state;
            AntState newDirection = nextAntDirection(ant);
            return new LangtonCell(newCellState,state.antId,newDirection);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends CellState> getCellStateClass() {
        return LangtonCell.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceCellState(CellCoordinates coordinates) {
        LangtonCell langtonCell = (LangtonCell) getCellState(coordinates);
        if(!langtonCell.hasAnt() && langtonCell.cellState == BinaryState.ALIVE)
            putNewAnt(coordinates, AntState.NORTH);
        else if(langtonCell.hasAnt() && langtonCell.cellState == BinaryState.ALIVE &&
                langtonCell.antState == AntState.WEST) {
            antCounter--;
            setCell(coordinates, new LangtonCell(BinaryState.DEAD));
        }else
            super.advanceCellState(coordinates);
    }

    private void putNewAnt(CellCoordinates coordinates, AntState antState) {
        setCell(coordinates,new LangtonCell(BinaryState.DEAD,++antCounter,antState));
    }

    private Set<Cell> getAntsGoingToCoord(Coords2D coords, Set<Cell> neighbours){
        Set<Cell> ants = new TreeSet<Cell>();
        for(Cell ant: neighbours){
            LangtonCell state = (LangtonCell) ant.state;
            if(state.hasAnt())
                if(nextAntPosition(ant).equals(coords))
                    ants.add(ant);
        }
        return ants;
    }

    private Coords2D nextAntPosition(Cell ant){
        Coords2D antCoords = (Coords2D) ant.coords;
        switch (nextAntDirection(ant)){
            case NORTH:{
                int newY = antCoords.y -1;
                if(getWrap())
                    newY = Math.floorMod(newY,getWidth());
                return new Coords2D(antCoords.x,newY);
            }
            case EAST:{
                int newX = antCoords.x+1;
                if(getWrap())
                    newX = Math.floorMod(newX,getWidth());
                return new Coords2D(newX,antCoords.y);
            }
            case SOUTH:{
                int newY = antCoords.y+1;
                if(getWrap())
                    newY = Math.floorMod(newY,getHeight());
                return new Coords2D(antCoords.x,newY);
            }
            case WEST:{
                int newX = antCoords.x-1;
                if(getWrap())
                    newX = Math.floorMod(newX,getWidth());
                return new Coords2D(newX,antCoords.y);
            }
            default:
                throw new IllegalStateException();
        }
    }

    private AntState nextAntDirection(Cell ant){
        LangtonCell cell = (LangtonCell) ant.state;
        switch (cell.cellState){
            case DEAD:
                return AntState.rotateRight(cell.antState);
            case ALIVE:
                return AntState.rotateLeft(cell.antState);
            default:
                throw new IllegalStateException();
        }
    }
}
