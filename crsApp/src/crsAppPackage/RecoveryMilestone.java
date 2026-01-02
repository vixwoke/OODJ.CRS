package crsAppPackage;

public class RecoveryMilestone {

    private String milestoneId;
    private String recoveryId;
    private int weekNo;
    private String task;
    private String status;

    public RecoveryMilestone(
        String milestoneId,
        String recoveryId,
        int weekNo,
        String task,
        String status
    ) {
        this.milestoneId = milestoneId;
        this.recoveryId = recoveryId;
        this.weekNo = weekNo;
        this.task = task;
        this.status = status;
    }

    // ===== GETTERS =====
    public String getMilestoneId() {
        return milestoneId;
    }

    public String getRecoveryId() {
        return recoveryId;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public String getTask() {
        return task;
    }

    public String getStatus() {
        return status;
    }

    // ===== SETTERS (for edit mode) =====
    public void setWeekNo(int weekNo) {
        this.weekNo = weekNo;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
