package crsAppPackage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EligibilityPanel extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private EligibilityManager manager;
    private JComboBox<String> filterBox;
    private JComboBox<String> sortBox;
    private JButton enrollButton;

    public EligibilityPanel() {
        manager = new EligibilityManager();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        //Top Control Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        
        topPanel.add(new JLabel("Filter:"));
        String[] filters = {"Show All", "Show Eligible", "Show Ineligible"};
        filterBox = new JComboBox<>(filters);
        filterBox.addActionListener(e -> refreshTable());
        topPanel.add(filterBox);

        topPanel.add(new JLabel("Sort By:"));
        String[] sortOptions = {"ID", "Name (A-Z)", "CGPA (High-Low)", "Fail Count (High-Low)"};
        sortBox = new JComboBox<>(sortOptions);
        sortBox.addActionListener(e -> handleSort());
        topPanel.add(sortBox);


        add(topPanel, BorderLayout.NORTH);

        //Table Setup
        String[] columnNames = {"Student ID", "Name", "CGPA", "Failed Courses", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        //Load first time
        refreshTable();
    }

    private void handleSort() {
        String selected = (String) sortBox.getSelectedItem();
        
        if(selected.contains("ID")) {
            manager.sortStudents(EligibilityManager.SortCriteria.ID, true);
        } 
        else if(selected.contains("Name")) {
            manager.sortStudents(EligibilityManager.SortCriteria.NAME, true);
        } 
        else if(selected.contains("CGPA")) {
            manager.sortStudents(EligibilityManager.SortCriteria.CGPA, false);
        } 
        else if(selected.contains("Fail")) {
            manager.sortStudents(EligibilityManager.SortCriteria.FAIL_COUNT, false);
        }
        
        refreshTable();
    }

    private void refreshTable() {
        while(tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        List<StudentAcademicProfile> allStudents = manager.getAllStudents();
        String filter = (String) filterBox.getSelectedItem();

        ArrayList<StudentAcademicProfile> studentsToShow = new ArrayList<>();

        for(int i = 0; i < allStudents.size(); i++) {
            StudentAcademicProfile s = allStudents.get(i);
            
            boolean shouldAdd = false;
            
            if(filter.equals("Show All")) {
                shouldAdd = true;
            } 
            else if(filter.equals("Show Eligible")) {
                if(s.checkEligibility() == true) {
                    shouldAdd = true;
                }
            } 
            else if(filter.equals("Show Ineligible")) {
                if(s.checkEligibility() == false) {
                    shouldAdd = true;
                }
            }
            
            if(shouldAdd) {
                studentsToShow.add(s);
            }
        }

        //Populate Table
        for(int i = 0; i < studentsToShow.size(); i++) {
            StudentAcademicProfile s = studentsToShow.get(i);
            
            //Recalculating status for the string
            String statusString = "";
            if(s.checkEligibility()) {
                statusString = "Eligible";
            } 
            else {
                statusString = "Not Eligible";
            }

            double val = s.calculateCGPA();
            String cgpaRaw = "" + val; 
            String cgpaPretty = cgpaRaw;
            
            if(cgpaRaw.length() > 4) {
                cgpaPretty = cgpaRaw.substring(0, 4);
            }

            //Create new array object
            Object[] rowData = new Object[5];
            rowData[0] = s.getStudentID();
            rowData[1] = s.getStudentName();
            rowData[2] = cgpaPretty;
            rowData[3] = s.getFailedCourseCount();
            rowData[4] = statusString;

            tableModel.addRow(rowData);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
