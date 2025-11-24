package crsAppPackage;

import javax.swing.*;
import java.awt.*;

public class UserManagementCardPanel extends JPanel implements IPanelNavigation {
    public static final String CONSTRAINTS = "USER MANAGEMENT";
    private final CardLayout cardLayout = new CardLayout();
    private final UserManagementPanel userManagementPanel = new UserManagementPanel(this);
    private final AddUserPanel addUserPanel = new AddUserPanel(this);
    private UpdateUserPanel updateUserPanel;

    public UserManagementCardPanel() {
        setLayout(cardLayout);

        add(userManagementPanel, UserManagementPanel.CONSTRAINTS);
        add(addUserPanel, AddUserPanel.CONSTRAINTS);
    }

    public void goTo(String constraints) {
        if (AddUserPanel.CONSTRAINTS.equals(constraints)) {
            addUserPanel.resetPanel();
            cardLayout.show(this, AddUserPanel.CONSTRAINTS);

        } else if (UpdateUserPanel.CONSTRAINTS.equals(constraints)) {
            User user = userManagementPanel.getUser();
            if (user == null) {
                return;
            }
            
            updateUserPanel = new UpdateUserPanel(this, user);
            add(updateUserPanel, constraints);
            cardLayout.show(this, constraints);
        }
    };
    
    public void goNext() {};

    public void goBack() {
        if (updateUserPanel != null) {
            remove(updateUserPanel);
            updateUserPanel = null;
        }

        userManagementPanel.refreshTable();
        cardLayout.show(this, UserManagementPanel.CONSTRAINTS);
    };
}
