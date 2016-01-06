package pl.edu.agh.kis.project.main.model.states;

/**
 * Representation of ant direction in {@link LangtonCell}
 * @author Kamil Osuch
 * @version 1.0
 */
public enum AntState {
    NONE, NORTH, SOUTH, EAST, WEST;

    /**
     * Converts {@link AntState} to String
     * @return converted {@link AntState}
     */
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

    /**
     * Returns new ant direction after turning left
     * @param state current ant direction
     * @return new ant direction
     */
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

    /**
     * Returns new ant direction after turning left
     * @param state current ant direction
     * @return new ant direction
     */
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
