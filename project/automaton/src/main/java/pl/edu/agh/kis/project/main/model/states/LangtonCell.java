package pl.edu.agh.kis.project.main.model.states;

/**
 * Implementation of {@link CellState} for {@link pl.edu.agh.kis.project.main.model.automaton.LangtonAnt}
 * @author Kamil Osuch
 * @version 1.0
 */
public class LangtonCell implements CellState{
    public final BinaryState cellState;
    public final int antId;
    public final AntState antState;

    /**
     * Creates state with living ant, with specified id, {@link AntState}, and {@link BinaryState}
     * @param cellState {@link BinaryState} of cell beneath ant
     * @param antId ID of an ant
     * @param antState ant direction
     */
    public LangtonCell(BinaryState cellState, int antId, AntState antState) {
        this.cellState = cellState;
        this.antId = antId;
        this.antState = antState;
    }

    /**
     * Creates state without ant, with specified {@link BinaryState}
     * @param state {@link BinaryState} of cell
     */
    public LangtonCell(BinaryState state){
        this(state, 0, AntState.NONE);
    }

    /**
     * Check if ant is on the field
     * @return True if there is ant, False otherwise
     */
    public boolean hasAnt(){
        return antId != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellState next(){
        if(!hasAnt() && cellState == BinaryState.DEAD){
            return new LangtonCell(BinaryState.ALIVE);
        }else if(hasAnt() && antState == AntState.WEST && cellState == BinaryState.DEAD){
            return new LangtonCell(BinaryState.ALIVE,antId,AntState.NORTH);
        }else {
            return new LangtonCell(cellState,antId, AntState.rotateRight(antState));
        }
    }

    /**
     * Converts {@link LangtonCell} to String
     * @return converted {@link LangtonCell}
     */
    @Override
    public String toString(){
        if(hasAnt()){
            return cellState.toString() +
                    antId +
                    antState.toString();
        }else
            return cellState.toString();
    }

    /**
     * Check equality of two {@link LangtonCell}
     * @param obj second {@link LangtonCell} to check equality
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(!obj.getClass().equals(getClass()))
            return false;
        LangtonCell second = (LangtonCell) obj;
        return cellState == second.cellState && antId == second.antId && antState == second.antState;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 31;
    }
}
