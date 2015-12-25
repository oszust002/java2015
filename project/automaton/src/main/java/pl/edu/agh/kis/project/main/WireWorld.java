package pl.edu.agh.kis.project.main;


import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.states.CellState;
import pl.edu.agh.kis.project.main.states.WireElectronState;

import java.util.Set;

/**
 * Created by Kamil on 07.12.2015.
 */
public class WireWorld extends Automaton2Dim {
    public WireWorld(CellNeighborhood neighboursStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighboursStrategy, stateFactory, width, height);
    }

    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new WireWorld(neighbourhood,factory,getWidth(),getHeight());
    }

    @Override
    protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) {
        if(currentState == WireElectronState.ELECTRON_HEAD)
            return WireElectronState.ELECTRON_TAIL;
        else if(currentState == WireElectronState.ELECTRON_TAIL)
            return WireElectronState.WIRE;
        else if(currentState == WireElectronState.WIRE){
            int occurences = 0;
            for(Cell c: neighborsStates){
                if(c.state == WireElectronState.ELECTRON_HEAD)
                    occurences++;
            }
            if(occurences==1 || occurences==2)
                return WireElectronState.ELECTRON_HEAD;
            else
                return currentState;
        }
        else
            return currentState;
    }
}
