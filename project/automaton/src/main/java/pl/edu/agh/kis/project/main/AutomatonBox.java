package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.exceptions.InvalidAutomatonException;
import pl.edu.agh.kis.project.main.model.automaton.*;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.neighbourhood.Cell2DimNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.VonNeumanNeighborhood;
import pl.edu.agh.kis.project.main.model.states.CellState;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Kamil on 04.01.2016.
 */
public class AutomatonBox extends Observable implements Observer{
    private Automaton automaton;
    private AutomatonFactory automatonFactory;

    public AutomatonBox(Automaton automaton){
        this.automaton = automaton;
        automatonFactory = new AutomatonFactory();
        automatonFactory.setAutomatonClass(automaton.getClass());
        if(automaton instanceof Automaton2Dim){
            automatonFactory.setHeight(((Automaton2Dim) automaton).getHeight());
            automatonFactory.setWidth(((Automaton2Dim) automaton).getHeight());
        }
        else if(automaton.getClass().equals(Automaton1Dim.class)){
            automatonFactory.setWidth(((Automaton1Dim)automaton).getSize());
        }
        automatonFactory.setWrap(automaton.getWrap());
        automatonFactory.setNeighboorhood2Dim(automaton.getNeighborhoodName());
    }

    public Automaton getAutomaton() {
        return automaton;
    }

    public void setAutomaton(Automaton automaton){
        this.automaton = automaton;
        automatonFactory.setAutomatonClass(automaton.getClass());
        if(automaton instanceof Automaton2Dim){
            automatonFactory.setHeight(((Automaton2Dim) automaton).getHeight());
            automatonFactory.setWidth(((Automaton2Dim) automaton).getHeight());
        }
        else if(automaton.getClass().equals(Automaton1Dim.class)){
            automatonFactory.setWidth(((Automaton1Dim)automaton).getSize());
        }
        automatonFactory.setWrap(automaton.getWrap());
        if(automaton.getClass().equals(GameOfLife.class) || automaton.getClass().equals(QuadLife.class))
            automatonFactory.setNeighboorhood2Dim(automaton.getNeighborhoodName());
        setChanged();
        notifyObservers(automaton);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Automaton) {
            automaton = (Automaton) arg;
            setChanged();
            notifyObservers(automaton);
        }else if(arg.getClass().equals(String.class)){
            if(automaton.getClass().equals(GameOfLife.class))
                ((GameOfLife)automaton).setRule((String)arg);
            automatonFactory.setRule((String)arg);
        }else if(arg.getClass().equals(Integer.class)){
            if(automaton.getClass().equals(Automaton1Dim.class)){
                ((Automaton1Dim)automaton).setRule((Integer)arg);
            }
            automatonFactory.setRule(arg.toString());
        }else if(arg instanceof Cell2DimNeighbourhood){
            Cell2DimNeighbourhood neighbourhood = (Cell2DimNeighbourhood) arg;
            if (automaton instanceof Automaton2Dim){
                Automaton2Dim automaton2Dim = (Automaton2Dim) automaton;
                neighbourhood.setWrap(automaton.getWrap());
                neighbourhood.setNewSize(new Coords2D(automaton2Dim.getWidth(),automaton2Dim.getHeight()));
                automaton.set2DimNeighborsStrategy(neighbourhood);
            }
            if(neighbourhood.getClass().equals(MoorNeighbourhood.class))
                automatonFactory.setNeighboorhood2Dim("Moore");
            else if(neighbourhood.getClass().equals(VonNeumanNeighborhood.class))
                automatonFactory.setNeighboorhood2Dim("VonNeumann");
        }else if(arg.getClass().equals(JSpinner.class)){
            JSpinner spinner = (JSpinner) arg;
            Integer newRadius = (Integer) spinner.getValue();
            if(automaton.getClass().equals(GameOfLife.class) || automaton.getClass().equals(QuadLife.class)){
                automaton.changeRadius(newRadius);
            }
            automatonFactory.setR(newRadius);
        }else if (arg.getClass().equals(Boolean.class)){
            automaton.setWrap((Boolean)arg);
            automatonFactory.setWrap((Boolean)arg);
        }
    }

    public AutomatonFactory getFactory(){
        return automatonFactory;
    }

    public void putStructure(CellState[][] structure, Coords2D start) throws InvalidAutomatonException {
        try {
            ((Automaton2Dim) automaton).putStructure(structure, start);
            setChanged();
            notifyObservers(automaton);
        }catch (ClassCastException e){
            throw new InvalidAutomatonException();
        }
    }
}
