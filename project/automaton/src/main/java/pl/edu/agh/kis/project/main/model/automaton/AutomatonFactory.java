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
 * Created by Kamil on 02.01.2016.
 */
public class AutomatonFactory {
    private Class<? extends Automaton> automatonClass = GameOfLife.class;
    private String neighbourhood2DimName = "Moore";
    private String rule = "";
    private boolean wrap = false;
    private int width = 20;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private int height = 20;
    private int r = 1;

    public AutomatonFactory(AutomatonFactory factory){
        automatonClass = factory.automatonClass;
        neighbourhood2DimName = factory.neighbourhood2DimName;
        rule = factory.rule;
        wrap = factory.wrap;
        width = factory.width;
        height = factory.height;
        r = factory.r;
    }

    public AutomatonFactory(){}

    public void setNeighboorhood2Dim(String neighboorhood2Dim) {
        if(neighboorhood2Dim.equals("Moore") || neighboorhood2Dim.equals("VonNeumann"))
            this.neighbourhood2DimName = neighboorhood2Dim;
        else
            throw new IllegalArgumentException("Only VonNeumann or Moore");
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setAutomatonClass(Class<? extends Automaton> automatonClass){
        this.automatonClass = automatonClass;
    }

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
