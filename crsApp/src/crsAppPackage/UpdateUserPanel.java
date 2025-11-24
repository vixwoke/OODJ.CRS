package crsAppPackage;

import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UpdateUserPanel extends AddUpdateUserPanel {
    public static final String CONSTRAINTS = "UPDATE";
    private final User user;
    private final JCheckBox cbChangePassword = new JCheckBox("Change password?", false);

    public UpdateUserPanel(IPanelNavigation navigator, User user) {
        super(navigator);
        this.user = user;

        setTitle("Update User Information");

        setDefaultValue(user.getUsername(), "", user.isActive(), (user.isAdmin()) ? User.ROLE.ADMIN : User.ROLE.ACADEMIC_OFFICER,
            user.getName(), (user.isMale()) ? User.GENDER.MALE : User.GENDER.FEMALE, user.getEmail());
            
        setAdditionalLayout(new FlowLayout(FlowLayout.LEADING));
        addToAdditionalPanel(cbChangePassword);
        setTxtPasswordVisible(false);

        cbChangePassword.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setTxtPasswordVisible(cbChangePassword.isSelected());
            }
        });
    }

    protected boolean btnConfirmDoClick() {
        int answer = JOptionPane.showConfirmDialog(this, "Do you want to change " + user.getUsername() + "'s information?", "Update Information", JOptionPane.OK_CANCEL_OPTION);
        if (answer != JOptionPane.OK_OPTION) {
            return false;
        }

        String password;
        if (cbChangePassword.isSelected()) {
            if (!checkConfirmPassword()) {
                JOptionPane.showMessageDialog(this, "Confirm password doesn't match with password", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            password = getPassword();
        } else {
            password = null;
        }

        int numOfAffectedLine;
        try {
            numOfAffectedLine = user.updateInformation(getUsername(), password, getRole(), getEmail(), isActive(), getUserName(), getGender());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        JOptionPane.showMessageDialog(this, "Successfully update user information! Number of lines affected: " + numOfAffectedLine);
        return true;
    }
    
}
