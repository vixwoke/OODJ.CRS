package crsAppPackage;

import java.awt.*;
import javax.swing.*;

public class MainCardPanel extends JPanel implements IPanelNavigation {
    private final CardLayout cardLayout = new CardLayout();
    private final LoginCardPanel loginCardPanel = new LoginCardPanel(this);
    private HomePanel homePanel;
    private User user = null;

    public MainCardPanel() {
        setLayout(cardLayout);
        add(loginCardPanel, LoginPanel.CONSTRAINTS);
    }

    public boolean isLoggedIn() {
        return (user != null);
    }

    public void goTo(String constraints) {};

    public void goNext() {
        user = loginCardPanel.getUser();

        if (user == null) {
            return;
        }

        if (user.isAdmin()) {
            homePanel = new AdminHomePanel(this);
            add(homePanel, HomePanel.CONSTRAINTS);
        }
        if (user.isAcademicOfficer()) {
            homePanel = new AcademicOfficerHomePanel(this);
            add(homePanel, HomePanel.CONSTRAINTS);
        }
        cardLayout.show(this, HomePanel.CONSTRAINTS);
    };

    public void goBack() {
        if (homePanel != null) {
            remove(homePanel);
        }
        user = null;
        cardLayout.show(this, LoginCardPanel.CONSTRAINTS);
    };
}
