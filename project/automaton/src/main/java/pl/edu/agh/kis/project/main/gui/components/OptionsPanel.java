package pl.edu.agh.kis.project.main.gui.components;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.Ticker;
import pl.edu.agh.kis.project.main.gui.boards.Board;
import pl.edu.agh.kis.project.main.gui.boards.Board2D;
import pl.edu.agh.kis.project.main.model.automaton.AutomatonFactory;
import pl.edu.agh.kis.project.main.model.automaton.GameOfLife;
import pl.edu.agh.kis.project.main.model.automaton.StructuresContainer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Kamil on 03.01.2016.
 */
public class OptionsPanel extends Observable implements Observer{
    private JPanel options;
    private JPanel chosenGame;
    private JPanel spinnerPanel;
    private AutomatonTypeComboBox comboBox;
    private Buttons buttons;
    private RulesPanel rulesPanel;
    private WidthSpinner spinnerX;
    private HeightSpinner spinnerY;
    private AutomatonFactory factory;
    private InsertStructureButton insertButton;


    public OptionsPanel(Board2D board, Dimension size, AutomatonBox automatonBox, Ticker ticker){
        buttons = new Buttons(automatonBox,ticker);
        rulesPanel = new RulesPanel();
        options = new JPanel();
        options.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        options.setPreferredSize(size);
        chosenGame = new JPanel();
        chosenGame.setLayout(new GridLayout(2,2,10,10));
        comboBox = new AutomatonTypeComboBox();
        spinnerX = new WidthSpinner();
        spinnerY = new HeightSpinner();
        spinnerPanel = new JPanel();
        spinnerPanel.setLayout(new GridLayout(1,2,5,5));
        chosenGame.add(comboBox.getComboBoxPanel());
        spinnerPanel.add(spinnerX.getSpinnerPanel());
        spinnerPanel.add(spinnerY.getSpinnerPanel());
        chosenGame.add(spinnerPanel);
        chosenGame.setBorder(BorderFactory.createLineBorder(Color.black));
        options.add(chosenGame);
        options.add(buttons.getJPanel());
        options.add(rulesPanel.getJPanel());
        factory = new AutomatonFactory();
        insertButton = new InsertStructureButton(board, StructuresContainer.get(GameOfLife.class));
        options.add(insertButton.getInsertButton());
        buttons.addObserver(insertButton);
        spinnerY.addChangeListener(new ChangeSizeListener());
        spinnerX.addChangeListener(new ChangeSizeListener());
        comboBox.addActionListener(new AutomatonChangeActionListener());
        buttons.addObserver(this);
        rulesPanel.addObserver(automatonBox);
        this.addObserver(rulesPanel);
        this.addObserver(insertButton);
    }

    public JPanel getMainOptionsPanel(){
        return options;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.getClass().equals(Boolean.class)) {
            boolean enabled = (Boolean) arg;
            spinnerX.getSpinner().setEnabled(enabled);
            if(comboBox.getComboBox().getSelectedItem() == "Automat jednowymiarowy")
                spinnerY.getSpinner().setEnabled(false);
            else
                spinnerY.getSpinner().setEnabled(enabled);
            comboBox.getComboBox().setEnabled(enabled);
            if(!enabled)
                rulesPanel.setEverythingEnabled(false);
            else
                rulesPanel.resetToExpectedEnabledPanels();

        }
    }

    public void setNewBoardToInserts(Board2D board2D){
        insertButton.setNewBoard(board2D);
    }

    private static class WidthSpinner{
        private final JSpinner width;
        private final NamedComponentPanel namedComponentPanel;

        public WidthSpinner(){
            width = new JSpinner(new SpinnerNumberModel(20,1,300,1));
            namedComponentPanel = new NamedComponentPanel("Szerokosc", width);
        }

        public Integer getWidth(){
            return (Integer) width.getValue();
        }

        public JPanel getSpinnerPanel(){
            return namedComponentPanel.getjPanel();
        }

        public JSpinner getSpinner(){
            return width;
        }

        public void addChangeListener(ChangeListener listener){
            width.addChangeListener(listener);
        }
    }

    private static class HeightSpinner{
        private final JSpinner height;
        private final NamedComponentPanel namedComponentPanel;

        public HeightSpinner(){
            height = new JSpinner(new SpinnerNumberModel(20,1,300,1));
            namedComponentPanel = new NamedComponentPanel("Wysokosc",height);
        }

        public Integer getHeight(){
            return (Integer) height.getValue();
        }

        public JPanel getSpinnerPanel(){
            return namedComponentPanel.getjPanel();
        }
        public JSpinner getSpinner(){
            return height;
        }

        public void addChangeListener(ChangeListener listener){
            height.addChangeListener(listener);
        }
    }

    private static class AutomatonTypeComboBox{
        private final String[] automatonNames = new String[]{
                "Game of Life","WireWorld", "QuadLife", "LangtonAnt", "Automat jednowymiarowy"
        };
        private final JComboBox<String> comboBox;
        private final NamedComponentPanel namedComponentPanel;
        public AutomatonTypeComboBox(){
            comboBox = new JComboBox<String>(automatonNames);
            namedComponentPanel = new NamedComponentPanel("Typ automatu",comboBox);
        }

        public void addActionListener(ActionListener listener){
            comboBox.addActionListener(listener);
        }

        public JPanel getComboBoxPanel(){
            return namedComponentPanel.getjPanel();
        }

        public JComboBox<String> getComboBox(){
            return comboBox;
        }
    }

    private class ChangeSizeListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            factory.setWidth(spinnerX.getWidth());
            factory.setHeight(spinnerY.getHeight());
            setChanged();
            notifyObservers(factory);
        }
    }

    private class AutomatonChangeActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox comboBox = (JComboBox) e.getSource();
            String name = (String) comboBox.getSelectedItem();
            if(name.equals("Automat jednowymiarowy"))
                spinnerY.getSpinner().setEnabled(false);
            else
                spinnerY.getSpinner().setEnabled(true);
            setChanged();
            notifyObservers(name);
        }
    }
}
