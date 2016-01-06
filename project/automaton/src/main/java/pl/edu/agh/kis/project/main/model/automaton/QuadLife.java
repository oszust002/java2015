package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.Cell2DimNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.LifeState;
import pl.edu.agh.kis.project.main.model.states.QuadState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of QuadLife Automaton
 * @author Kamil Osuch
 * @version 1.0
 */
public class QuadLife extends GameOfLife{
    /**
     * Creates Automaton based on {@link Cell2DimNeighbourhood}, {@link CellStateFactory}, width and height
     * @param neighborhood {@link CellNeighborhood} strategy of getting neighbours of cell
     * @param factory {@link CellStateFactory} of initial states
     * @param width width of Automaton
     * @param height height of Automaton
     */
    public QuadLife(Cell2DimNeighbourhood neighborhood, CellStateFactory factory, int width, int height) {
        super(neighborhood, factory, "23/3", width, height);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new QuadLife((Cell2DimNeighbourhood) neighbourhood,factory,getWidth(),getHeight());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    protected CellState nextCellState(Cell current, Set<Cell> neighborsStates) {
        Map<LifeState,Integer> aliveNeighbours= getAliveNeighbours(neighborsStates);
        int aliveNeighboursSum = 0;
        for(Integer i: aliveNeighbours.values()){
            aliveNeighboursSum+=i;
        }
        QuadState currentState = (QuadState) current.state;
        if(surviveAmount.contains(aliveNeighboursSum) && currentState.isAlive())
            return currentState;
        if(birthAmount.contains(aliveNeighboursSum) && !currentState.isAlive()){
            QuadState dominantState = getDominantState(aliveNeighbours);
            if(dominantState == null){
                return getNotExistingState(aliveNeighbours);
            }
        }
        return QuadState.DEAD;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Class<? extends CellState> getCellStateClass() {
        return QuadState.class;
    }

    private QuadState getDominantState(Map<LifeState,Integer> mapOfNeighbourStates){
        Integer maxValue = 1;
        QuadState dominant = null;
        for(Map.Entry<LifeState,Integer> entry :mapOfNeighbourStates.entrySet()){
            if(maxValue<entry.getValue()){
                dominant = (QuadState) entry.getKey();
            }
        }
        return dominant;
    }

    private QuadState getNotExistingState(Map<LifeState,Integer> mapOfNeighbourStates){
        ArrayList<QuadState> states = new ArrayList<QuadState>(Arrays.asList(QuadState.values()));
        states.remove(QuadState.DEAD);
        for(LifeState state : mapOfNeighbourStates.keySet()){
            QuadState quadState = (QuadState) state;
            states.remove(quadState);
        }
        return states.iterator().next();
    }
}
