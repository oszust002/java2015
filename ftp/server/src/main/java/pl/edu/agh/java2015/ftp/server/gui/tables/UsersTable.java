package pl.edu.agh.java2015.ftp.server.gui.tables;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;
import pl.edu.agh.java2015.ftp.server.gui.NamedComponentPanel;
import pl.edu.agh.java2015.ftp.server.gui.TabbedPane;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kamil on 03.02.2016.
 */
public class UsersTable {
    DBUserManager userManager;
    DBGroupManager groupManager;

    private JPanel panel;
    private JButton delete;
    private JButton addUser;

    private JPanel innerPanel;
    private TabbedPane parent;
    private NamedComponentPanel usernameTF;
    private NamedComponentPanel passwordTF;
    private JTable table;
    private UsersTableModel tableModel;

    public UsersTable(DBUserManager userManager,  DBGroupManager groupManager, TabbedPane parent){
        this.userManager = userManager;
        this.groupManager = groupManager;

        this.parent = parent;
        innerPanel = new JPanel(new GridLayout(1,4));
        delete = new JButton("Delete");
        addUser = new JButton("Add user");
        usernameTF = new NamedComponentPanel("Username", new JTextField(10));
        passwordTF = new NamedComponentPanel("Password", new JTextField(10));
        innerPanel.add(usernameTF.getjPanel());
        innerPanel.add(passwordTF.getjPanel());
        innerPanel.add(addUser);
        innerPanel.add(delete);
        tableModel = new UsersTableModel(userManager.getUsers());
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        panel = new JPanel(new FlowLayout());
        innerPanel.setSize(new Dimension(panel.getWidth(),10));
        addUser.addActionListener(new AddUserListener());
        delete.addActionListener(new DeleteUserListener());
        panel.add(innerPanel);
        panel.add(new JScrollPane(table));
    }

    public JPanel getPanel() {
        return panel;
    }

    public void reload() {
        tableModel.reload();
    }

    private class UsersTableModel extends AbstractTableModel {
        private java.util.List<String> columnNames;
        private java.util.List<Object[]> rowUserData;

        public UsersTableModel(List<User> users){
            super();
            columnNames = new LinkedList<String>();
            columnNames.add("id");
            columnNames.add("Username");
            columnNames.add("Password");
            rowUserData = new LinkedList<>();
            addToModel(users);
        }

        @Override
        public int getRowCount() {
            return rowUserData.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return rowUserData.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames.get(column);
        }

        public void addRow(Object[] row){
            rowUserData.add(row);
        }

        public void reload() {
            rowUserData.clear();
            List<User> users = userManager.getUsers();
            addToModel(users);
            fireTableDataChanged();
        }

        private void addToModel(List<User> users){
            for(User user: users){
                rowUserData.add(new Object[]{
                        user.getId(),
                        user.getUsername(),
                        user.getPassword()
                });
            }
        }
    }

    private class AddUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((JTextField)usernameTF.getComponent()).getText();
            String password = ((JTextField)usernameTF.getComponent()).getText();
            if(username.isEmpty() || password.isEmpty())
                    return;
            int index = userManager.createUser(username,password,"random");
            if(index == -1){
                JOptionPane.showMessageDialog(null, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int groupIndex = groupManager.createGroup(username);
            groupManager.addUserToGroup(index, groupIndex);
            parent.reloadPanels();
        }
    }

    private class DeleteUserListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i: table.getSelectedColumns()){
                Integer index = (Integer) tableModel.getValueAt(i,0);
                userManager.deleteUser(index);
                groupManager.deleteUserFromUserGroups(index);
            }
            parent.reloadPanels();
        }
    }
}
