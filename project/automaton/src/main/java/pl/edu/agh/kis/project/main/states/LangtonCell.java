package pl.edu.agh.kis.project.main.states;

/**
 * Created by Kamil on 23.11.2015.
 */
public class LangtonCell implements CellState{
    public final BinaryState cellState;
    public final int antId;
    public final AntState antState;

    public LangtonCell(BinaryState cellState, int antId, AntState antState) {
        this.cellState = cellState;
        this.antId = antId;
        this.antState = antState;
    }
}
