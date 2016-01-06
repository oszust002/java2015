package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.exceptions.RuleOutOfBoundariesException;
import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.coords.Coords1D;
import pl.edu.agh.kis.project.main.model.neighbourhood.Cell1DimNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.states.BinaryState;
import pl.edu.agh.kis.project.main.model.states.CellState;

import java.util.Set;
import java.util.TreeMap;

/**
 * Implementation of Elementary Cellular Automaton for all 256 rules
 * @author Kamil Osuch
 * @version 1.0
 */
public class Automaton1Dim extends Automaton {
    private int size;
    private int[] rule = new int[8];

    /**
     * Creates Automaton with specified width, rule, wrapping and initial states
     * @param stateFactory {@link CellStateFactory} of initial states
     * @param rule rule passed as array
     * @param size width of Automaton
     * @param wrap true if Automaton should wrap board, false otherwise
     */
    public Automaton1Dim(CellStateFactory stateFactory, int[] rule, int size, boolean wrap) {
        super(new Cell1DimNeighbourhood(size,wrap), stateFactory);
        this.size=size;
        System.arraycopy(rule, 0, this.rule, 0, rule.length);
        TreeMap<CellCoordinates,CellState> cells = new TreeMap<CellCoordinates, CellState>();
        for (int i=0;i<size;i++){
            Coords1D coords1D = new Coords1D(i);
            cells.put(coords1D,stateFactory.initialState(coords1D));
        }
        this.insertStructure(cells);
    }
    /**
     * Creates Automaton with specified width, rule, wrapping and initial states
     * @param factory {@link CellStateFactory} of initial states
     * @param rule rule passed as number from 0 to 255
     * @param size width of Automaton
     * @param wrap true if Automaton should wrap board, false otherwise
     * @throws RuleOutOfBoundariesException if rule value is not a number between 0 and 255
     */
    public Automaton1Dim(CellStateFactory factory, int rule, int size, boolean wrap){
        super(new Cell1DimNeighbourhood(size,wrap),factory);
        this.size = size;
        if(rule>255 || rule<0)
            throw new RuleOutOfBoundariesException("Automaton1Dim rules are in bounds [0;255]");
        setRule(rule);
        TreeMap<CellCoordinates,CellState> cells = new TreeMap<CellCoordinates, CellState>();
        for (int i=0;i<size;i++){
            Coords1D coords1D = new Coords1D(i);
            cells.put(coords1D,factory.initialState(coords1D));
        }
        this.insertStructure(cells);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new Automaton1Dim(factory,rule,getSize(), getWrap());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean hasNextCoordinates(CellCoordinates coords) {
        if (!(coords instanceof Coords1D))
            throw new IllegalArgumentException("Automaton1Dim supports only Coords1D");
        Coords1D coords1D = (Coords1D) coords;
        return coords1D.x < 0 || coords1D.x < size - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellCoordinates initialCoordinates() {
        return new Coords1D(-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coords) {
            Coords1D coords1D = (Coords1D) coords;
            return new Coords1D(coords1D.x + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellState nextCellState(Cell current, Set<Cell> neighborsStates) {
        BinaryState currentState = (BinaryState) current.state;
        int index=2*currentState.ordinal();
        for(Cell c: neighborsStates){
            index+=getAddFromCell(current,c);
        }
        return BinaryState.values()[rule[index]];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends CellState> getCellStateClass() {
        return BinaryState.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int newWidth, int newHeight) {
        if(newWidth>size){
            for (int x = size; x<newWidth;x++)
                putNewCell(new Coords1D(x));
        }
        else if(newWidth<size){
            for (int x = newWidth;x<size;x++)
                removeCell(new Coords1D(x));
        }
        size = newWidth;
        getNeighbourhood().setNewSize(new Coords1D(newWidth));
    }

    private int getAddFromCell(Cell current, Cell neighbour){
        BinaryState neighbourState = (BinaryState) neighbour.state;
        if(neighbour.compareTo(current)==-1)
            return 4*neighbourState.ordinal();
        else if(neighbour.compareTo(current)<-1)
            return neighbourState.ordinal();
        else if(neighbour.compareTo(current)==1)
            return neighbourState.ordinal();
        else
            return 4*neighbourState.ordinal();

    }

    /**
     * Gets width of current {@link Automaton1Dim}
     * @return width of Automaton
     */
    public int getSize() {
        return size;
    }

    /**
     * Convert {@link Automaton1Dim} to String
     * @return String representing Automaton
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell c: this){
            stringBuilder.append(c.state);
        }
        return stringBuilder.toString();
    }

    /**
     * Sets new rule to {@link Automaton1Dim}
     * @param rule value of rule
     */
    public void setRule(int rule){
        String ruleToBinary = String.format("%8s",Integer.toBinaryString(rule)).replace(" ", "0");
        for(int i=ruleToBinary.length()-1;i>=0;i--){
            this.rule[7-i]=Character.getNumericValue(ruleToBinary.charAt(i));
        }
    }
}
