package pl.edu.agh.kis.project.main.automaton;

import org.junit.Test;
import pl.edu.agh.kis.project.main.GeneralStateFactory;
import pl.edu.agh.kis.project.main.states.CellState;
import pl.edu.agh.kis.project.main.states.WireElectronState;

import static org.junit.Assert.assertEquals;

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
        Automaton wireWorldStart = new WireWorld(new GeneralStateFactory(wireWorld),wireWorld[0].length,
                wireWorld.length,false);

        Automaton wireWorldExpected = new WireWorld(new GeneralStateFactory(expected),expected[0].length,
                expected.length,false);
        for (int i=0;i<3;i++) {
            wireWorldStart = wireWorldStart.nextState();
        }

        assertEquals(wireWorldStart,wireWorldExpected);
    }

    @Test
    public void toStringMethodTest_IsEqualToExpected(){
        Automaton wireWorldStart = new WireWorld(new GeneralStateFactory(wireWorld),wireWorld[0].length,
                wireWorld.length,false);
        String expected="00000000\n" +
                        "THWWWWWW\n" +
                        "00000000";
        assertEquals(expected,wireWorldStart.toString());
    }
}
