package pl.edu.agh.kis.project.main.model.automaton;


import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.WireElectronState;

import java.util.Set;

/**
 * Implementation of WireWorld Automaton
 * @author Kamil Osuch
 * @version 1.0
 */
public class WireWorld extends Automaton2Dim {
    /**
     * Creates Automaton based on {@link CellStateFactory}, width and height with wrapping
     * @param stateFactory {@link CellStateFactory} of initial states
     * @param width width of Automaton
     * @param height height of Automaton
     * @param wrap True if board will be wrapped, False otherwise
     */
    public WireWorld(CellStateFactory stateFactory, int width, int height,boolean wrap) {
        super(new MoorNeighbourhood(1,width,height,wrap), stateFactory, width, height);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new WireWorld(factory,getWidth(),getHeight(),getWrap());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    protected CellState nextCellState(Cell current, Set<Cell> neighborsStates) {
        WireElectronState state = (WireElectronState) current.state;
        switch (state){
            case VOID:
                return state;
            case ELECTRON_HEAD:
                return WireElectronState.ELECTRON_TAIL;
            case ELECTRON_TAIL:
                return WireElectronState.WIRE;
            case WIRE:{
                int occurences = 0;
                for(Cell cell: neighborsStates)
                    if(cell.state == WireElectronState.ELECTRON_HEAD)
                        occurences++;
                    if(occurences==1 || occurences==2)
                        return WireElectronState.ELECTRON_HEAD;
                    else
                        return state;
            }
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Class<? extends CellState> getCellStateClass() {
        return WireElectronState.class;
    }
}
