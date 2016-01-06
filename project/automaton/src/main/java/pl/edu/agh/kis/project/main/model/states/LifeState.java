package pl.edu.agh.kis.project.main.model.states;

/**
 * Interface extending {@link CellState}, used in {@link pl.edu.agh.kis.project.main.model.automaton.GameOfLife} and
 * {@link pl.edu.agh.kis.project.main.model.automaton.QuadLife}
 * @author Kamil Osuch
 * @version 1.0
 */
public interface LifeState extends CellState {
    /**
     * Checks if specified state is alive state
     * @return True if alive, False otherwise
     */
    boolean isAlive();
}
