package crsAppPackage;

import java.util.ArrayList;
import java.util.List;

public class CourseRecoveryPlan {
    private String studentId;
    private String status; // Draft / Approved / Completed
    private List<RecoveryMilestone> milestones;

    public CourseRecoveryPlan(String studentId) {
        this.studentId = studentId;
        this.status = "Draft";
        this.milestones = new ArrayList<>();
    }

    public void addMilestone(RecoveryMilestone m) {
        milestones.add(m);
    }

    public List<RecoveryMilestone> getMilestones() {
        return milestones;
    }

    public String getStudentId() { return studentId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
