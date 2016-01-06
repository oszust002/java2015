package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.model.factory.UniformStateFactory;
import pl.edu.agh.kis.project.main.model.neighbourhood.Cell2DimNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.VonNeumanNeighborhood;
import pl.edu.agh.kis.project.main.model.states.BinaryState;
import pl.edu.agh.kis.project.main.model.states.LangtonCell;
import pl.edu.agh.kis.project.main.model.states.QuadState;
import pl.edu.agh.kis.project.main.model.states.WireElectronState;

/**
 * Factory that creates new instances of Automaton basing on held information
 * @author Kamil Osuch
 * @version 1.0
 */
public class AutomatonFactory {
    private Class<? extends Automaton> automatonClass = GameOfLife.class;
    private String neighbourhood2DimName = "Moore";
    private String rule = "";
    private boolean wrap = false;
    private int width = 20;
    private int height = 20;
    private int r = 1;

    /**
     * Gets width of possibly future created Automaton
     * @return possible width of new Automaton
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets height of possibly future created Automaton
     * @return possible height of new Automaton
     */
    public int getWidth() {
        return width;
    }

    /**
     * Creates new Automaton factory based on another factory
     * @param factory factory that
     */
    public AutomatonFactory(AutomatonFactory factory){
        automatonClass = factory.automatonClass;
        neighbourhood2DimName = factory.neighbourhood2DimName;
        rule = factory.rule;
        wrap = factory.wrap;
        width = factory.width;
        height = factory.height;
        r = factory.r;
    }

    /**
     * Creates new Automaton factory with default values
     */
    public AutomatonFactory(){}

    /**
     * Sets new {@link Cell2DimNeighbourhood} of possible future {@link Automaton2Dim}
     * @param neighboorhood2Dim neighbourhood name
     */
    public void setNeighboorhood2Dim(String neighboorhood2Dim) {
        if(neighboorhood2Dim.equals("Moore") || neighboorhood2Dim.equals("VonNeumann"))
            this.neighbourhood2DimName = neighboorhood2Dim;
        else
            throw new IllegalArgumentException("Only VonNeumann or Moore");
    }

    /**
     * Sets new rule of possible future Automaton
     * @param rule rule given as String
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * Sets new board wrapping of possible future Automaton
     * @param wrap wrapping board(True if wrap board, False if not)
     */
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    /**
     * Sets new width of possible future Automaton
     * @param width new possible width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets new height of possible future Automaton
     * @param height new possible height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets new radius for neighbourhood of possible future Automaton
     * @param r new possible radius
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * Sets new class of possible future Automaton
     * @param automatonClass possible class of new Automaton
     */
    public void setAutomatonClass(Class<? extends Automaton> automatonClass){
        this.automatonClass = automatonClass;
    }

    /**
     * Sets new class of possible future Automaton, basing on Automaton name
     * @param name name of new possible Automaton
     */
    public void setAutomatonClassByName(String name){
        switch (name){
            case "Game of Life":
                automatonClass = GameOfLife.class;
                break;
            case "WireWorld":
                automatonClass = WireWorld.class;
                break;
            case "QuadLife":
                automatonClass = QuadLife.class;
                break;
            case "LangtonAnt":
                automatonClass = LangtonAnt.class;
                break;
            case "Automat jednowymiarowy":
                automatonClass = Automaton1Dim.class;
                break;
            default:
                throw new IllegalArgumentException("Don't handle that kind of automaton");
        }
    }

    /**
     * Creates new Automaton based on set values
     * @return new Automaton
     * @throws AssertionError if no implemented Automaton fits set values
     */
    public Automaton createAutomaton(){
        Cell2DimNeighbourhood neighborhood;
        if(neighbourhood2DimName.equals("Moore"))
            neighborhood = new MoorNeighbourhood(r,width,height,wrap);
        else
            neighborhood = new VonNeumanNeighborhood(r,width,height,wrap);
        if(automatonClass.equals(GameOfLife.class)) {
            if(rule.isEmpty() || rule.matches("[0-9]+"))
                return new GameOfLife(neighborhood, new UniformStateFactory(BinaryState.DEAD), "23/3", width, height);
            else
                return new GameOfLife(neighborhood, new UniformStateFactory(BinaryState.DEAD), rule, width, height);
        }
        else if(automatonClass.equals(Automaton1Dim.class))
            if(rule.isEmpty())
                return new Automaton1Dim(new UniformStateFactory(BinaryState.DEAD),30,width,wrap);
            else
                return new Automaton1Dim(new UniformStateFactory(BinaryState.DEAD),Integer.parseInt(rule),width,wrap);
        else if(automatonClass.equals(QuadLife.class))
            return new QuadLife(neighborhood,new UniformStateFactory(QuadState.DEAD),width,height);
        else if(automatonClass.equals(WireWorld.class))
            return new WireWorld(new UniformStateFactory(WireElectronState.VOID),width,height,wrap);
        else if(automatonClass.equals(LangtonAnt.class)){
            return new LangtonAnt(new UniformStateFactory(new LangtonCell(BinaryState.DEAD)),width,height,wrap);
        }
        throw new AssertionError("Not handled automaton");
    }


}
