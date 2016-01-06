package pl.edu.agh.kis.project.main.model.states;

/**
 * Represents state for different {@link pl.edu.agh.kis.project.main.model.automaton.Automaton}
 * @author Kamil Osuch
 * @version 1.0
 */
public interface CellState {
    /**
     * Get next possible state. Used for iterating through states in circular way
     * @return next possible state
     */
    CellState next();
}
