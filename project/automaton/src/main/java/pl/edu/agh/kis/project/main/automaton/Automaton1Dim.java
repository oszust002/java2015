package pl.edu.agh.kis.project.main.automaton;

import pl.edu.agh.kis.project.main.Cell;
import pl.edu.agh.kis.project.main.CellStateFactory;
import pl.edu.agh.kis.project.main.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.coords.Coords1D;
import pl.edu.agh.kis.project.main.neighbourhood.Cell1DimNeighbourhood;
import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.states.BinaryState;
import pl.edu.agh.kis.project.main.states.CellState;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Kamil on 24.11.2015.
 */
public class Automaton1Dim extends Automaton {
    private int size;
    private int[] rule = new int[8];

    public Automaton1Dim(CellStateFactory stateFactory, int[] rule, int size, boolean wrap) {
        super(new Cell1DimNeighbourhood(size,wrap), stateFactory);
        this.size=size;
        this.rule = rule;
        TreeMap<CellCoordinates,CellState> cells = new TreeMap<CellCoordinates, CellState>();
        for (int i=0;i<size;i++){
            Coords1D coords1D = new Coords1D(i);
            cells.put(coords1D,stateFactory.initialState(coords1D));
        }
        this.insertStructure(cells);
    }

    public Automaton1Dim(CellStateFactory factory, int rule, int size, boolean wrap){
        super(new Cell1DimNeighbourhood(size,wrap),factory);
        this.size = size;
        if(rule>255 || rule<0)
            throw new IllegalArgumentException("Automaton1Dim rules are in bounds [0;255]");
        String ruleToBinary = String.format("%8s",Integer.toBinaryString(rule)).replace(" ", "0");
        for(int i=ruleToBinary.length()-1;i>=0;i--){
            this.rule[7-i]=Character.getNumericValue(ruleToBinary.charAt(i));
        }
        TreeMap<CellCoordinates,CellState> cells = new TreeMap<CellCoordinates, CellState>();
        for (int i=0;i<size;i++){
            Coords1D coords1D = new Coords1D(i);
            cells.put(coords1D,factory.initialState(coords1D));
        }
        this.insertStructure(cells);
    }


    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new Automaton1Dim(factory,rule,getSize(), getWrap());
    }

    @Override
    protected boolean hasNextCoordinates(CellCoordinates coords) {
        if (!(coords instanceof Coords1D))
            throw new IllegalArgumentException("Automaton1Dim supports only Coords1D");
        Coords1D coords1D = (Coords1D) coords;
        return coords1D.x < 0 || coords1D.x < size - 1;
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new Coords1D(-1);
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coords) {
            Coords1D coords1D = (Coords1D) coords;
            return new Coords1D(coords1D.x + 1);
    }

    @Override
    protected CellState nextCellState(Cell current, Set<Cell> neighborsStates) {
        BinaryState currentState = (BinaryState) current.state;
        int index=2*currentState.ordinal();
        for(Cell c: neighborsStates){
            index+=getAddFromCell(current,c);
        }
        return BinaryState.values()[rule[index]];
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

    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell c: this){
            stringBuilder.append(c.state);
        }
        return stringBuilder.toString();
    }
}
