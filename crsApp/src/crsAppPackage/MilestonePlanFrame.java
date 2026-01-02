package crsAppPackage;
import javax.swing.*;
import java.awt.Dialog;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.util.ArrayList;

public class MilestonePlanFrame extends javax.swing.JFrame
    implements CRPView {
    
    private boolean isEditMode = false;
    private DefaultTableModel tableModel;
    private StudentAcademicProfile student;
    private String recoveryId;
    

    public MilestonePlanFrame(StudentAcademicProfile student, String recoveryId) {
        this.student = student;
        this.recoveryId = recoveryId;
        initComponents(); 

        setDefaultTableFormat();
        setupStatusColumnEditor();
        setDefaultEditMode();
        loadTableData();
        loadStudentInfo();
        autoResizeAllColumns(tblMilestonePlanList);
    }

    
    @Override
    public void setDefaultTableFormat() {

        tableModel = new DefaultTableModel(
            new Object[]{"Week", "Task", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditMode;
            }
        };

        tblMilestonePlanList.setModel(tableModel);
        tblMilestonePlanList.setRowSelectionAllowed(true);
        tblMilestonePlanList.setColumnSelectionAllowed(false);
        tblMilestonePlanList.setCellSelectionEnabled(false);
        tblMilestonePlanList.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
    }



    @Override
    public void loadTableData() {

        tableModel.setRowCount(0);

        ArrayList<ArrayList<String>> rows =
            FileManager.readFile("Resources/Data/recovery_milestone.txt", 5);

        for (int i = 1; i < rows.size(); i++) {

            ArrayList<String> row = rows.get(i);

            String rowRecoveryId = row.get(1);

            if (!rowRecoveryId.equals(recoveryId)) {
                continue;
            }

            tableModel.addRow(new Object[]{
                row.get(2), // Week
                row.get(3), // Task
                row.get(4)  // Status
            });
        }
    }

    @Override
    public void saveData() {

        FileManager.deleteData(
            "Resources/Data/recovery_milestone.txt",
            java.util.Map.of(1, recoveryId)
        );

        for (int row = 0; row < tableModel.getRowCount(); row++) {

            String week = tableModel.getValueAt(row, 0).toString().trim();
            String task = tableModel.getValueAt(row, 1).toString().trim();
            String status = tableModel.getValueAt(row, 2).toString().trim();

            if (week.isBlank() || task.isBlank()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Week and Task cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String milestoneId = "MS" + recoveryId + "W" + week;

            FileManager.addData(
                "Resources/Data/recovery_milestone.txt",
                new String[]{
                    milestoneId,
                    recoveryId,
                    week,
                    task,
                    status
                }
            );
        }
    }


    @Override
    public void cancelChanges() {
        loadTableData();
    }

    
    private void setDefaultEditMode() {
        isEditMode = false;
        btnAdd.setEnabled(false);
        btnRemove.setEnabled(false);
        lblTip.setVisible(false);
    }


    private void loadStudentInfo() {
        lblStudentId.setText(student.getStudentID());
        lblStudentName.setText(student.getStudentName());
        lblStudentMajor.setText(student.getStudentMajor());
        lblCourseName.setText("ah");
    }

    private void autoResizeAllColumns(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            autoResizeColumn(table, col);
        }
    }

    private void autoResizeColumn(JTable table, int column) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);

        int preferredWidth = tableColumn.getMinWidth();
        int maxWidth = tableColumn.getMaxWidth();

        // Header width
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        Component headerComp = headerRenderer.getTableCellRendererComponent(
                table, tableColumn.getHeaderValue(), false, false, 0, column);

        preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

        // Cell width
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
            Component cellComp = table.prepareRenderer(cellRenderer, row, column);
            preferredWidth = Math.max(preferredWidth, cellComp.getPreferredSize().width);
        }

        tableColumn.setPreferredWidth(Math.min(preferredWidth + 10, maxWidth));
    }
    private void updateEditModeUI() {
        btnUnlockEdit.setText(isEditMode ? "Lock Edit" : "Unlock Edit");
        btnAdd.setEnabled(isEditMode);
        btnRemove.setEnabled(isEditMode);
        lblTip.setVisible(isEditMode);
    }
    private void setupStatusColumnEditor() {

        JComboBox<String> combo = new JComboBox<>(
            new String[]{"Pending", "Pass", "Fail"}
        );

        TableColumn statusColumn = tblMilestonePlanList.getColumnModel().getColumn(2);
        statusColumn.setCellEditor(new DefaultCellEditor(combo));
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCourseRecoveryPlanList = new javax.swing.JPanel();
        scrollpanelCourseRecoveryPlanList = new javax.swing.JScrollPane();
        tblMilestonePlanList = new javax.swing.JTable();
        lblTitle = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        panelStudentDetails = new javax.swing.JPanel();
        colonMajor = new javax.swing.JLabel();
        colonCGPA = new javax.swing.JLabel();
        lblStudentId = new javax.swing.JLabel();
        lblStudentName = new javax.swing.JLabel();
        lblStudentMajor = new javax.swing.JLabel();
        lblCourseName = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblMajor = new javax.swing.JLabel();
        lblCGPA = new javax.swing.JLabel();
        colonId = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        colonName = new javax.swing.JLabel();
        separator1 = new javax.swing.JSeparator();
        separator2 = new javax.swing.JSeparator();
        btnUnlockEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        lblTip = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblMilestonePlanList.setAutoCreateRowSorter(true);
        tblMilestonePlanList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Week", "Task", "Status", "Grade Entry"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMilestonePlanList.setColumnSelectionAllowed(true);
        tblMilestonePlanList.setName("list_student_failed"); // NOI18N
        tblMilestonePlanList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tblMilestonePlanList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollpanelCourseRecoveryPlanList.setViewportView(tblMilestonePlanList);
        tblMilestonePlanList.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout panelCourseRecoveryPlanListLayout = new javax.swing.GroupLayout(panelCourseRecoveryPlanList);
        panelCourseRecoveryPlanList.setLayout(panelCourseRecoveryPlanListLayout);
        panelCourseRecoveryPlanListLayout.setHorizontalGroup(
            panelCourseRecoveryPlanListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpanelCourseRecoveryPlanList, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        );
        panelCourseRecoveryPlanListLayout.setVerticalGroup(
            panelCourseRecoveryPlanListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCourseRecoveryPlanListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scrollpanelCourseRecoveryPlanList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Milestone Plan");

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        colonMajor.setText(":");
        colonMajor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        colonCGPA.setText(":");
        colonCGPA.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblStudentId.setText("studentId");
        lblStudentId.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblStudentName.setText("studentName");
        lblStudentName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblStudentMajor.setText("studentMajor");
        lblStudentMajor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblCourseName.setText("courseName");
        lblCourseName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblName.setText("Name");
        lblName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblMajor.setText("Major");
        lblMajor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblCGPA.setText("Course");
        lblCGPA.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        colonId.setText(":");
        colonId.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblId.setText("ID");
        lblId.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        colonName.setText(":");
        colonName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout panelStudentDetailsLayout = new javax.swing.GroupLayout(panelStudentDetails);
        panelStudentDetails.setLayout(panelStudentDetailsLayout);
        panelStudentDetailsLayout.setHorizontalGroup(
            panelStudentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStudentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStudentDetailsLayout.createSequentialGroup()
                        .addComponent(lblMajor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(colonMajor))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStudentDetailsLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(colonName))
                    .addGroup(panelStudentDetailsLayout.createSequentialGroup()
                        .addComponent(lblId)
                        .addGap(44, 44, 44)
                        .addComponent(colonId)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelStudentDetailsLayout.createSequentialGroup()
                        .addComponent(lblStudentId)
                        .addGap(52, 52, 52)
                        .addComponent(lblCGPA)
                        .addGap(18, 18, 18)
                        .addComponent(colonCGPA)
                        .addGap(9, 9, 9)
                        .addComponent(lblCourseName)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblStudentMajor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelStudentDetailsLayout.setVerticalGroup(
            panelStudentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentDetailsLayout.createSequentialGroup()
                .addGroup(panelStudentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(colonId)
                    .addComponent(lblCGPA)
                    .addComponent(lblStudentId)
                    .addComponent(colonCGPA)
                    .addComponent(lblCourseName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(colonName)
                    .addComponent(lblStudentName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMajor)
                    .addComponent(colonMajor)
                    .addComponent(lblStudentMajor))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        btnUnlockEdit.setText("Unlock Edit");
        btnUnlockEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnlockEditActionPerformed(evt);
            }
        });

        btnAdd.setText("+ Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setText("- Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        lblTip.setText("Tip: You can update the information directly on the table.");
        lblTip.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUnlockEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTip)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(separator1)
                            .addComponent(panelCourseRecoveryPlanList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClose))
                            .addComponent(panelStudentDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(separator2))
                        .addGap(23, 23, 23))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(btnClose))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUnlockEdit)
                    .addComponent(btnAdd)
                    .addComponent(btnRemove)
                    .addComponent(lblTip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelCourseRecoveryPlanList, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        if (isEditMode) {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "You have unsaved changes. Do you want to save before closing?",
                "Unsaved Changes",
                JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            }

            if (choice == JOptionPane.YES_OPTION) {
                saveData();

            }
        }
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnUnlockEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnlockEditActionPerformed
        if (!isEditMode) {
            isEditMode = true;
            updateEditModeUI();
            return;
        } 

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Do you want to save changes?",
            "Confirm",
            JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (choice == JOptionPane.CANCEL_OPTION) {
            return;
        }

        if (choice == JOptionPane.YES_OPTION) {
            saveData();
        } else {
            cancelChanges();
        }

        isEditMode = false;
        updateEditModeUI();
    }//GEN-LAST:event_btnUnlockEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!isEditMode) return;

        tableModel.addRow(new Object[]{
            "",
            "",
            "Pending"
        });

        int row = tableModel.getRowCount() - 1;
        tblMilestonePlanList.setRowSelectionInterval(row, row);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if (!isEditMode) return;

        int row = tblMilestonePlanList.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Select a milestone to remove",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Remove selected milestone?",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MilestonePlanFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnUnlockEdit;
    private javax.swing.JLabel colonCGPA;
    private javax.swing.JLabel colonId;
    private javax.swing.JLabel colonMajor;
    private javax.swing.JLabel colonName;
    private javax.swing.JLabel lblCGPA;
    private javax.swing.JLabel lblCourseName;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblMajor;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStudentId;
    private javax.swing.JLabel lblStudentMajor;
    private javax.swing.JLabel lblStudentName;
    private javax.swing.JLabel lblTip;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelCourseRecoveryPlanList;
    private javax.swing.JPanel panelStudentDetails;
    private javax.swing.JScrollPane scrollpanelCourseRecoveryPlanList;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;
    private javax.swing.JTable tblMilestonePlanList;
    // End of variables declaration//GEN-END:variables
 
}
