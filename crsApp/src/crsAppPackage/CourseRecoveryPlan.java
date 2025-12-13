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
            new String[]{"ID", "Name", "Major", "Course","CGPA"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Read files
        ArrayList<ArrayList<String>> student_results =
            FileManager.readFile("Resources/Data/student_results.txt", 3, new HashMap<>());

        ArrayList<ArrayList<String>> student_info =
            FileManager.readFile("Resources/Data/student_info.txt", 6, new HashMap<>());

        ArrayList<ArrayList<String>> course_information =
            FileManager.readFile("Resources/Data/course_information.txt", 6, new HashMap<>());

        // Build lookup maps
        Map<String, String> studentNameMap = new HashMap<>();
        for (ArrayList<String> row : student_info) {
            studentNameMap.put(row.get(0), row.get(1));
        }

        Map<String, String> courseNameMap = new HashMap<>();
        for (ArrayList<String> row : course_information) {
            courseNameMap.put(row.get(0), row.get(1));
        }

        // Filter + join
        for (ArrayList<String> row : student_results) {
            String studentId = row.get(0);
            String courseId = row.get(1);
            double gradePoint = Double.parseDouble(row.get(2));

            if (gradePoint < 2.0) {
                model.addRow(new Object[]{
                    studentId,
                    studentNameMap.get(studentId),
                    courseId,
                    courseNameMap.get(courseId),
                    String.format("%.2f", gradePoint)
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
