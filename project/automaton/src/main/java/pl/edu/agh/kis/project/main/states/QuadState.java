package pl.edu.agh.kis.project.main.states;

/**
 * Created by Kamil on 23.11.2015.
 */
public enum QuadState implements LifeState{
    DEAD, RED, YELLOW, BLUE, GREEN;


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
                return null;
        }
    }

    @Override
    public boolean isAlive(){
        return this != DEAD;
    }
}
