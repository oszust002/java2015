package pl.edu.agh.kis.project.main.model.states;

/**
 * Created by Kamil on 23.11.2015.
 */
public enum AntState {
    NONE, NORTH, SOUTH, EAST, WEST;

    @Override
    public String toString() {
        switch (this){
            case NONE:
                return " ";
            case NORTH:
                return "N";
            case SOUTH:
                return "S";
            case EAST:
                return "E";
            case WEST:
                return "W";
            default:
                return "";
        }
    }

    public static AntState rotateRight(AntState state){
        switch (state){
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static AntState rotateLeft(AntState state){
        switch (state){
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            default:
                throw new IllegalArgumentException();
        }
    }
}
