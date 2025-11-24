package crsAppPackage;

import javax.swing.*;
import java.awt.*;

public class LoginCardPanel extends JPanel implements IPanelNavigation {
    public static final String CONSTRAINTS = "LOGIN";
    // PanelNavigation
    private final IPanelNavigation navigator;
    // layout
    private final CardLayout cardLayout = new CardLayout();
    // Panels
    private final LoginPanel loginPanel = new LoginPanel(this);
    private VerificationPanel verificationPanel;
    private ResetPasswordPanel resetPasswordPanel;

    public LoginCardPanel(IPanelNavigation navigator) {
        this.navigator = navigator;
        setLayout(cardLayout);
        add(loginPanel, LoginPanel.CONSTRAINTS);
    }

    public User getUser() {
        User user = loginPanel.getUser();
        loginPanel.reset();
        return user;
    }

    public void goTo(String constraints) {
        if (VerificationPanel.CONSTRAINTS.equals(constraints)) {
            User user = loginPanel.getUser();
            if (user == null) {
                return;
            }

            PasswordRecoverySession passwordRecoverySession;
            try {
                passwordRecoverySession = new PasswordRecoverySession(user);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            verificationPanel = new VerificationPanel(this, passwordRecoverySession);
            add(verificationPanel, VerificationPanel.CONSTRAINTS);
            cardLayout.show(this, VerificationPanel.CONSTRAINTS);

        } else if (ResetPasswordPanel.CONSTRAINTS.equals(constraints)) {
            User user = null;
            if (verificationPanel != null) {
                user = verificationPanel.getUser();  
                remove(verificationPanel); 
            }
            if (user == null) {
                return;
            }

            resetPasswordPanel = new ResetPasswordPanel(this, user);
            add(resetPasswordPanel, ResetPasswordPanel.CONSTRAINTS);
            cardLayout.show(this, ResetPasswordPanel.CONSTRAINTS);
        }
    }

    public void goNext() {
        navigator.goNext();
    }

    public void goBack() {
        if (verificationPanel != null) {
            remove(verificationPanel);
        }
        if (resetPasswordPanel != null) {
            remove(resetPasswordPanel);
        }

        cardLayout.show(this, LoginPanel.CONSTRAINTS);
    }
}
