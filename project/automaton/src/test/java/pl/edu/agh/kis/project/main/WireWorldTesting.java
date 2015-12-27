package pl.edu.agh.kis.project.main;

import org.junit.Test;
import static org.junit.Assert.*;

import pl.edu.agh.kis.project.main.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.states.BinaryState;
import pl.edu.agh.kis.project.main.states.CellState;
import pl.edu.agh.kis.project.main.states.WireElectronState;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Kamil on 09.12.2015.
 */


public class WireWorldTesting {

    public CellState[][] wireWorld = {
            {WireElectronState.VOID,WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID},
            {WireElectronState.ELECTRON_TAIL,WireElectronState.ELECTRON_HEAD, WireElectronState.WIRE, WireElectronState.WIRE, WireElectronState.WIRE, WireElectronState.WIRE, WireElectronState.WIRE, WireElectronState.WIRE},
            {WireElectronState.VOID,WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID}
    };

    public CellState[][] expected = {
            {WireElectronState.VOID,WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID},
            {WireElectronState.WIRE,WireElectronState.WIRE, WireElectronState.WIRE, WireElectronState.ELECTRON_TAIL, WireElectronState.ELECTRON_HEAD, WireElectronState.WIRE, WireElectronState.WIRE, WireElectronState.WIRE},
            {WireElectronState.VOID,WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID}
    };

    @Test
    public void WireWorld_CreateWithInitialState_Do3Steps_CheckWithExpected(){
        Automaton wireWorldStart = new WireWorld(new MoorNeighbourhood(wireWorld[0].length,wireWorld.length,false),
                new GeneralStateFactory(wireWorld),wireWorld[0].length,wireWorld.length);

        Automaton wireWorldExpected = new WireWorld(new MoorNeighbourhood(expected[0].length,expected.length,false),
                new GeneralStateFactory(expected),expected[0].length,expected.length);
        for (int i=0;i<3;i++) {
            wireWorldStart = wireWorldStart.nextState();
        }

        assertEquals(wireWorldStart,wireWorldExpected);
    }
}
