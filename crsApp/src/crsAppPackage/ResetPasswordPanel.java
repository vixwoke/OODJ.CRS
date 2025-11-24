package crsAppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResetPasswordPanel extends JPanel {
    public static final String CONSTRAINTS = "RESET PASSWORD";
    private final User user;
    private final IPanelNavigation navigator;
    // UI
    private final GroupLayout groupLayout = new GroupLayout(this);
    private final JLabel lblTitle = new JLabel("Reset Password");
    private final JLabel lblUsername = new JLabel();
    private final JLabel lblNewPassword = new JLabel("New Password:");
    private final JPasswordField txtNewPassword = new JPasswordField(15);
    private final JButton btnShowNewPassword = new JButton("👁️");
    private final JLabel lblConfirmPassword = new JLabel("Confirm Password:");
    private final JPasswordField txtConfirmPassword = new JPasswordField(15);
    private final JButton btnShowConfirmPassword = new JButton("👁️");
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnConfirm = new JButton("Confirm");

    public ResetPasswordPanel(IPanelNavigation navigator, User user) {
        this.user = user;
        this.navigator = navigator;

        lblUsername.setText("Username: " + user.getUsername());

        setLayout(groupLayout);
        placeComponents();
        setActions();
    }

    private void setActions() {
        JPanel currentPanel = this;

        // btnCancel
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                navigator.goBack();
            }
        });

        // btnConfirm
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(currentPanel, "Are you sure you want to change password for " + user.getUsername() + " ?", "Reset Password", JOptionPane.OK_CANCEL_OPTION);
                if (answer != JOptionPane.OK_OPTION) {
                    return;
                }

                String password = new String(txtNewPassword.getPassword());

                if (!password.equals(new String(txtConfirmPassword.getPassword()))) {
                    JOptionPane.showMessageDialog(currentPanel, "New password doesn't match with confirm password", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    user.updateInformation(null, password, null, null, user.isActive(), null, null);
                    JOptionPane.showMessageDialog(currentPanel, "Successfully update the password!");
                    navigator.goBack();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(currentPanel, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        // btnShowPassword
        btnShowNewPassword.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Left click
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // show the password
                    txtNewPassword.setEchoChar((char) 0);
                }
            }

            public void mouseReleased(MouseEvent e) {
                // hide the password
                txtNewPassword.setEchoChar(new JPasswordField().getEchoChar());
                // Set focus to txtPassword
                txtNewPassword.requestFocusInWindow();
            }
        });

        btnShowConfirmPassword.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Left click
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // show the password
                    txtConfirmPassword.setEchoChar((char) 0);
                }
            }

            public void mouseReleased(MouseEvent e) {
                // hide the password
                txtConfirmPassword.setEchoChar(new JPasswordField().getEchoChar());
                // Set focus to txtPassword
                txtConfirmPassword.requestFocusInWindow();
            }
        });
    }

    private void placeComponents() {
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblTitle)
                .addComponent(lblUsername)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblNewPassword)
                        .addComponent(lblConfirmPassword))
                    .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txtNewPassword)
                        .addComponent(txtConfirmPassword))
                    .addGroup(groupLayout.createParallelGroup()
                        .addComponent(btnShowNewPassword)
                        .addComponent(btnShowConfirmPassword)))
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(btnCancel)
                    .addComponent(btnConfirm))
        );

        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addComponent(lblTitle)
                .addComponent(lblUsername)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewPassword)
                    .addComponent(txtNewPassword)
                    .addComponent(btnShowNewPassword))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmPassword)
                    .addComponent(txtConfirmPassword)
                    .addComponent(btnShowConfirmPassword))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnConfirm))
        );
    }
}
