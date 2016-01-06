package pl.edu.agh.kis.project.main.model.automaton;

import org.junit.Test;
import pl.edu.agh.kis.project.main.model.factory.GeneralStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.states.QuadState;
import static org.junit.Assert.assertEquals;

/**
 * Created by Kamil on 31.12.2015.
 */
public class QuadLifeTest {
    private QuadState[][] start = {
            {QuadState.YELLOW, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.RED, QuadState.GREEN, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.BLUE, QuadState.BLUE, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD}
    };

    private QuadState[][] expected = {
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.RED, QuadState.GREEN, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.BLUE, QuadState.YELLOW, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD},
            {QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD, QuadState.DEAD}
    };

    @Test
    public void QuadLife_createFromStart_10Steps_EqualToExpected(){
        Automaton quadLifeStart = new QuadLife(new MoorNeighbourhood(1,10,10,false),new GeneralStateFactory(start),
                10,10);
        Automaton quadLifeExpected = new QuadLife(new MoorNeighbourhood(1,10,10,false),
                new GeneralStateFactory(expected),10,10);
        for(int i=0;i<10;i++) {
            quadLifeStart = quadLifeStart.nextState();
        }
        assertEquals(quadLifeStart,quadLifeExpected);
    }
}
