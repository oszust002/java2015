package pl.edu.agh.kis.project.main.states;

/**
 * Created by Kamil on 23.11.2015.
 */
public enum WireElectronState implements CellState{
    VOID, WIRE, ELECTRON_HEAD, ELECTRON_TAIL;

    @Override
    public String toString(){
        switch (this){
            case VOID:
                return "0";
            case WIRE:
                return "W";
            case ELECTRON_HEAD:
                return "H";
            case ELECTRON_TAIL:
                return "T";
            default:
                return null;
        }
    }
}
