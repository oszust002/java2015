package pl.edu.agh.kis.project.main.automaton;

import pl.edu.agh.kis.project.main.Cell;
import pl.edu.agh.kis.project.main.CellStateFactory;
import pl.edu.agh.kis.project.main.neighbourhood.Cell2DimNeighbourhood;
import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.states.BinaryState;
import pl.edu.agh.kis.project.main.states.CellState;
import pl.edu.agh.kis.project.main.states.LifeState;

import java.util.*;

/**
 * Created by Kamil on 28.12.2015.
 */
public class GameOfLife extends Automaton2Dim{
    protected final TreeSet<Integer> surviveAmount;
    protected final TreeSet<Integer> birthAmount;

    public GameOfLife(Cell2DimNeighbourhood neighborhood, CellStateFactory factory, String rule, int width, int height){
        super(neighborhood,factory,width,height);
        if(!rule.matches("([0-9]*)/([0-9]*)"))
            throw new IllegalArgumentException("Rule pattern is: SurviveDigits/BornDigits");
        surviveAmount = new TreeSet<Integer>();
        birthAmount = new TreeSet<Integer>();
        for(char toSurvive: rule.split("/")[0].toCharArray())
            surviveAmount.add(Character.getNumericValue(toSurvive));
        for (char toBorn: rule.split("/")[1].toCharArray())
            birthAmount.add(Character.getNumericValue(toBorn));
    }

    public GameOfLife(Cell2DimNeighbourhood neighborhood, CellStateFactory factory, TreeSet<Integer> surviveAmount,
                      TreeSet<Integer> birthAmount, int width, int height){
        super(neighborhood,factory,width,height);
        this.birthAmount = birthAmount;
        this.surviveAmount = surviveAmount;
    }

    public GameOfLife(Cell2DimNeighbourhood neighborhood, CellStateFactory factory, int width, int height){
        super(neighborhood,factory,width,height);
        surviveAmount = new TreeSet<Integer>(Arrays.asList(2,3));
        birthAmount = new TreeSet<Integer>(Arrays.asList(3));
    }

    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new GameOfLife((Cell2DimNeighbourhood) neighbourhood,factory,surviveAmount,birthAmount,
                getWidth(),getHeight());
    }

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
}
