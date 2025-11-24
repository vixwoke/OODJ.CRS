package crsAppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class AddUpdateUserPanel extends JPanel {
    // Panel Navigation
    private final IPanelNavigation navigator;
    // Layout
    private final GroupLayout groupLayout = new GroupLayout(this);
    // Components
    private final JLabel lblTitle = new JLabel();
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnConfirm = new JButton("Confirm");
    // Account Information
    private final JPanel accountInfoPanel = new JPanel();
    private final GroupLayout accountInfoLayout = new GroupLayout(accountInfoPanel);
    // Username
    private final JLabel lblUsername = new JLabel("Username:");
    private final JTextField txtUsername = new JTextField(15);
    // Status
    private final JLabel lblStatus = new JLabel("Active");
    private final JCheckBox cbStatus = new JCheckBox();
    // Role
    private final JLabel lblRole = new JLabel("Role:");
    private final JComboBox<String> cmbRole = new JComboBox<>(new String[] { User.ROLE.ADMIN, User.ROLE.ACADEMIC_OFFICER }); 
    // Password
    private final JLabel lblNewPassword = new JLabel("Password:");
    private final JPasswordField txtNewPassword = new JPasswordField(30);
    private final JLabel lblConfirmPassword = new JLabel("Confirm new password:");
    private final JButton btnShowNewPassword =  new JButton("👁️");
    private final JPasswordField txtConfirmPassword = new JPasswordField(30);
    private final JButton btnShowConfirmPassword =  new JButton("👁️");
    // Additional Component to add later
    private final JPanel additionalPanel = new JPanel();
    // Personal Information
    private final JPanel personalInfoPanel = new JPanel();
    private final GroupLayout personalInfoLayout = new GroupLayout(personalInfoPanel);
    // Name
    private final JLabel lblName = new JLabel("Name:");
    private final JTextField txtName = new JTextField(30);
    // Gender
    private final JLabel lblGender = new JLabel("Gender:");
    private final JComboBox<String> cmbGender = new JComboBox<>(new String[] { User.GENDER.MALE, User.GENDER.FEMALE });
    // Email
    private final JLabel lblEmail = new JLabel("Email:");
    private final JTextField txtEmail = new JTextField(30);

    public AddUpdateUserPanel(IPanelNavigation navigator) {
        this.navigator = navigator;
        setLayout(groupLayout);
        designComponents();
        placeComponents();
        setActions();
    }

    public void resetPanel() {
        txtUsername.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
        txtEmail.setText("");
        txtName.setText("");
        cmbRole.setSelectedIndex(0);
        cmbGender.setSelectedIndex(0);
        cbStatus.setSelected(true);
    }

    protected abstract boolean btnConfirmDoClick();

    private void setActions() {
        // btnCancel
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                navigator.goBack();
            }
        });

        // btnConfirm
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (btnConfirmDoClick()) {
                    navigator.goBack();
                }
            }
        });

        // btnShowNewPassword
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

        // btnShowConfirmPassword
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

    protected final void setAdditionalPanelVisibile(boolean aFlag) {
        additionalPanel.setVisible(aFlag);
    }

    protected final void setTxtPasswordVisible(boolean aFlag) {
        lblNewPassword.setVisible(aFlag);
        txtNewPassword.setVisible(aFlag);
        btnShowNewPassword.setVisible(aFlag);
        lblConfirmPassword.setVisible(aFlag);
        txtConfirmPassword.setVisible(aFlag);
        btnShowConfirmPassword.setVisible(aFlag);
    }

    protected final void setDefaultValue(String username, String password, boolean active, String role, String name, String gender, String email) {
        txtUsername.setText(username);
        txtNewPassword.setText(password);
        txtConfirmPassword.setText(password);
        cbStatus.setSelected(active);
        cmbRole.setSelectedItem(role);
        txtName.setText(name);
        cmbGender.setSelectedItem(gender);
        txtEmail.setText(email);
    }

    protected final String getUsername() {
        return txtUsername.getText();
    }

    protected final String getPassword() {
        return (new String(txtNewPassword.getPassword()));
    }

    protected final String getRole() {
        return cmbRole.getItemAt(cmbRole.getSelectedIndex());
    }

    protected final boolean isActive() {
        return cbStatus.isSelected();
    }

    protected final String getUserName() {
        return txtName.getText();
    }

    protected final String getGender() {
        return cmbGender.getItemAt(cmbGender.getSelectedIndex());
    }

    protected final String getEmail() {
        return txtEmail.getText();
    }

    protected final boolean checkConfirmPassword() {
        return (getPassword().equals(new String(txtConfirmPassword.getPassword())));
    }

    protected final void setTitle(String title) {
        lblTitle.setText(title);
    }

    protected final void setAdditionalLayout(LayoutManager mgr) {
        additionalPanel.setLayout(mgr);
    }

    protected final void addToAdditionalPanel(Component comp) {
        additionalPanel.add(comp);
    }

    protected final void addToAdditionalPanel(Component comp, Object constraints) {
        additionalPanel.add(comp, constraints);
    }

    private void designComponents() {
        accountInfoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
        personalInfoPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));
    }

    private void placeComponents() {
        accountInfoPanel.setLayout(accountInfoLayout);
        personalInfoPanel.setLayout(personalInfoLayout);
        //  Enable auto gaps
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        accountInfoLayout.setAutoCreateContainerGaps(true);
        accountInfoLayout.setAutoCreateGaps(true);
        personalInfoLayout.setAutoCreateContainerGaps(true);
        personalInfoLayout.setAutoCreateGaps(true);

        // Link the size of some components
        accountInfoLayout.linkSize(SwingConstants.VERTICAL, lblUsername, txtUsername, lblRole, cmbRole, lblStatus, cbStatus, 
            lblNewPassword, txtNewPassword, btnShowNewPassword, lblConfirmPassword, txtConfirmPassword, btnShowConfirmPassword);
        personalInfoLayout.linkSize(SwingConstants.VERTICAL, lblName, txtName, lblGender, cmbGender, lblEmail, txtEmail);
        groupLayout.linkSize(SwingConstants.VERTICAL, accountInfoPanel, personalInfoPanel);
        groupLayout.linkSize(SwingConstants.HORIZONTAL, accountInfoPanel, personalInfoPanel);
        
        // this
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGap(0, 0, 10000)
                .addComponent(lblTitle)
                .addComponent(accountInfoPanel)
                .addComponent(personalInfoPanel)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(btnCancel)
                    .addComponent(btnConfirm))
                .addGap(0, 0, 10000)
        );

        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addContainerGap(0, 10000)
                .addComponent(lblTitle)
                .addComponent(accountInfoPanel)
                .addComponent(personalInfoPanel)
                .addGroup(groupLayout.createParallelGroup()
                    .addComponent(btnCancel)
                    .addComponent(btnConfirm))
                .addContainerGap(0, 10000)
        );

        // accountInfoPanel
        accountInfoLayout.setHorizontalGroup(
            accountInfoLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(accountInfoLayout.createSequentialGroup()
                    .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblUsername)
                        .addComponent(lblRole)
                        .addComponent(lblStatus)
                        .addComponent(lblNewPassword)
                        .addComponent(lblConfirmPassword))
                    .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txtUsername)
                        .addComponent(cmbRole)
                        .addComponent(cbStatus)
                        .addComponent(txtNewPassword)
                        .addComponent(txtConfirmPassword))
                    .addGroup(accountInfoLayout.createParallelGroup()
                        .addComponent(btnShowNewPassword)
                        .addComponent(btnShowConfirmPassword)))
                .addComponent(additionalPanel)
        );

        accountInfoLayout.setVerticalGroup(
            accountInfoLayout.createSequentialGroup()
                .addContainerGap(0, 10000)
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername))
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRole)
                    .addComponent(cmbRole))
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(cbStatus))
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewPassword)
                    .addComponent(txtNewPassword)
                    .addComponent(btnShowNewPassword))
                .addGroup(accountInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConfirmPassword)
                    .addComponent(txtConfirmPassword)
                    .addComponent(btnShowConfirmPassword))
                .addComponent(additionalPanel)
                .addContainerGap(0, 10000)
        );

        // personalInfoPanel
        personalInfoLayout.setHorizontalGroup(
            personalInfoLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(personalInfoLayout.createSequentialGroup()
                    .addGroup(personalInfoLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblName)
                        .addComponent(lblGender)
                        .addComponent(lblEmail))
                    .addGroup(personalInfoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txtName)
                        .addComponent(cmbGender)
                        .addComponent(txtEmail)))
        );

        personalInfoLayout.setVerticalGroup(
            personalInfoLayout.createSequentialGroup()
                .addContainerGap(0, 10000)
                .addGroup(personalInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName))
                .addGroup(personalInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGender)
                    .addComponent(cmbGender))
                .addGroup(personalInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail))
                .addContainerGap(0, 10000)
        );
    }
}
