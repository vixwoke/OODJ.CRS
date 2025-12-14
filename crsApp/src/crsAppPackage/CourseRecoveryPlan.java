package crsAppPackage;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CourseRecoveryPlan {

     public TableModel loadFailedStudentsTable() {

        // ===== TABLE HEADER =====
        String[] columns;

            columns = new String[]{"Student ID", "Student Name", "Major", "Course", "Grade Point", "Recovery ID", "Status"};

        // ===== TABLE MODEL =====
        DefaultTableModel model = new DefaultTableModel(columns, 0) {

//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//                if (batchMode && columnIndex == 0) {
//                    return Boolean.class;
//                }
//                return String.class;
//            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        // ===== READ FILES =====
        ArrayList<ArrayList<String>> student_results =
                FileManager.readFile("Resources/Data/student_results.txt", 3, new HashMap<>());

        ArrayList<ArrayList<String>> student_info =
                FileManager.readFile("Resources/Data/student_info.txt", 6, new HashMap<>());

        ArrayList<ArrayList<String>> course_information =
                FileManager.readFile("Resources/Data/course_information.txt", 6, new HashMap<>());

        ArrayList<ArrayList<String>> recovery_plan =
                FileManager.readFile("Resources/Data/recovery_plan.txt", 4, new HashMap<>());

        // ===== LOOKUP MAPS =====
        Map<String, String> studentFirstNameMap = new HashMap<>();
        Map<String, String> studentLastNameMap  = new HashMap<>();
        Map<String, String> studentMajorMap     = new HashMap<>();
        Map<String, String> courseNameMap       = new HashMap<>();
        Map<String, String> recoveryIdMap       = new HashMap<>();

        // 🔑 composite key: studentId|courseId -> status
        Map<String, String> recoveryStatusMap   = new HashMap<>();

        // ===== STUDENT INFO =====
        for (ArrayList<String> row : student_info) {
            studentFirstNameMap.put(row.get(0), row.get(1));
            studentLastNameMap.put(row.get(0), row.get(2));
            studentMajorMap.put(row.get(0), row.get(3));
        }

        // ===== COURSE INFO =====
        for (ArrayList<String> row : course_information) {
            courseNameMap.put(row.get(0), row.get(1));
        }

        // ===== RECOVERY PLAN (COMPOSITE KEY) =====
        for (ArrayList<String> row : recovery_plan) {
            String recoveryId   = row.get(0);
            String studentId    = row.get(1);
            String courseId     = row.get(2);
            String status       = row.get(3);

            String key = studentId + "|" + courseId;
            recoveryStatusMap.put(key, status);
            recoveryIdMap.put(key, recoveryId);
            
        }

        // ===== FILTER + JOIN =====
        for (ArrayList<String> row : student_results) {

            String studentId = row.get(0);
            String courseId  = row.get(1);
            double gradePoint = Double.parseDouble(row.get(2));

            if (gradePoint < 2.0) {

                String key = studentId + "|" + courseId;
                String recoveryStatus =
                        recoveryStatusMap.getOrDefault(key, "No Recovery");
                String recoveryId =
                        recoveryIdMap.getOrDefault(key,"0");

                model.addRow(new Object[]{
                    studentId,
                    studentFirstNameMap.get(studentId) + " " + studentLastNameMap.get(studentId),
                    studentMajorMap.get(studentId),
                    courseNameMap.get(courseId),
                    String.format("%.2f", gradePoint),
                    recoveryId,
                    recoveryStatus
                });
                
            }
        }

        return model;
     }

   
    public void editRecoveryPlan(String studentIds){
        
    }

    public boolean deleteRecoveryPlan(String recoveryId) {

        String path = "Resources/Data/recovery_plan.txt";

        Map<Integer, String> conditions = new HashMap<>();
        conditions.put(0, recoveryId); // kolom 0 = RecoveryID

        int deletedRows = FileManager.deleteData(path, conditions);

        return deletedRows > 0;
    }


}
