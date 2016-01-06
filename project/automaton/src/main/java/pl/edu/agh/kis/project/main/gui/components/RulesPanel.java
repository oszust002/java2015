package pl.edu.agh.kis.project.main.gui.components;

import pl.edu.agh.kis.project.main.model.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.model.neighbourhood.VonNeumanNeighborhood;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Kamil on 05.01.2016.
 */
public class RulesPanel extends Observable implements Observer {
    private JPanel rules;
    private OneDimRuleSpinner ruleSpinner;
    private TwoDimRuleTextField ruleTextField;
    private NeighbourhoodComboBox neighbourhoodComboBox;
    private WrapCheckBox wrapCheckBox;
    private RadiusSpinner radiusSpinner;
    private boolean[] enabledPanels = {false,true,true,true,true};



    public RulesPanel() {
        rules = new JPanel();
        ruleSpinner = new OneDimRuleSpinner();
        ruleTextField = new TwoDimRuleTextField();
        neighbourhoodComboBox = new NeighbourhoodComboBox();
        radiusSpinner = new RadiusSpinner();
        wrapCheckBox = new WrapCheckBox();
        ruleSpinner.getRuleSpinner().setEnabled(false);
        rules.add(ruleSpinner.getSpinnerPanel());
        rules.add(ruleTextField.getTextFieldPanel());
        rules.add(neighbourhoodComboBox.getComboBoxPanel());
        rules.add(radiusSpinner.getRadiusSpinnerPanel());
        rules.add(wrapCheckBox.getCheckBoxPanel());
        rules.setLayout(new BoxLayout(rules, BoxLayout.PAGE_AXIS));
        rules.setBorder(BorderFactory.createLineBorder(Color.black));
        ruleSpinner.addChangeListener(new RuleChangeListener());
        ruleTextField.addActionListener(new Rule2DimListener());
        neighbourhoodComboBox.addActionListener(new NeighbourhoodActionListener());
        radiusSpinner.addChangeListener(new RadiusChangeListener());
        wrapCheckBox.addActionListener(new WrapActionListener());
    }

    public JPanel getJPanel() {
        return rules;
    }

