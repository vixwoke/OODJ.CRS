package crsAppPackage;

import javax.swing.*;

public class AddUserPanel extends AddUpdateUserPanel {
    public static final String CONSTRAINTS = "ADD USER";

    public AddUserPanel(IPanelNavigation navigator) {
        super(navigator);
        setTitle("Add User");
    }

    protected boolean btnConfirmDoClick() {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to register a new user ( " + getUsername() + " )?", "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (answer != JOptionPane.OK_OPTION) {
            return false;
        }

        if (!checkConfirmPassword()) {
            JOptionPane.showMessageDialog(this, "Confirm password doesn't match with password", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        User user = new User(getUsername(), getPassword(), getRole(), getEmail(), isActive(), getUserName(), getGender());

        try {
            user.addUser();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        JOptionPane.showMessageDialog(this, "Successfully added");
        return true;
    }
}
