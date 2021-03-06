package pl.edu.agh.java2015.ftp.server.gui.tables;

import javafx.scene.control.Tab;
import pl.edu.agh.java2015.ftp.server.Group;
import pl.edu.agh.java2015.ftp.server.database.DBGroupManager;
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
public class GroupsTable {
    private DBGroupManager groupManager;

    private TabbedPane parent;
    private JPanel panel;
    private GroupsTableModel groupsModel;
    private JTable table;
    private NamedComponentPanel groupNameTF;
    private JButton addGroup;
    private JButton delete;
    private JPanel innerPanel;

    public GroupsTable(DBGroupManager groupManager, TabbedPane parent){
        this.parent = parent;
        this.groupManager = groupManager;
        innerPanel = new JPanel(new GridLayout(1,3));
        addGroup = new JButton("Add group");
        delete = new JButton("Delete");
        groupNameTF = new NamedComponentPanel("Group name", new JTextField(10));
        innerPanel.add(groupNameTF.getjPanel());
        innerPanel.add(addGroup);
        innerPanel.add(delete);
        panel = new JPanel(new FlowLayout());
        innerPanel.setSize(new Dimension(panel.getWidth(),10));
        addGroup.addActionListener(new AddGroupListener());
        delete.addActionListener(new DeleteGroupListener());
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

    public void reload() {
        groupsModel.reload();
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
            addToModel(groups);
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
            List<Group> groups = groupManager.getGroups();
            addToModel(groups);
            fireTableDataChanged();
        }

        private void addToModel(List<Group> groups){
            for(Group group: groups){
                rowUserData.add(new Object[]{
                        group.getId(),
                        group.getGroupname()
                });
            }
        }
    }

    private class AddGroupListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String groupname = ((JTextField)groupNameTF.getComponent()).getText();
            if(groupname.isEmpty())
                return;
            int index = groupManager.createGroup(groupname);
            if(index == -1) {
                JOptionPane.showMessageDialog(null, "Group already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            parent.reloadPanels();
        }
    }

    private class DeleteGroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i: table.getSelectedRows()){
                Integer groupIndex = (Integer) groupsModel.getValueAt(i,0);
                groupManager.deleteGroup(groupIndex);
                groupManager.deleteGroupFromUserGroup(groupIndex);
            }
            parent.reloadPanels();
        }
    }
}
