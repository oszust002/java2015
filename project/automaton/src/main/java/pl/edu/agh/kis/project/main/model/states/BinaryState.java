package pl.edu.agh.kis.project.main.model.states;

/**
 * Implementation of {@link CellState} for {@link pl.edu.agh.kis.project.main.model.automaton.GameOfLife}.
 * Also used in {@link LangtonCell}
 * @author Kamil Osuch
 * @version 1.0
 */
public enum BinaryState implements LifeState{
    DEAD, ALIVE;

    /**
     * Converts {@link BinaryState} to String
     * @return converted {@link BinaryState}
     */
    @Override
    public String toString(){
        switch(this){
            case DEAD:
                return "0";
            case ALIVE:
                return "X";
            default:
                return "";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive(){
        return this != DEAD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState next(){
        return values()[(this.ordinal()+1) % values().length];
    }
}
