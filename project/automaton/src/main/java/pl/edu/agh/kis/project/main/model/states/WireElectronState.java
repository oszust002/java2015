package pl.edu.agh.kis.project.main.model.states;

/**
 * Implementation of {@link CellState} used in {@link pl.edu.agh.kis.project.main.model.automaton.WireWorld}
 */
public enum WireElectronState implements CellState{
    VOID, WIRE, ELECTRON_HEAD, ELECTRON_TAIL;

    /**
     * Converts {@link WireElectronState} to String
     * @return converted {@link WireElectronState}
     */
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
                return "";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState next(){
        return values()[(this.ordinal()+1) % values().length];
    }
}
