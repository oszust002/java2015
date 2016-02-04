package pl.edu.agh.java2015.ftp.server.gui.action;

import pl.edu.agh.java2015.ftp.server.session.SessionsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 03.02.2016.
 */
public class ConnectAction extends AbstractAction{
    SessionsManager manager;


    public ConnectAction(SessionsManager manager){
        super("Start");
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(getValue(NAME));
        if(getValue(NAME).equals("Start")){
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    manager.start();
                    return null;
                }
            }.execute();
            putValue(NAME,"Stop");
            System.out.println("Started");
        }
        else {
            manager.disconnect();
            putValue(NAME,"Start");
            System.out.println("Stopped");
        }
    }
}
