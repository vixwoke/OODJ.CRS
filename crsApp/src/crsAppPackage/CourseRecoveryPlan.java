package crsAppPackage;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CourseRecoveryPlan {

    public TableModel loadFailedStudentsTable() {

        // Create TableModel (JTable Format)
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Student ID", "Course ID", "CGPA"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Read file
        ArrayList<ArrayList<String>> results =
            FileManager.readFile("Resources/Data/student_results.txt", 3, new HashMap<>());

        // Filter results
        for (ArrayList<String> row : results) {
            double cgpa = Double.parseDouble(row.get(2));

            if (cgpa < 2.0) {
                model.addRow(new Object[]{
                    row.get(0),   // Student ID
                    row.get(1),   // Course ID
                    String.format("%.2f", cgpa)
                });
            }
        }

        // return 
        return model;
    }

    public void deleteRecoveryPlan(List<String> studentIds) {
        for (String studentId : studentIds) {
            // contoh delete berdasarkan StudentID
            FileManager.deleteData(
                "Resources/Data/recovery_plan.txt",
                new java.util.HashMap<Integer, String>() {{
                    put(1, studentId); // kolom StudentID
                }}
            );
        }
    }
}
