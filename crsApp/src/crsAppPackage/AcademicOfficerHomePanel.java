package crsAppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class AcademicOfficerHomePanel extends HomePanel {
    // Center Panels
    private final EligibilityPanel eligibilityPanel = new EligibilityPanel();
    // West Buttons
    private final JButton btnCheckEligibility = new JButton("Check Eligibility");
    private final JButton btnAcademicPerformanceReporting = new JButton("Academic Performance & Reporting");
    private final JButton btnCourseRecoveryPlan = new JButton("Course Recovery Plan");
    private final JButton btnLogOut = new JButton("Log Out");

    public AcademicOfficerHomePanel(IPanelNavigation navigator) {
        super(navigator);
        placeCenterPanels();
        placeWestCenterButtons();
        setActions();
    }

    protected void placeCenterPanels() {
        addCenterPanel(eligibilityPanel, "CHECK_ELIGIBILITY");
    }

    protected void placeWestCenterButtons() {
        setWestCenterPanelLayout(4);
        addWestCenterPanel(btnCheckEligibility);
        addWestCenterPanel(btnAcademicPerformanceReporting);
        addWestCenterPanel(btnCourseRecoveryPlan);
        addWestCenterPanel(btnLogOut);
    }

    protected void setActions() {
        super.setActions();

        btnCheckEligibility.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goTo("CHECK_ELIGIBILITY");
            }
        });

        btnAcademicPerformanceReporting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goTo("");
            }
        });

        // TEMPORARY FORCE OPEN COURSE RECOVERY PLAN
        // PLEASE CHANGE IT LATER
        btnCourseRecoveryPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameCourseRecoveryPlan frame = new FrameCourseRecoveryPlan();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null); // biar muncul di tengah
            }
        });



        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }
}
