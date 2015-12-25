package pl.edu.agh.kis.project.main;

import org.junit.Test;
import pl.edu.agh.kis.project.main.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.states.CellState;
import pl.edu.agh.kis.project.main.states.WireElectronState;

import java.util.TreeMap;

/**
 * Created by Kamil on 09.12.2015.
 */


public class WireWorldTesting {

    public CellState[][] wireWorld = {
            {WireElectronState.VOID,WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID},
            {WireElectronState.ELECTRON_TAIL,WireElectronState.ELECTRON_HEAD, WireElectronState.WIRE, WireElectronState.WIRE, WireElectronState.WIRE},
            {WireElectronState.VOID,WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID, WireElectronState.VOID}
    };


    public 
}
