package crsAppPackage;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.regex.Pattern;

public class UserManagementPanel extends JPanel {
    public static final String CONSTRAINTS = "USER MANAGEMENT";
    // user
    private User selectedUser;
    // PanelNavigation
    private final IPanelNavigation navigator;
    // layout
    private final GroupLayout groupLayout = new GroupLayout(this);
    // Components
    private final JLabel lblTitle = new JLabel("Manage User");
    private final JLabel lblSearch = new JLabel("Search by");
    private final JComboBox<String> cmbSearch = new JComboBox<>(new String[] {"Username", "Name", "Email"});
    private final JTextField txtSearch = new JTextField(10);
    private final JButton btnSearch = new JButton("🔍");
    private final JLabel lblFilter = new JLabel("Filter by");
    private final JComboBox<String> cmbFilter = new JComboBox<>(new String[] {"None", "Role", "Gender", "Status"});
    private final JComboBox<String> cmbFilterValue = new JComboBox<>();
    private final JButton btnAddUser = new JButton("+ Add User");
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable table;
    private final JScrollPane tablePanel = new JScrollPane();

    public UserManagementPanel(IPanelNavigation navigator) {
        this.navigator = navigator;
        setLayout(groupLayout);
        placeComponents();
        designComponents();
        refreshTable();
        setActions();
        cmbFilterValue.setVisible(false);
    }

    public User getUser() {
        // One time user
        User user = selectedUser;
        selectedUser = null;
        return user;
    }

