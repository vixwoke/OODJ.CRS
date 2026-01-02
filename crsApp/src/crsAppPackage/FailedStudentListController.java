package crsAppPackage;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FailedStudentListController {

    public static List<StudentAcademicProfile> getFailedStudents() {

        List<StudentAcademicProfile> all =
            StudentAcademicRepository.loadAllProfiles();

        List<StudentAcademicProfile> failed = new ArrayList<>();
        for (StudentAcademicProfile s : all) {
            if (!s.checkEligibility()) {
                failed.add(s);
            }
        }

        return failed;
    }
    
    public static CourseRecoveryPlan buildPlan(StudentAcademicProfile profile) {
        CourseRecoveryPlan plan = new CourseRecoveryPlan(profile.getStudentID());

        for (AcademicRecord r : profile.getRecords()) {
            if (!r.isPass()) {
                RecoveryMilestone m =
                    new RecoveryMilestone(r.getCourseId(), r.getGradePoints());
                plan.addMilestone(m);
            }
        }
        return plan;
    }
    
}
