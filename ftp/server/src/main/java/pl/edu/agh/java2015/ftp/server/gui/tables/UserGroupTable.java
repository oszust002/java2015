package pl.edu.agh.java2015.ftp.server.gui.tables;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.UserGroup;
import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
import pl.edu.agh.java2015.ftp.server.database.DBUserManager;
import pl.edu.agh.java2015.ftp.server.gui.NamedComponentPanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kamil on 03.02.2016.
 */
public class UserGroupTable {
    DBUserManager userManager;
    DBGroupManager groupManager;

    private JPanel panel;
    private UserGroupTableModel tableModel;
    private JTable table;
    private JButton delete;
    private JButton addUserGroup;
    private JPanel innerPanel;
    private NamedComponentPanel userIDTF;
    private NamedComponentPanel groupIDTF;

    public UserGroupTable(DBUserManager userManager, DBGroupManager groupManager){
        this.userManager = userManager;
        this.groupManager = groupManager;

        innerPanel = new JPanel(new GridLayout(1,4));
        delete = new JButton("Delete");
        addUserGroup = new JButton("Add user to group");
        userIDTF = new NamedComponentPanel("User ID", new JTextField(10));
        groupIDTF = new NamedComponentPanel("Group ID", new JTextField(10));
        innerPanel.add(userIDTF.getjPanel());
        innerPanel.add(groupIDTF.getjPanel());
        innerPanel.add(addUserGroup);
        innerPanel.add(delete);
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

    private class UserGroupTableModel extends AbstractTableModel {
        private List<String> columnNames;
        private List<Object[]> rowUserData;

        public UserGroupTableModel(List<UserGroup> userGroups){
            super();
            columnNames = new LinkedList<String>();
            columnNames.add("User ID");
            columnNames.add("Group ID");
            rowUserData = new LinkedList<>();
            for(UserGroup userGroup: userGroups){
                rowUserData.add(new Object[]{
                        userGroup.getUserID(),
                        userGroup.getGroupID()
                });
            }
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
    }
}
