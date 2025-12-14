package crsAppPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author gilan
 */
public class CourseRecoveryPlanDetails {
    public Map<String, Object> refreshData(String recoveryId) {
        // ===== READ FILES ====
        System.out.println("recovery_milestone");
        ArrayList<ArrayList<String>> recovery_milestone =
                FileManager.readFile("Resources/Data/recovery_milestone.txt", 5, new HashMap<>());
        System.out.println("recovery_result");
        ArrayList<ArrayList<String>> recovery_result =
                FileManager.readFile("Resources/Data/recovery_result.txt", 2, new HashMap<>());
        System.out.println("recovery_plan");
        ArrayList<ArrayList<String>> recovery_plan =
                FileManager.readFile("Resources/Data/recovery_plan.txt", 4, new HashMap<>());
        System.out.println("student_information");
        ArrayList<ArrayList<String>> student_information =
                FileManager.readFile("Resources/Data/student_info.txt", 6, new HashMap<>());
        System.out.println("course_information");
        ArrayList<ArrayList<String>> course_information =
                FileManager.readFile("Resources/Data/course_information.txt", 6, new HashMap<>());


        // ===== LOOKUP MAPS =====
        Map<String, String> recoveryResultMap          = new HashMap<>();
        Map<String, String> recoveryPlanStatusMap      = new HashMap<>();
        Map<String, String> recoveryStudentIDMap       = new HashMap<>();
        Map<String, String> recoveryCourseIDMap        = new HashMap<>();
        Map<String, String> courseNameMap              = new HashMap<>();
        Map<String, String> studentFirstNameMap        = new HashMap<>();
        Map<String, String> studentLastNameMap         = new HashMap<>();
        Map<String, String> studentMajorMap            = new HashMap<>();
        
        // ===== RECOVERY MILESTONE INFO =====
        DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Week", "Task", "Status"},
            0
        );
        
        for (ArrayList<String> row : recovery_milestone) {
            String rowRecoveryId = row.get(1);
            // MATCH dengan recoveryId yang dipilih
            if (rowRecoveryId.equals(recoveryId)) {
                String week   = row.get(2);
                String task   = row.get(3);
                String status = row.get(4);
                tableModel.addRow(new Object[]{week, task, status});
            }
        }

        // ===== RECOVERY RESULT INFO =====
        for (ArrayList<String> row : recovery_result) {
            recoveryResultMap.put(row.get(0), row.get(1));
        }
        
        // ===== RECOVERY PLAN INFO =====
        for (ArrayList<String> row : recovery_plan) {
            recoveryStudentIDMap.put(row.get(0), row.get(1));
            recoveryCourseIDMap.put(row.get(0), row.get(2));
            recoveryPlanStatusMap.put(row.get(0), row.get(3));
        }
        
        // ===== STUDENT INFO =====
        for (ArrayList<String> row : student_information) {
            studentFirstNameMap.put(row.get(0), row.get(1));
            studentLastNameMap.put(row.get(0), row.get(2));
            studentMajorMap.put(row.get(0), row.get(3));
        }

        // ===== COURSE INFO =====
        for (ArrayList<String> row : course_information) {
            courseNameMap.put(row.get(0), row.get(1));
        }

        // ===== DEPLOY RECOVERY DATA (non table) =====\
        Map<String, Object> result = new HashMap<>();
        Map<String, String> details = new HashMap<>();

        
        String studentId    = recoveryStudentIDMap.get(recoveryId);
        String courseId     = recoveryCourseIDMap.get(recoveryId);
        
        String courseName = courseNameMap.get(courseId);
        String studentName = studentFirstNameMap.get(studentId) + " " + studentLastNameMap.get(studentId);
        String studentMajor = studentMajorMap.get(studentId);
        String recoveryStatus = recoveryPlanStatusMap.get(recoveryId);
        String recoveryMarks = recoveryResultMap.get(recoveryId);
            


        details.put("courseName", courseName);
        details.put("studentName", studentName);
        details.put("studentMajor", studentMajor);
        details.put("recoveryStatus", recoveryStatus);
        details.put("recoveryMarks", recoveryMarks);
        
        
        // ===== DEPLOY RECOVERY DATA (table) =====\
        // table already configured in the RECOVERY MILESTONE INFO 
        
        result.put("details", details);
        result.put("table", tableModel);

        return result;


    }
    
    public void saveAll(
        String recoveryId,
        String status,
        String marks,
        DefaultTableModel tableModel
) {
    saveRecoveryPlanStatus(recoveryId, status);
    saveRecoveryResult(recoveryId, marks);
    saveMilestones(recoveryId, tableModel);
}

    
public void saveRecoveryPlanStatus(String recoveryId, String newStatus) {

    String path = "Resources/Data/recovery_plan.txt";

    // 1️⃣ Ambil row lama
    Map<Integer, String> cond = new HashMap<>();
    cond.put(0, recoveryId);

    ArrayList<ArrayList<String>> rows =
        FileManager.readFile(path, 4, cond);

    if (rows.isEmpty()) return;

    ArrayList<String> oldRow = rows.get(0);

    // 2️⃣ Buat row BARU (LENGKAP)
    String[] newRow = new String[]{
        oldRow.get(0), // RecoveryID
        oldRow.get(1), // StudentID
        oldRow.get(2), // CourseID
        newStatus      // Status
    };

    // 3️⃣ Replace row lama
    FileManager.updateData(path, newRow, cond);
}

    

    public void saveRecoveryResult(String recoveryId, String marks) {
        if (marks == null || marks.trim().isEmpty()) {
            marks = "0";
        }
        String path = "Resources/Data/recovery_result.txt";

        Map<Integer, String> cond = new HashMap<>();
        cond.put(0, recoveryId);

        // 1️⃣ delete dulu (soft delete)
        FileManager.deleteData(path, cond);

        // 2️⃣ add row baru
        FileManager.addData(
            path,
            new String[]{recoveryId, marks}
        );
    }
    public void saveMilestones(
            String recoveryId,
            DefaultTableModel model
    ) {

        String path = "Resources/Data/recovery_milestone.txt";

        // 1️⃣ delete lama
        Map<Integer, String> cond = new HashMap<>();
        cond.put(1, recoveryId); // kolom RecoveryID

        FileManager.deleteData(path, cond);

        // 2️⃣ add dari JTable
        for (int i = 0; i < model.getRowCount(); i++) {

            String milestoneId = "MS" + System.currentTimeMillis() + i;

            FileManager.addData(
                path,
                new String[]{
                    milestoneId,
                    recoveryId,
                    model.getValueAt(i, 0).toString(), // Week
                    model.getValueAt(i, 1).toString(), // Task
                    model.getValueAt(i, 2).toString()  // Status
                }
            );
        }
    }




}


