package pl.edu.agh.kis.project.main.model.automaton;

import org.junit.Test;
import pl.edu.agh.kis.project.main.model.factory.GeneralStateFactory;
import pl.edu.agh.kis.project.main.model.states.BinaryState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Kamil on 31.12.2015.
 */
public class Automaton1DimTest {

    private BinaryState[] startState =
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD};

    private BinaryState[] rule30Expected =
            {BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE};

    private BinaryState[] rule110expected =
            {BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD};

    private BinaryState[] wrappedStart =
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD};

    private BinaryState[] wrappedRule30Expected =
            {BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD};

    @Test
    public void OneDimAutomaton_rule30_10Steps_isEqualToExpected(){
        Automaton start = new Automaton1Dim(new GeneralStateFactory(startState),30,startState.length,false);
        Automaton expected = new Automaton1Dim(new GeneralStateFactory(rule30Expected),30,startState.length,false);
        for (int i=0;i<10;i++){
            start=start.nextState();
        }
        assertEquals(expected,start);
    }

    @Test
    public void OneDimAutomaton_rule110_10Steps_isEqualToExpected(){
        Automaton start = new Automaton1Dim(new GeneralStateFactory(startState),110,rule30Expected.length,false);
        Automaton expected = new Automaton1Dim(new GeneralStateFactory(rule110expected),110,
                rule110expected.length,false);
        for (int i=0;i<10;i++){
            start = start.nextState();
        }
        assertEquals(expected,start);
    }

    @Test
    public void OneDimAutomaton_rule30_withWrap_10Steps_isEqualToExpected(){
        Automaton start = new Automaton1Dim(new GeneralStateFactory(wrappedStart),30,wrappedStart.length,true);
        Automaton expected = new Automaton1Dim(new GeneralStateFactory(wrappedRule30Expected),30,
                wrappedRule30Expected.length,true);
        for (int i=0;i<10;i++){
            start = start.nextState();
        }
        assertEquals(expected,start);
    }

    @Test
    public void OneDimAutomaton_toStringMethodTest_EqualToExpected(){
        Automaton automaton1Dim=new Automaton1Dim(new GeneralStateFactory(startState),30,startState.length,true);
        assertEquals("0000000000X0000000000",automaton1Dim.toString());
    }

    @Test
    public void GivenRuleIsOutOfBounds_throwIllegalArgumentException_MessageIsEqualToExpected(){
        try {
            new Automaton1Dim(new GeneralStateFactory(startState),300,startState.length,false);
            fail("Expected IllegalArgumentException to be thrown");
        }catch (IllegalArgumentException e){
            assertEquals("Automaton1Dim rules are in bounds [0;255]",e.getMessage());
        }
    }
}
