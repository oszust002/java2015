package pl.edu.agh.kis.project.main.model.automaton;

import org.junit.Test;
import pl.edu.agh.kis.project.main.model.factory.GeneralStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.states.BinaryState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Kamil on 28.12.2015.
 */
public class GameOfLifeTest {
    private BinaryState[][] gliderStart={
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD}
    };

    private BinaryState[][] gliderExpectedEnd = {
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE}
    };

    private BinaryState[][] seeds = {
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD}
    };

    private BinaryState[][] expectedStates = {
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD}
    };

    private BinaryState[][] wrappedEnd = {
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD}
    };

    private BinaryState[][] frog = {
            {BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD},
            {BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE},
            {BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD}
    };

    private BinaryState[][] blinker = {
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
            {BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE},
            {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD}
    };

    private BinaryState[][] stillLife = {
            {BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD},
            {BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE},
            {BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE},
            {BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD}
    };



    public Automaton createAutomaton(BinaryState[][] initialState, boolean wrap){
        return new GameOfLife(new MoorNeighbourhood(1,initialState[0].length,initialState.length,wrap),
                new GeneralStateFactory(initialState),initialState[0].length,initialState.length);
    }

    @Test
    public void createGameOfLifeWithGliderStartIn10x10_rule23_3_28Steps_GliderEndExpected(){
        Automaton golStart = createAutomaton(gliderStart,false);
        Automaton golExpected = createAutomaton(gliderExpectedEnd,false);
        for(int i = 0; i<28;i++){
            golStart = golStart.nextState();
        }
        assertEquals(golExpected,golStart);
    }

    @Test
    public void createGameOfLifeWithWrongRulePattern_IllegalArgumentExceptionExpected(){
        try{
            new GameOfLife(new MoorNeighbourhood(1,1,1,false),new GeneralStateFactory(gliderStart),"231",1,1);
            fail("Expected IllegalArgumentException to be thrown");
        }
        catch (IllegalArgumentException e){
            assertEquals("Rule pattern is: SurviveDigits/BornDigits",e.getMessage());
        }
    }

    @Test
    public void createCustomRulesGameOfLifeWithSeeds_3Steps_EndStatesExpected(){
        Automaton gol_rule_2_0 = new GameOfLife(new MoorNeighbourhood(1,seeds[0].length,seeds.length,false),
                new GeneralStateFactory(seeds),"2/2",seeds[0].length,seeds.length);
        Automaton expected = createAutomaton(expectedStates,false);
        for(int i = 0; i<3;i++){
            gol_rule_2_0 = gol_rule_2_0.nextState();
        }
        assertEquals(expected,gol_rule_2_0);
    }

    @Test
    public void createCustomRulesWrappedGOLWithSeeds_5Steps_WrappedEndExpected(){
        Automaton gol_rule_none_1 = new GameOfLife(new MoorNeighbourhood(1,seeds[0].length,seeds.length,true),
                new GeneralStateFactory(seeds),"/1",seeds[0].length,seeds.length);
        Automaton expected = createAutomaton(wrappedEnd,true);
        for(int i = 0; i<5;i++){
            gol_rule_none_1 = gol_rule_none_1.nextState();
        }
        assertEquals(expected,gol_rule_none_1);
    }

    @Test
    public void createGameOfLifeWithOscillatorsAndStillLife_EndEqualToStartExpected(){
        Automaton frogOscillator = createAutomaton(frog,false);
        Automaton blinkerOscillator = createAutomaton(blinker,false);
        Automaton stillLifeGOL = createAutomaton(stillLife,false);

        for(int i=0;i<6;i++){
            frogOscillator=frogOscillator.nextState();
            blinkerOscillator=blinkerOscillator.nextState();
            stillLifeGOL= stillLifeGOL.nextState();
        }
        assertEquals(createAutomaton(frog,false),frogOscillator);
        assertEquals(createAutomaton(blinker,false),blinkerOscillator);
        assertEquals(createAutomaton(stillLife,false),stillLifeGOL);
    }
}
