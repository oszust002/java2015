package pl.edu.agh.kis.project.main.model.states;

/**
 * Implementation of {@link CellState} used in {@link pl.edu.agh.kis.project.main.model.automaton.QuadLife}
 * @author Kamil Osuch
 * @version 1.0
 */
public enum QuadState implements LifeState{
    DEAD, RED, YELLOW, BLUE, GREEN;

    /**
     * Converts {@link QuadState} to String
     * @return converted {@link QuadState}
     */
    @Override
    public String toString(){
        switch (this){
            case DEAD:
                return "0";
            case RED:
                return "R";
            case YELLOW:
                return "Y";
            case BLUE:
                return "B";
            case GREEN:
                return "G";
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
