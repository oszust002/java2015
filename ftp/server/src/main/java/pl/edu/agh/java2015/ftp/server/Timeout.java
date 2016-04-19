package pl.edu.agh.java2015.ftp.server;

import pl.edu.agh.java2015.ftp.server.session.Session;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kamil on 14.01.2016.
 */
public class Timeout {
    private final int time;
    private final Session sessionToTimeout;
    private TimerTask timerTask = null;
    private final Timer timer = new Timer("Connection timeout timer");

    public Timeout(Session sessionToTimeout, int seconds) {
        this.sessionToTimeout = sessionToTimeout;
        this.time = seconds*1000;
        reset();
    }

    public void reset(){
        if(timerTask != null)
            timerTask.cancel();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Connection timeout");
                sessionToTimeout.disconnect();
            }
        };
        timer.schedule(timerTask,time);
    }
}
