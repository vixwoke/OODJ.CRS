
package crsAppPackage;

import java.util.ArrayList;

public class CourseRecoveryPlanController {
    public String calculateCRPStatus(String recoveryId) {

    ArrayList<ArrayList<String>> milestones = FileManager.readFile(
        "Resources/Data/recovery_milestone.txt",5
    );

    boolean hasMilestone = false;
    boolean allPassed = true;

    for (ArrayList<String> row : milestones) {
        String rowRecoveryId = row.get(1); // RecoveryID
        String status = row.get(4);        // Status

        if (!rowRecoveryId.equals(recoveryId)) continue;

        hasMilestone = true;

        if (!status.equalsIgnoreCase("Pass")) {
            allPassed = false;
        }
    }

    if (!hasMilestone) {
        return "No Milestone";
    }

    if (allPassed) {
        return "Completed";
    }

    return "Pending";
}

}
