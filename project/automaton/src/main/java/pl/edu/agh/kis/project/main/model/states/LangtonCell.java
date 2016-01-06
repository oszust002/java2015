package pl.edu.agh.kis.project.main.model.states;

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

    public LangtonCell(BinaryState state){
        this(state, 0, AntState.NONE);
    }

    public boolean hasAnt(){
        return antId != 0;
    }

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

    @Override
    public String toString(){
        if(hasAnt()){
            return cellState.toString() +
                    antId +
                    antState.toString();
        }else
            return cellState.toString();
    }

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