    private void setEnabledPanels(boolean rule1Dim, boolean ruleGOL, boolean radius, boolean neighbours,
                                  boolean wrap){
        enabledPanels[0] = rule1Dim;
        enabledPanels[1] = ruleGOL;
        enabledPanels[2] = radius;
        enabledPanels[3] = neighbours;
        enabledPanels[4] = wrap;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.getClass().equals(String.class)) {
            String name = (String) arg;
            if (name.equals("Game of Life")) {
                setOneByOneEnabled(false, true, true, true, true);
                setEnabledPanels(false,true,true,true,true);
            }
            else if (name.equals("WireWorld")) {
                setOneByOneEnabled(false, false, false, false, true);
                setEnabledPanels(false, false, false, false, true);
            }
            else if (name.equals("QuadLife")) {
                setOneByOneEnabled(false, false, true, true, true);
                setEnabledPanels(false, false, true, true, true);
            }
            else if (name.equals("Automat jednowymiarowy")) {
                setOneByOneEnabled(true, false, false, false, true);
                setEnabledPanels(true, false, false, false, true);
            }
            else if(name.equals("LangtonAnt")){
                setOneByOneEnabled(false, false, false, false, true);
                setEnabledPanels(false,false,false,false,true);
            }
        }
    }

    public void setEverythingEnabled(boolean enabled){
        setOneByOneEnabled(enabled,enabled,enabled,enabled,enabled);
    }

    public void resetToExpectedEnabledPanels(){
        setOneByOneEnabled(enabledPanels[0],enabledPanels[1],enabledPanels[2],enabledPanels[3],enabledPanels[4]);
    }

    private void setOneByOneEnabled(boolean rule1Dim, boolean ruleGOL, boolean radius, boolean neighbours,
                                    boolean wrap){
        ruleTextField.getRuleTextField().setEnabled(ruleGOL);
        radiusSpinner.getRadiusSpinner().setEnabled(radius);
        ruleSpinner.getRuleSpinner().setEnabled(rule1Dim);
        neighbourhoodComboBox.getComboBox().setEnabled(neighbours);
        wrapCheckBox.getWrapCheckBox().setEnabled(wrap);
    }

    private static class RadiusSpinner{
        private final JSpinner radiusSpinner;
        private final NamedComponentPanel namedComponentPanel;

        public RadiusSpinner(){
            radiusSpinner = new JSpinner(new SpinnerNumberModel(1,1,30,1));
            namedComponentPanel = new NamedComponentPanel("Promien sasiedztwa[1-30]",radiusSpinner);
        }

        public JSpinner getRadiusSpinner(){
            return radiusSpinner;
        }

        public JPanel getRadiusSpinnerPanel(){
            return namedComponentPanel.getjPanel();
        }

        public void addChangeListener(ChangeListener listener){
            radiusSpinner.addChangeListener(listener);
        }
    }

    private static class OneDimRuleSpinner {
        private final JSpinner ruleSpinner;
        private final NamedComponentPanel namedComponentPanel;


        public OneDimRuleSpinner() {
            ruleSpinner = new JSpinner(new SpinnerNumberModel(30, 0, 255, 1));
            namedComponentPanel = new NamedComponentPanel("Zasada dla jednowymiarowego[0-255]", ruleSpinner);
        }

        public Integer getRule() {
            return (Integer) ruleSpinner.getValue();
        }

        public JSpinner getRuleSpinner(){
            return ruleSpinner;
        }

        public JPanel getSpinnerPanel() {
            return namedComponentPanel.getjPanel();
        }

        public void addChangeListener(ChangeListener changeListener) {
            ruleSpinner.addChangeListener(changeListener);
        }
    }

    private static class TwoDimRuleTextField {
        private final JTextField ruleTextField;
        private final NamedComponentPanel namedComponentPanel;

        public TwoDimRuleTextField() {
            ruleTextField = new JTextField();
            namedComponentPanel = new NamedComponentPanel("Zasada dla GameOfLife", ruleTextField);
        }

        public String getRule() {
            return ruleTextField.getText();
        }

        public JTextField getRuleTextField(){
            return ruleTextField;
        }

        public JPanel getTextFieldPanel() {
            return namedComponentPanel.getjPanel();
        }

        public void addActionListener(ActionListener listener) {
            ruleTextField.addActionListener(listener);
        }
    }

    private static class NeighbourhoodComboBox {
        private final String[] neighbours = {"Moore", "VonNeumann"};
        private final JComboBox<String> neighbourhood;
        private final NamedComponentPanel namedComponentPanel;

        public NeighbourhoodComboBox() {
            neighbourhood = new JComboBox<String>(neighbours);
            namedComponentPanel = new NamedComponentPanel("Sasiedztwo", neighbourhood);
        }

        public JPanel getComboBoxPanel() {
            return namedComponentPanel.getjPanel();
        }

        public JComboBox getComboBox(){
            return neighbourhood;
        }

        public void addActionListener(ActionListener listener) {
            neighbourhood.addActionListener(listener);
        }
    }

    private static class WrapCheckBox {
        private final JCheckBox wrap;
        private final NamedComponentPanel namedComponentPanel;

        public WrapCheckBox() {
            wrap = new JCheckBox("Zawijac");
            namedComponentPanel = new NamedComponentPanel("Czy zawijac?", wrap);
        }

        public JCheckBox getWrapCheckBox(){
            return wrap;
        }

        public JPanel getCheckBoxPanel() {
            return namedComponentPanel.getjPanel();
        }

        public void addActionListener(ActionListener listener) {
            wrap.addActionListener(listener);
        }
    }

    private class RuleChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            setChanged();
            notifyObservers(ruleSpinner.getRule());
        }
    }

    private class Rule2DimListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            if(!ruleTextField.getRule().matches("([0-9]*)/([0-9]*)"))
                JOptionPane.showMessageDialog(null,"Niepoprawny format! \nPrzykładowo poprawnie: 23/3", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            else {
                setChanged();
                notifyObservers(ruleTextField.getRule());
            }
        }
    }

    private class NeighbourhoodActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox comboBox = (JComboBox) e.getSource();
            String neighbourhoodName = (String) comboBox.getSelectedItem();
            setChanged();
            if(neighbourhoodName.equals("Moore"))
                notifyObservers(new MoorNeighbourhood(1,1,1,false));
            else if(neighbourhoodName.equals("VonNeumann"))
                notifyObservers(new VonNeumanNeighborhood(1,1,1,false));
        }
    }

    private class RadiusChangeListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = (JSpinner) e.getSource();
            setChanged();
            notifyObservers(spinner);
        }
    }

    private class WrapActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            AbstractButton abstractButton = (AbstractButton) e.getSource();
            setChanged();
            Boolean isSelected = abstractButton.getModel().isSelected();
            notifyObservers(isSelected);
        }
    }
}
