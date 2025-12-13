package crsAppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportPanel extends JPanel {

    private IPanelNavigation navigator;
    private JTextField txtStudentId; // New input field
    private JButton btnGenerate;
    private JButton btnBack;

    
    private static final String GRADE_FILE = "student_grades.txt"; 
    private static final int TOTAL_COLUMNS = 6; // ID, Code, Title, Credit, Grade, Point
    private static final int STUDENT_ID_COL_INDEX = 0; 

    public ReportPanel(IPanelNavigation navigator) {
        this.navigator = navigator;
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        // 1. Input Field for Student ID
        JLabel lblId = new JLabel("Enter Student ID:");
        txtStudentId = new JTextField(15);
        
        // 2. Generate Button
        btnGenerate = new JButton("Generate Real Report");
        btnGenerate.setPreferredSize(new Dimension(200, 50));
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runReportGeneration();
            }
        });

        // 3. Back Button
        btnBack = new JButton("Back");
        btnBack.setPreferredSize(new Dimension(100, 50));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigator.goBack(); 
            }
        });

        this.add(lblId);
        this.add(txtStudentId);
        this.add(btnGenerate);
        this.add(btnBack);
    }

    private void runReportGeneration() {
        String targetID = txtStudentId.getText().trim();
        
        if (targetID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Student ID.");
            return;
        }

        AcademicReport reporter = new AcademicReport(); 
        
        try {
            // --- REAL DATA FETCHING START ---
            
            // 1. Set condition: Column 0 (ID) must match targetID
            Map<Integer, String> conditions = new HashMap<>();
            conditions.put(STUDENT_ID_COL_INDEX, targetID);

            // 2. Use the Team's FileManager to find the rows
            // Note: FileManager throws RuntimeException if file is missing
            ArrayList<ArrayList<String>> rawData = FileManager.readFile(GRADE_FILE, TOTAL_COLUMNS, conditions);
            
            if (rawData.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No records found for Student ID: " + targetID);
                return;
            }

            // 3. Convert ArrayList to String[][] for the Report Module
            String[][] processedData = new String[rawData.size()][5];
            for (int i = 0; i < rawData.size(); i++) {
                ArrayList<String> row = rawData.get(i);
                // We skip the ID column (index 0) and take the rest for the report
                // Assuming File: ID, Code, Title, Credit, Grade, Point
                processedData[i][0] = row.get(1); // Course Code
                processedData[i][1] = row.get(2); // Course Title
                processedData[i][2] = row.get(3); // Credit
                processedData[i][3] = row.get(4); // Grade
                processedData[i][4] = row.get(5); // Point
            }
            
            // --- REAL DATA FETCHING END ---

            // 4. Generate the File
            reporter.generateReport("Student " + targetID, targetID, "Bachelor of IT", processedData);
            
            JOptionPane.showMessageDialog(this, 
                "Success! Report saved as: AcademicReport_" + targetID + ".txt", 
                "Report Generated", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error writing file: " + ex.getMessage());
        } catch (RuntimeException ex) {
            // Catches FileManager errors (like missing file)
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

}
