package pl.edu.agh.java2015.ftp.server.gui.tables;

import javafx.scene.control.Tab;
import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.User;
import pl.edu.agh.java2015.ftp.server.UserGroup;
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
public class UserGroupTable {
    private DBUserManager userManager;
    private DBGroupManager groupManager;
    private TabbedPane parent;

    private JPanel panel;
    private UserGroupTableModel tableModel;
    private JTable table;
    private JButton delete;
    private JButton addUserGroup;
    private JPanel innerPanel;
    private NamedComponentPanel userIDTF;
    private NamedComponentPanel groupIDTF;

    public UserGroupTable(DBUserManager userManager, DBGroupManager groupManager,TabbedPane parent){
        this.userManager = userManager;
        this.groupManager = groupManager;
        this.parent = parent;

        innerPanel = new JPanel(new GridLayout(1,4));
        delete = new JButton("Delete");
        addUserGroup = new JButton("Add user to group");
        userIDTF = new NamedComponentPanel("User ID", new JTextField(10));
        groupIDTF = new NamedComponentPanel("Group ID", new JTextField(10));
        innerPanel.add(userIDTF.getjPanel());
        innerPanel.add(groupIDTF.getjPanel());
        innerPanel.add(addUserGroup);
        innerPanel.add(delete);
        addUserGroup.addActionListener(new AddUserGroupListener());
        delete.addActionListener(new DeleteUserGroupListener());
        tableModel = new UserGroupTableModel(groupManager.getUserGroup());
        table = new JTable(tableModel);
        panel = new JPanel(new FlowLayout());
        innerPanel.setSize(new Dimension(panel.getWidth(),10));
        panel.add(innerPanel);
        panel.add(new JScrollPane(table));
    }

    public JPanel getPanel(){
        return panel;
    }

    public void reload() {
        tableModel.reload();
    }

    private class UserGroupTableModel extends AbstractTableModel {
        private List<String> columnNames;
        private List<Object[]> rowUserData;

        public UserGroupTableModel(List<UserGroup> userGroups){
            super();
            columnNames = new LinkedList<String>();
            columnNames.add("User ID");
            columnNames.add("Group ID");
            rowUserData = new LinkedList<>();
            addToModel(userGroups);
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


        public void reload() {
            rowUserData.clear();
            List<UserGroup> userGroups = groupManager.getUserGroup();
            addToModel(userGroups);
            fireTableDataChanged();
        }

        private void addToModel(List<UserGroup> userGroups){
            for(UserGroup userGroup: userGroups){
                rowUserData.add(new Object[]{
                        userGroup.getUserID(),
                        userGroup.getGroupID()
                });
            }
        }
    }

    private class AddUserGroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Integer userID = Integer.parseInt(((JTextField) userIDTF.getComponent()).getText());
                Integer groupID = Integer.parseInt(((JTextField) groupIDTF.getComponent()).getText());
                User user = userManager.findUserById(userID);
                Group group = groupManager.findGroupById(groupID);
                if(user == null){
                    JOptionPane.showMessageDialog(null,"User not exist","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(group==null){
                    JOptionPane.showMessageDialog(null,"Group not exist","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                groupManager.addUserToGroup(userID,groupID);
                parent.reloadPanels();
            }catch (NumberFormatException ignore){

            }
        }
    }

    private class DeleteUserGroupListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                for (int i: table.getSelectedRows()) {
                    Integer uID = (Integer) tableModel.getValueAt(i, 0);
                    Integer gID = (Integer) tableModel.getValueAt(i, 1);
                    groupManager.deleteUserGroup(uID, gID);
                }
                parent.reloadPanels();
            }catch (NumberFormatException ignore){

            }
        }
    }
}
