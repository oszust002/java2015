package pl.edu.agh.java2015.ftp.server.gui;

import pl.edu.agh.java2015.ftp.server.database.DBConnectionsManager;
import pl.edu.agh.java2015.ftp.server.database.ServerConfig;
import pl.edu.agh.java2015.ftp.server.gui.action.ConnectAction;
import pl.edu.agh.java2015.ftp.server.session.SessionsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Kamil on 03.02.2016.
 */
public class DatabaseOptions extends JPanel{
    private DBConnectionsManager connectionsManager;
    private JFrame parent;
    private NamedComponentPanel urlTextField;
    private NamedComponentPanel passwordTextField;
    private NamedComponentPanel loginTextField;
    private NamedComponentPanel saveButton;
    private NamedComponentPanel connectButton;
    private JPanel panel;

    public JPanel getPanel() {
        return panel;
    }

    public DatabaseOptions(JFrame frame, SessionsManager manager, DBConnectionsManager connectionsManager){
        this.connectionsManager = connectionsManager;

        ServerConfig config = ServerConfig.getInstance();
        this.parent = frame;
        urlTextField = new NamedComponentPanel("URL", new JTextField(config.getUrl()));
        loginTextField = new NamedComponentPanel("Login", new JTextField(config.getLogin()));
        passwordTextField = new NamedComponentPanel("Password", new JPasswordField((config.getPassword())));
        saveButton = new NamedComponentPanel("Save to Database", new JButton("Save"));
        connectButton = new NamedComponentPanel("Connect/Disconnect",
                new JButton(new ConnectAction(manager)));
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,3));
        panel.setSize(new Dimension(frame.getWidth(),200));
        panel.add(urlTextField.getjPanel());
        panel.add(loginTextField.getjPanel());
        ((JButton)saveButton.getComponent()).addActionListener(new ChangeDBListener());
        panel.add(passwordTextField.getjPanel());
        panel.add(saveButton.getjPanel());
        panel.add(connectButton.getjPanel());
    }

    private class ChangeDBListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String url = ((JTextField)urlTextField.getComponent()).getText();
            String login = ((JTextField)loginTextField.getComponent()).getText();
            String password = ((JTextField)passwordTextField.getComponent()).getText();
            if(url.isEmpty() || login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "One of the fields in database is empty", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!connectionsManager.testConnection(url, login, password)) {
                JOptionPane.showMessageDialog(null, "Database data is not valid", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ServerConfig config = ServerConfig.getInstance();
            config.setLogin(login);
            config.setUrl(url);
            config.setPassword(password);
            try {
                config.saveProperties();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
