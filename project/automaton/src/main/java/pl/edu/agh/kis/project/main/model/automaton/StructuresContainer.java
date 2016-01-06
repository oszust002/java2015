package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.model.states.BinaryState;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.WireElectronState;

import java.util.HashMap;

/**
 * Container of structures which can be added to Automaton
 * @author Kamil Osuch
 * @version 1.0
 */
public class StructuresContainer {
    private static final HashMap<Class<? extends Automaton2Dim>,Structure[]> structures = insertAllStructures();

    private static HashMap<Class<? extends Automaton2Dim>,Structure[]> insertAllStructures(){
        HashMap<Class<? extends Automaton2Dim>,Structure[]> structurePack = new HashMap<Class<? extends Automaton2Dim>,Structure[]>();
        final CellState[][] glider = new CellState[][]{
                {BinaryState.DEAD,BinaryState.DEAD,BinaryState.ALIVE},
                {BinaryState.ALIVE,BinaryState.DEAD,BinaryState.ALIVE},
                {BinaryState.DEAD,BinaryState.ALIVE,BinaryState.ALIVE}
        };
        final CellState[][] blinker = new CellState[][]{
                {BinaryState.ALIVE,BinaryState.ALIVE,BinaryState.ALIVE}
        };
        final CellState[][] gliderGun = new CellState[][]{
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD},
                {BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD}
        };

        final CellState[][] generator = new CellState[][]{
                {WireElectronState.WIRE,WireElectronState.ELECTRON_TAIL,WireElectronState.ELECTRON_HEAD},
                {WireElectronState.WIRE,WireElectronState.VOID,WireElectronState.WIRE},
                {WireElectronState.WIRE,WireElectronState.WIRE,WireElectronState.WIRE}
        };

        final CellState[][] notGate = new CellState[][]{
                {WireElectronState.VOID,WireElectronState.WIRE,WireElectronState.VOID},
                {WireElectronState.WIRE,WireElectronState.WIRE,WireElectronState.WIRE},
                {WireElectronState.WIRE,WireElectronState.WIRE,WireElectronState.WIRE}
        };

        final CellState[][] xorGate = new CellState[][]{
                {WireElectronState.VOID,WireElectronState.WIRE,WireElectronState.VOID,WireElectronState.VOID,
                        WireElectronState.VOID,WireElectronState.VOID},
                {WireElectronState.VOID,WireElectronState.WIRE,WireElectronState.VOID,WireElectronState.VOID,
                        WireElectronState.VOID,WireElectronState.VOID},
                {WireElectronState.WIRE,WireElectronState.WIRE,WireElectronState.WIRE,WireElectronState.WIRE,
                        WireElectronState.VOID,WireElectronState.VOID},
                {WireElectronState.WIRE,WireElectronState.VOID,WireElectronState.WIRE,WireElectronState.WIRE,
                        WireElectronState.WIRE,WireElectronState.WIRE},
                {WireElectronState.WIRE,WireElectronState.WIRE,WireElectronState.WIRE,WireElectronState.WIRE,
                        WireElectronState.VOID,WireElectronState.VOID},
                {WireElectronState.VOID,WireElectronState.WIRE,WireElectronState.VOID,WireElectronState.VOID,
                        WireElectronState.VOID,WireElectronState.VOID},
                {WireElectronState.VOID,WireElectronState.WIRE,WireElectronState.VOID,WireElectronState.VOID,
                        WireElectronState.VOID,WireElectronState.VOID}
        };

        final Structure[] golStructures = {
                new Structure("Glider", GameOfLife.class, glider),
                new Structure("Blinker", GameOfLife.class,blinker),
                new Structure("Glider gun", GameOfLife.class, gliderGun)
        };
        final Structure[] wireWorldStructures = {
                new Structure("FastSmallGenerator",WireWorld.class,generator),
                new Structure("Gate-NOT",WireWorld.class,notGate),
                new Structure("Gate-XOR",WireWorld.class,xorGate)
        };
        structurePack.put(GameOfLife.class,golStructures);
        structurePack.put(WireWorld.class,wireWorldStructures);
        return structurePack;
    }

    /**
     * Gets array of {@link Structure} for specified Automaton
     * @param automaton2DClass automaton class
     * @return array of {@link Structure} for specified Automaton
     */
    public static Structure[] get(Class<? extends Automaton2Dim> automaton2DClass){
        return structures.get(automaton2DClass);
    }

    /**
     * Class that holds one structure which can be added to Automaton
     */
    public static class Structure{
        private final String structureName;
        private final Class<? extends Automaton2Dim> compatibleAutomaton;
        private final CellState[][] structure;

        /**
         * Creates structure based on name, compatible Automaton and structure array
         * @param structureName name of structure
         * @param compatibleAutomaton {@link Automaton} that is compatible with structure
         * @param structure two dimensional array of {@link CellState}
         */
        public Structure(String structureName, Class<? extends Automaton2Dim> compatibleAutomaton,
                         CellState[][] structure){
            this.structureName = structureName;
            this.compatibleAutomaton = compatibleAutomaton;
            this.structure = structure;
        }

        /**
         * Converts {@link Structure} to String
         * @return name of the structure
         */
        @Override
        public String toString() {
            return structureName;
        }

        /**
         * Gets structure array
         * @return two dimensional array of {@link CellState}
         */
        public CellState[][] getStructure() {
            return structure;
        }
    }
}
