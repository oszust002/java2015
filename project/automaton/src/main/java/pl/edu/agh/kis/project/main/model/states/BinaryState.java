package pl.edu.agh.kis.project.main.model.states;

/**
 * Created by Kamil on 23.11.2015.
 */
public enum BinaryState implements LifeState{
    DEAD, ALIVE;

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
    @Override
    public boolean isAlive(){
        return this != DEAD;
    }

    @Override
    public CellState next(){
        return values()[(this.ordinal()+1) % values().length];
    }
}