    public void refreshTable() {
        // Get the data
        ArrayList<ArrayList<String>> users;
        try {
            users =  User.getUsers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (users.size() <= 1) {
            return;
        }

        // convert array list to array
        users.getFirst().add("Action"); // Add additional column for buttons
        String[] columns = users.getFirst().toArray(new String[0]);
        Object[][] data = new String[users.size() - 1][users.getFirst().size()];
        for (int i = 1; i < users.size(); i ++) {
            users.get(i).add(null); // reserve for the buttons later
            for (int j = 0; j < users.getFirst().size(); j ++) {
                data[i-1][j] = users.get(i).get(j);
            }
        }

        model = new DefaultTableModel(data, columns) {
            // set the table to only view, but still leaving the last column editable
            public boolean isCellEditable(int row, int column) {
                return column == users.getFirst().size() - 1;
            }
        };

        sorter = new TableRowSorter<>(model);

        table = new JTable(model);

        table.setRowSorter(sorter);

        // No selection
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);

        // Display the buttons panel properly
        table.getColumnModel().getColumn(table.getColumnCount() - 1).setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel tableButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                JButton btnUpdate = new JButton("✏️");
                JButton btnDelete = new JButton("🗑");

                tableButtonsPanel.add(btnUpdate);
                tableButtonsPanel.add(btnDelete);

                return tableButtonsPanel;
            }
        });

        // Handle the button clicked event
        table.getColumnModel().getColumn(table.getColumnCount() - 1).setCellEditor(new TableCellEditor() {
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JPanel tableButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                JButton btnUpdate = new JButton("✏️");
                JButton btnDelete = new JButton("🗑");

                btnUpdate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username = table.getValueAt(row, 0).toString();

                        User user;
                        try {
                            user = new User(username);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(tablePanel, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                            refreshTable();
                            return;
                        }
                        selectedUser = user;
                        navigator.goTo(UpdateUserPanel.CONSTRAINTS);
                    }
                });

                btnDelete.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username = table.getValueAt(row, 0).toString();

                        int answer = JOptionPane.showConfirmDialog(tablePanel, "Do you want to delete " + username + " ?", "Delete User", JOptionPane.OK_CANCEL_OPTION);
                        if (answer != JOptionPane.OK_OPTION) {
                            return;
                        }

                        User user;
                        int numOfAffectedLine;
                        try {
                            user = new User(username);
                            numOfAffectedLine = user.deleteUser();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(tablePanel, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                            refreshTable();
                            return;
                        }
                        JOptionPane.showMessageDialog(tablePanel, "Successfully delete the user! Number of lines affected: " + numOfAffectedLine);
                        refreshTable();
                    }
                });

                tableButtonsPanel.add(btnUpdate);
                tableButtonsPanel.add(btnDelete);

                return tableButtonsPanel;
            }

            public Object getCellEditorValue() {
                return "";
            }

            public boolean isCellEditable(EventObject anEvent) {
                return true;
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }

            public boolean stopCellEditing() {
                return true;
            }

            public void cancelCellEditing() {
            }

            public void addCellEditorListener(CellEditorListener l) {
            }

            public void removeCellEditorListener(CellEditorListener l) {
            }
        });

        // Resize the table cell based on the last column
        Component comp = table.getCellRenderer(0, table.getColumnCount() - 1).getTableCellRendererComponent(table,
            table.getValueAt(0, table.getColumnCount() - 1), false, false, 0, table.getColumnCount() - 1);
        table.setRowHeight(comp.getPreferredSize().height);
        table.getColumnModel().getColumn(table.getColumnCount() - 1).setWidth(comp.getPreferredSize().width);

        tablePanel.setViewportView(table);

        // apply the filtering
        btnSearch.doClick();
    }

    private void setActions() {
        // btnAddUser
        btnAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                navigator.goTo(AddUserPanel.CONSTRAINTS);
            }
        });

        // btnSearch
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Filter the table
                ArrayList<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();

                if (!txtSearch.getText().isBlank()) {
                    String text = txtSearch.getText();
                    String filterBy = cmbSearch.getItemAt(cmbSearch.getSelectedIndex());

                    int column;
                    if (filterBy == null || text == null) {
                        return;
                    }
                    if (filterBy.equals("Username")) {
                        column = 0;
                    } else if (filterBy.equals("Name")) {
                        column = 5;
                    } else if (filterBy.equals("Email")) {
                        column = 3;
                    } else {
                        return;
                    }

                    filters.add(RowFilter.regexFilter("(?i).*" + Pattern.quote(text) + ".*", column));
                }
                if (cmbFilterValue.isVisible()) {
                    String text = cmbFilterValue.getItemAt(cmbFilterValue.getSelectedIndex());
                    String filterBy = cmbFilter.getItemAt(cmbFilter.getSelectedIndex());

                    int column;
                    if (filterBy == null || text == null) {
                        return;
                    }
                    if (filterBy.equals("Gender")) {
                        column = 6;
                    } else if (filterBy.equals("Status")) {
                        column = 4;
                    } else if (filterBy.equals("Role")) {
                        column = 2;
                    } else {
                        return;
                    }

                    filters.add(RowFilter.regexFilter("(?i)^" + Pattern.quote(text) + "$", column));
                }

                if (filters.isEmpty()) {
                    // Print all values (no filtering)
                    sorter.setRowFilter(null);
                } else {
                    // Connect with AND operator
                    sorter.setRowFilter(RowFilter.andFilter(filters));
                }
            }
        });

        // cmbFilter
        cmbFilter.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String filterBy = cmbFilter.getItemAt(cmbFilter.getSelectedIndex());
                cmbFilterValue.setVisible(false);
                cmbFilterValue.removeAllItems();
                if (null != filterBy) switch (filterBy) {
                    case "Role":
                        cmbFilterValue.addItem(User.ROLE.ADMIN);
                        cmbFilterValue.addItem(User.ROLE.ACADEMIC_OFFICER);
                        break;
                    case "Gender":
                        cmbFilterValue.addItem(User.GENDER.MALE);
                        cmbFilterValue.addItem(User.GENDER.FEMALE);
                        break;
                    case "Status":
                        cmbFilterValue.addItem(User.STATUS.ACTIVE);
                        cmbFilterValue.addItem(User.STATUS.INACTIVE);
                        break;
                    default:
                        break;
                }

                if (!"None".equals(filterBy)) {
                    cmbFilterValue.setVisible(true);
                }
            }
        });

        // txtSearch
        // When enter is pressed, click btnSearch
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER) {
                    btnSearch.doClick();
                }
            }
        });
    }

    private void designComponents() {
    }

    private void placeComponents() {
        // Enable auto gaps
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        // Group components to have the same height
        groupLayout.linkSize(SwingConstants.VERTICAL, lblSearch, cmbSearch, txtSearch, btnSearch, lblFilter, cmbFilter, cmbFilterValue, btnAddUser);

        // Horizontal Group
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblTitle)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup()
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(lblSearch)
                            .addComponent(cmbSearch))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(txtSearch)
                            .addComponent(btnSearch)))
                    .addContainerGap(0, 10000)
                    .addComponent(lblFilter)
                    .addComponent(cmbFilter)
                    .addComponent(cmbFilterValue)
                    .addContainerGap(0, 10000)
                    .addComponent(btnAddUser))
                .addComponent(tablePanel)
        );

        // Vertcal Group
        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addComponent(lblTitle)
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(lblSearch)
                    .addComponent(cmbSearch))
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(txtSearch)
                    .addComponent(btnSearch)
                    .addComponent(lblFilter)
                    .addComponent(cmbFilter)
                    .addComponent(cmbFilterValue)
                    .addComponent(btnAddUser))
                .addComponent(tablePanel)
        );
    }
}
