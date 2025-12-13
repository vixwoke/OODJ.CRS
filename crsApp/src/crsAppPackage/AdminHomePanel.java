package crsAppPackage;

import java.awt.event.*;
import javax.swing.*;

public class AdminHomePanel extends HomePanel {
    // Center Panels
    private final UserManagementCardPanel userManagementCardPanel = new UserManagementCardPanel();
    private final EligibilityPanel eligibilityPanel = new EligibilityPanel();
    private final ReportPanel academicPerformanceReportingPanel = new ReportPanel(this);
    // West Buttons
    private final JButton btnUserManagement = new JButton("Manage User");
    private final JButton btnCheckEligibility = new JButton("Check Eligibility");
    private final JButton btnAcademicPerformanceReporting = new JButton("Academic Performance & Reporting");
    private final JButton btnLogOut = new JButton("Log Out");

    public AdminHomePanel(IPanelNavigation navigator) {
        super(navigator);
        placeCenterPanels();
        placeWestCenterButtons();
        setActions();
    }

    protected void placeCenterPanels() {
        addCenterPanel(userManagementCardPanel, "USER_MANAGEMENT");
        addCenterPanel(eligibilityPanel, "CHECK_ELIGIBILITY");
        addCenterPanel(academicPerformanceReportingPanel, "ACADEMIC_REPORT");
    }

    protected void placeWestCenterButtons() {
        setWestCenterPanelLayout(4);
        addWestCenterPanel(btnUserManagement);
        addWestCenterPanel(btnCheckEligibility);
        addWestCenterPanel(btnAcademicPerformanceReporting);
        addWestCenterPanel(btnLogOut);
    }

    protected void setActions() {
        super.setActions();

        btnUserManagement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goTo("USER_MANAGEMENT");
            }
        });

        btnCheckEligibility.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goTo("CHECK_ELIGIBILITY");
            }
        });

        btnAcademicPerformanceReporting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goTo("ACADEMIC_REPORT");
            }
        });

        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }
}
