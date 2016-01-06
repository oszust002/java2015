package pl.edu.agh.kis.project.main.model.automaton;


import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.WireElectronState;

import java.util.Set;

/**
 * Created by Kamil on 07.12.2015.
 */
public class WireWorld extends Automaton2Dim {
    public WireWorld(CellStateFactory stateFactory, int width, int height,boolean wrap) {
        super(new MoorNeighbourhood(1,width,height,wrap), stateFactory, width, height);
    }

    @Override
    protected Automaton newInstance(CellStateFactory factory, CellNeighborhood neighbourhood) {
        return new WireWorld(factory,getWidth(),getHeight(),getWrap());
    }

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

    @Override
    public Class<? extends CellState> getCellStateClass() {
        return WireElectronState.class;
    }
}
