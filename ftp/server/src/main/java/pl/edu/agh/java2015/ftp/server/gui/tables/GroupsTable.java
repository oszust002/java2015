package pl.edu.agh.java2015.ftp.server.gui.tables;

import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
import pl.edu.agh.java2015.ftp.server.gui.NamedComponentPanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kamil on 03.02.2016.
 */
public class GroupsTable {
    private DBGroupManager groupManager;

    private JPanel panel;
    private GroupsTableModel groupsModel;
    private JTable table;
    private NamedComponentPanel groupNameTF;
    private JButton add;
    private JButton delete;
    private JPanel innerPanel;

    public GroupsTable(DBGroupManager groupManager){
        this.groupManager = groupManager;
        innerPanel = new JPanel(new GridLayout(1,3));
        add = new JButton("Add group");
        delete = new JButton("Delete");
        groupNameTF = new NamedComponentPanel("Group name", new JTextField(10));
        innerPanel.add(groupNameTF.getjPanel());
        innerPanel.add(add);
        innerPanel.add(delete);
        panel = new JPanel(new FlowLayout());
        innerPanel.setSize(new Dimension(panel.getWidth(),10));
        List<Group> groups = groupManager.getGroups();
        groupsModel = new GroupsTableModel(groups);
        table = new JTable(groupsModel);
        table.setAutoCreateRowSorter(true);
        panel.add(innerPanel);
        panel.add(new JScrollPane(table));

    }

    public JPanel getPanel() {
        return panel;
    }

    private class GroupsTableModel extends AbstractTableModel{
        private List<String> columnNames;
        private List<Object[]> rowUserData;

        public GroupsTableModel(List<Group> groups){
            super();
            columnNames = new LinkedList<String>();
            columnNames.add("GroupID");
            columnNames.add("Group Name");
            rowUserData = new LinkedList<>();
            for(Group group: groups){
                rowUserData.add(new Object[]{
                        group.getId(),
                        group.getGroupname()
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
