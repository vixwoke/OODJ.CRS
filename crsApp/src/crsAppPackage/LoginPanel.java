package crsAppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class LoginPanel extends JPanel {
    public static final String CONSTRAINTS = "LOGIN";
    // Data
    private User user;
    // Navigate through panel
    private final IPanelNavigation navigator;
    // panels & layouts
    private final GroupLayout groupLayout = new GroupLayout(this);
    // components of loginPanel
    private final JLabel lblTitle = new JLabel("Course Recovery Plan");
    private final JLabel lblLogin = new JLabel("User Login");
    private final JLabel lblUsername = new JLabel("Username:");
    private final JLabel lblPassword = new JLabel("Password:");
    private final JTextField txtUsername = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final JButton btnShowPassword =  new JButton("👁️");
    private final JButton btnConfirm =  new JButton("Confirm");
    private final JButton btnForgotPassword =  new JButton("Forgot your password? Click here!");

    public LoginPanel(IPanelNavigation navigator) {
        this.navigator = navigator;

        setLayout(groupLayout);

        placeComponents();
        designComponents();
        setActions();
    }

    public User getUser() {
        User user = this.user;
        this.user = null;
        return user;
    }

    public void reset() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private void setActions() {
        // btnConfirm
        // When clicked, verify the username and password then go to next panel based on the role
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                
                try {
                    user = new User(username, password);
                } catch (Exception ex) {
                    showErrorMessage(ex.getMessage());
                    return;
                }

                // Record timestamp
                /*try {
                    LocalDateTime currentTime = LocalDateTime.now();
                    FileManager.addData("Resources/Data/Timestamp.txt", new String[] { "LOG IN", currentTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) });
                } catch (Exception ex) {
                }*/

                navigator.goNext();
            }
        });

        // btnShowPassword
        // When pressed, show the plain text in txtPassword then hide again after released
        btnShowPassword.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Left click
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // show the password
                    txtPassword.setEchoChar((char) 0);
                }
            }

            public void mouseReleased(MouseEvent e) {
                // hide the password
                txtPassword.setEchoChar(new JPasswordField().getEchoChar());
                // Set focus to txtPassword
                txtPassword.requestFocusInWindow();
            }
        });

        // btnForgotPassword
        // When clicked, key in username and if valid, go to VerificationPanel
        JPanel currentPanel = this;
        btnForgotPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Input the username:");

                if (username == null) {
                    return;
                }

                try {
                    user = new User(username);
                    navigator.goTo(VerificationPanel.CONSTRAINTS);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(currentPanel, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        // txtUsername
        // When enter/arrow up/arrow down is pressed, set focus to txtPassword
        txtUsername.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP) {
                    txtPassword.requestFocusInWindow();
                }
            }
        });

        // txtPassword
        // When arrow up/arrow down is pressed, set focus to txtUsername
        // When enter is pressed, click btnConfirm
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP) {
                    txtUsername.requestFocusInWindow();
                } else if (code == KeyEvent.VK_ENTER) {
                    btnConfirm.doClick();
                }
            }
        });
    }

    private void designComponents() {
        // btnForgotPassword
        btnForgotPassword.setBorderPainted(false);
        btnForgotPassword.setFocusPainted(false);
        btnForgotPassword.setContentAreaFilled(false);
        btnForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnForgotPassword.setForeground(Color.BLUE);
        btnForgotPassword.setFont(btnForgotPassword.getFont().deriveFont(Font.ITALIC));
    }

    private void placeComponents() {
        // enable auto create gaps
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        // Make a group of components have the same height (vertical)
        groupLayout.linkSize(SwingConstants.VERTICAL, lblUsername, txtUsername, lblPassword, txtPassword, btnShowPassword);

        // add Components
        // Horizontal Group
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(lblTitle)
            .addComponent(lblLogin)
            .addGroup(groupLayout.createSequentialGroup()
                .addContainerGap(0, 10000)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsername)
                    .addComponent(lblPassword))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword))
                .addComponent(btnShowPassword)
                .addContainerGap(0, 10000))
            .addComponent(btnConfirm)
            .addComponent(btnForgotPassword)
        );

        // Vertical Group
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
            .addContainerGap(0, 10000)
            .addComponent(lblTitle)
            .addComponent(lblLogin)
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblUsername)
                .addComponent(txtUsername))
            .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblPassword)
                .addComponent(txtPassword)
                .addComponent(btnShowPassword))
            .addComponent(btnConfirm)
            .addComponent(btnForgotPassword)
            .addContainerGap(0, 10000)
        );
    }
}
