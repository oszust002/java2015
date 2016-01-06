package pl.edu.agh.kis.project.main.model.automaton;

import org.junit.Test;
import pl.edu.agh.kis.project.main.model.factory.GeneralStateFactory;
import pl.edu.agh.kis.project.main.model.states.AntState;
import pl.edu.agh.kis.project.main.model.states.BinaryState;
import pl.edu.agh.kis.project.main.model.states.LangtonCell;

import static org.junit.Assert.assertEquals;
/**
 * Created by Kamil on 05.01.2016.
 */
public class LangtonAntTest {
    public LangtonCell[][] start_1Ant = {
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD, 1, AntState.NORTH), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)}
    };

    public LangtonCell[][] expected = {
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.ALIVE ), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD, 1, AntState.NORTH), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)}
    };

    public LangtonCell[][] start_2AntsBeforeKill = {
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.ALIVE,1,AntState.NORTH)},
            {new LangtonCell(BinaryState.DEAD,1,AntState.WEST), new LangtonCell(BinaryState.ALIVE)}
    };

    public LangtonCell[][] expected_2AntsAfterKill = {
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.ALIVE)}
    };

    public LangtonCell[][] antOnEdgeStart = {
            {new LangtonCell(BinaryState.DEAD,1,AntState.WEST), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)}
    };

    public LangtonCell[][] antOnEdgeExpected_WithoutWrap = {
            {new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD), new LangtonCell(BinaryState.DEAD)}
    };

    public LangtonCell[][] antOnEdgeExpected_WithWrap = {
            {new LangtonCell(BinaryState.ALIVE), new LangtonCell(BinaryState.DEAD)},
            {new LangtonCell(BinaryState.DEAD,1,AntState.NORTH), new LangtonCell(BinaryState.DEAD)}
    };


    @Test
    public void langtonAntTest_setStart1Ant_38Steps_EqualToExpected(){
        Automaton langtonStart = new LangtonAnt(new GeneralStateFactory(start_1Ant),start_1Ant[0].length,
                start_1Ant.length,false);
        Automaton expectedLangtonAnt = new LangtonAnt(new GeneralStateFactory(expected),expected[0].length,
                expected.length,false);
        for (int i = 0;i < 38;i++){
            langtonStart = langtonStart.nextState();
        }
        assertEquals(langtonStart,expectedLangtonAnt);
    }

    @Test
    public void antsGoingToTheSameCoordsKillsEachOtherTest(){
        Automaton langtonStart = new LangtonAnt(new GeneralStateFactory(start_2AntsBeforeKill),
                start_2AntsBeforeKill[0].length,start_2AntsBeforeKill.length,false);
        Automaton expected = new LangtonAnt(new GeneralStateFactory(expected_2AntsAfterKill),
                expected_2AntsAfterKill[0].length,expected_2AntsAfterKill.length,false);
        langtonStart = langtonStart.nextState();
        assertEquals(expected,langtonStart);
    }

    @Test
    public void antOnEdge_WithoutWrap_Step_EqualToExpected(){
        Automaton langtonStart = new LangtonAnt(new GeneralStateFactory(antOnEdgeStart),
                antOnEdgeStart[0].length,antOnEdgeStart.length,false);
        Automaton langtonExpected = new LangtonAnt(new GeneralStateFactory(antOnEdgeExpected_WithoutWrap),
                antOnEdgeExpected_WithoutWrap[0].length,antOnEdgeExpected_WithoutWrap.length,false);
        langtonStart = langtonStart.nextState();
        assertEquals(langtonExpected, langtonStart);
    }

    @Test
    public void antOnEdge_WithWrap_Step_EqualToExpected(){
        Automaton langtonStart = new LangtonAnt(new GeneralStateFactory(antOnEdgeStart),
                antOnEdgeStart[0].length,antOnEdgeStart.length,true);
        Automaton langtonExpected = new LangtonAnt(new GeneralStateFactory(antOnEdgeExpected_WithWrap),
                antOnEdgeExpected_WithWrap[0].length,antOnEdgeExpected_WithWrap.length,true);
        langtonStart = langtonStart.nextState();
        assertEquals(langtonExpected, langtonStart);
    }
}
