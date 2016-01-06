package pl.edu.agh.kis.project.main.model.automaton;

import org.junit.Test;
import pl.edu.agh.kis.project.main.model.factory.UniformStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.states.BinaryState;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kamil on 05.01.2016.
 */
public class AutomatonTest {
    private Automaton automaton;

    @Test
    public void getNeighbourhood_EqualToExpected(){
        MoorNeighbourhood neighbourhood = new MoorNeighbourhood(1,6,6,false);
        automaton = new GameOfLife(neighbourhood,
                new UniformStateFactory(BinaryState.DEAD),"23/3",6,6);
        assertEquals(automaton.getNeighbourhood(),neighbourhood);
    }

    @Test
    public void getNeighbourhoodName_EqualToExpected(){
        MoorNeighbourhood neighbourhood = new MoorNeighbourhood(1,6,6,false);
        automaton = new GameOfLife(neighbourhood,
                new UniformStateFactory(BinaryState.DEAD),"23/3",6,6);
        assertEquals(automaton.getNeighborhoodName(),"Moore");
    }

    @Test
    public void getCellStateClassMethodTest_EqualToExpected(){
        automaton = new GameOfLife(new MoorNeighbourhood(1,6,6,false),
                new UniformStateFactory(BinaryState.DEAD),"23/3",6,6);
        assertEquals(automaton.getCellStateClass(), BinaryState.class);
    }
}
