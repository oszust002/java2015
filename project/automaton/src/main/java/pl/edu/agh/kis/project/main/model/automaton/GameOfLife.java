package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.Cell2DimNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.states.BinaryState;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.LifeState;

import java.util.*;

/**
 * Implementation of Conway's Game of Life
 * @author Kamil Osuch
 * @version 1.0
 */
public class GameOfLife extends Automaton2Dim{
    protected final TreeSet<Integer> surviveAmount;
    protected final TreeSet<Integer> birthAmount;

    /**
     * Creates Automaton based on {@link Cell2DimNeighbourhood}, {@link CellStateFactory}, rule, width and height
     * @param neighborhood {@link CellNeighborhood} strategy of getting neighbours of cell
     * @param factory {@link CellStateFactory} of initial states
     * @param rule rule of Conway's Game of Life passed as a String
     * @param width width of Automaton
     * @param height height of Automaton
     * @throws IllegalArgumentException if rule doesn't fit pattern
     */
    public GameOfLife(Cell2DimNeighbourhood neighborhood, CellStateFactory factory, String rule, int width, int height){
        super(neighborhood,factory,width,height);
        if(!rule.matches("([0-9]*)/([0-9]*)"))
            throw new IllegalArgumentException("Rule pattern is: SurviveDigits/BornDigits");
        surviveAmount = new TreeSet<Integer>();
        birthAmount = new TreeSet<Integer>();
        setRule(rule);
    }

    /**
     * Creates Automaton based on {@link Cell2DimNeighbourhood}, {@link CellStateFactory}, rule, width and height
     * @param neighborhood {@link CellNeighborhood} strategy of getting neighbours of cell
     * @param factory {@link CellStateFactory} of initial states
     * @param surviveAmount set of numbers of neighbours in which cell will survive
     * @param birthAmount set of numbers of neighbours in which cell will born
     * @param width width of Automaton
     * @param height height of Automaton
     */
    public GameOfLife(Cell2DimNeighbourhood neighborhood, CellStateFactory factory, TreeSet<Integer> surviveAmount,
                      TreeSet<Integer> birthAmount, int width, int height){
        super(neighborhood,factory,width,height);
        this.birthAmount = birthAmount;
        this.surviveAmount = surviveAmount;
    }

    /**
     * Creates Automaton based on {@link Cell2DimNeighbourhood}, {@link CellStateFactory}, width and height with
     * default rule "23/3"
     * @param neighborhood {@link CellNeighborhood} strategy of getting neighbours of cell
     * @param factory {@link CellStateFactory} of initial states
     * @param width width of Automaton
     * @param height height of Automaton
     */
    public GameOfLife(Cell2DimNeighbourhood neighborhood, CellStateFactory factory, int width, int height){
        super(neighborhood,factory,width,height);
        surviveAmount = new TreeSet<Integer>(Arrays.asList(2,3));
        birthAmount = new TreeSet<Integer>(Arrays.asList(3));
    }

    /**
     *{@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new GameOfLife((Cell2DimNeighbourhood) neighbourhood,factory,surviveAmount,birthAmount,
                getWidth(),getHeight());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    protected CellState nextCellState(Cell current, Set<Cell> neighborsStates) {
        Map<LifeState, Integer> aliveNeighbours = getAliveNeighbours(neighborsStates);
        int aliveNeighboursSum = 0;
        for(Integer value: aliveNeighbours.values()){
            aliveNeighboursSum+=value;
        }
        if(surviveAmount.contains(aliveNeighboursSum) && current.state == BinaryState.ALIVE)
            return BinaryState.ALIVE;
        if(birthAmount.contains(aliveNeighboursSum) &&  current.state == BinaryState.DEAD)
            return BinaryState.ALIVE;
        return BinaryState.DEAD;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Class<? extends CellState> getCellStateClass() {
        return BinaryState.class;
    }

    protected Map<LifeState,Integer> getAliveNeighbours(Set<Cell> neighbours){
        TreeMap<LifeState, Integer> states = new TreeMap<LifeState, Integer>();
        for (Cell neighbour: neighbours){
            LifeState state = (LifeState) neighbour.state;
            if(state.isAlive()){
                if(!states.containsKey(state))
                    states.put(state,1);
                else
                    states.put(state,states.get(state)+1);
            }
        }
        return states;
    }

    /**
     * Sets new rule to current GameOfLife Automaton
     * @param rule rule passed as a String
     * @throws IllegalArgumentException if rule doesn't fit pattern
     */
    public void setRule(String rule){
        if(!rule.matches("([0-9]*)/([0-9]*)"))
            throw new IllegalArgumentException("Rule pattern is: SurviveDigits/BornDigits");
        surviveAmount.clear();
        birthAmount.clear();
        for(char toSurvive: rule.split("/")[0].toCharArray())
            surviveAmount.add(Character.getNumericValue(toSurvive));
        for (char toBorn: rule.split("/")[1].toCharArray())
            birthAmount.add(Character.getNumericValue(toBorn));
    }
}
