package crsAppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class AcademicOfficerHomePanel extends HomePanel {
    // Center Panels
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
                goTo("");
            }
        });

        btnAcademicPerformanceReporting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goTo("");
            }
        });

        btnCourseRecoveryPlan.addActionListener(e -> {
            new FrameCourseRecoveryPlan();
        });


        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }
}
