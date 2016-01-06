package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.gui.boards.Board;

import java.util.Observable;

/**
 * Created by Kamil on 03.01.2016.
 */
public class Ticker extends Observable{
    private int sleepTime = 1000;
    private Thread thread = null;

    public synchronized void start(AutomatonBox automatonBox){
        if(thread == null){
            thread = new Thread(new InifiteTicks(automatonBox));
            thread.start();
        }else {
            thread.interrupt();
            thread = null;
        }
    }

    public int getSleepTime(){
        return sleepTime;
    }

    public void setSleepTime(int sleepTime){
        this.sleepTime = sleepTime;
    }

    private class InifiteTicks implements Runnable {
        private final AutomatonBox automatonBox;

        private InifiteTicks(AutomatonBox automatonBox) {
            this.automatonBox = automatonBox;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    setChanged();
                    notifyObservers(automatonBox.getAutomaton().nextState());
                    Thread.sleep(sleepTime);
                }
            }catch (InterruptedException e){

            }
        }
    }
}
