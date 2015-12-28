package pl.edu.agh.kis.project.main.automaton;

import pl.edu.agh.kis.project.main.Cell;
import pl.edu.agh.kis.project.main.CellStateFactory;
import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.states.BinaryState;
import pl.edu.agh.kis.project.main.states.CellState;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Kamil on 28.12.2015.
 */
public class GameOfLife extends Automaton2Dim{
    private final TreeSet<Integer> surviveAmount;
    private final TreeSet<Integer> birthAmount;

    public GameOfLife(CellNeighborhood neighborhood, CellStateFactory factory, String rule, int width, int height){
        super(neighborhood,factory,width,height);
        if(!rule.matches("([0-9]*)/([0-9]*)"))
            throw new IllegalArgumentException("Rule pattern is: SurviveDigits/BornDigits");
        surviveAmount = new TreeSet<Integer>();
        birthAmount = new TreeSet<Integer>();
        for(char toSurvive: rule.split("/")[0].toCharArray())
            surviveAmount.add(Character.getNumericValue(toSurvive));
        for (char toBorn: rule.split("/")[0].toCharArray())
            birthAmount.add(Character.getNumericValue(toBorn));
    }

    public GameOfLife(CellNeighborhood neighborhood, CellStateFactory factory, TreeSet<Integer> surviveAmount,
                      TreeSet<Integer> birthAmount, int width, int height){
        super(neighborhood,factory,width,height);
        this.birthAmount = birthAmount;
        this.surviveAmount = surviveAmount;
    }

    public GameOfLife(CellNeighborhood neighborhood, CellStateFactory factory, int width, int height){
        super(neighborhood,factory,width,height);
        surviveAmount = new TreeSet<Integer>(Arrays.asList(2,3));
        birthAmount = new TreeSet<Integer>(Arrays.asList(3));
    }

    @Override
    protected Automaton
    newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new GameOfLife(neighbourhood,factory,surviveAmount,birthAmount,getWidth(),getHeight());
    }

    @Override
    protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) {
        int aliveNeighbours = 0;
        for (Cell neighbour: neighborsStates){
            BinaryState state = (BinaryState) neighbour.state;
            if (state == BinaryState.ALIVE)
                aliveNeighbours++;
        }
        if(surviveAmount.contains(aliveNeighbours) && currentState == BinaryState.ALIVE)
            return BinaryState.ALIVE;
        if(birthAmount.contains(aliveNeighbours) && currentState == BinaryState.DEAD)
            return BinaryState.ALIVE;
        return BinaryState.DEAD;
    }
}
