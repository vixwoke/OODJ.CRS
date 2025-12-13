package crsAppPackage;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.GroupLayout.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class VerificationPanel extends JPanel {
    public static final String CONSTRAINTS = "VERIFICATION";
    private final IPanelNavigation navigator;
    private final PasswordRecoverySession session;
    private User user;
    // UI
    private final GroupLayout groupLayout = new GroupLayout(this);
    private final JLabel lblUsername = new JLabel();
    private final JLabel lblPrompt = new JLabel();
    private final JTextField[] txtOtp = new JTextField[6];
    private final JLabel lblRemainingTime = new JLabel();
    private final JLabel lblremainingAttempts = new JLabel();
    private final JButton btnBack = new JButton("Back");
    private final JButton btnNext = new JButton("Next");
    private Timer timer;
    private final LocalDateTime endingTime;

    public VerificationPanel(IPanelNavigation navigator, PasswordRecoverySession session) {
        this.navigator = navigator;
        this.session = session;
        endingTime = session.getEndingTime();

        lblUsername.setText("Username: " + session.getUsername());
        lblPrompt.setText("Please, key in the OTP! Check it at " + session.getEmail());
        lblremainingAttempts.setText("Remaining Attempts: " + session.getRemainingAttempt());

        setLayout(groupLayout);
        placeComponents();
        setActions();

        timer.start();
    }

    public User getUser() {
        User user = this.user;
        this.user = null;
        return user;
    }

    private void setActions() {
        // Timer
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long sec = ChronoUnit.SECONDS.between(LocalDateTime.now(), endingTime);

                if (sec <= 0) {
                    showErrorMessage("Session has ended");
                    goBack();
                    return;
                }

                long mins = (sec % 3600) / 60;
                long secs = sec % 60;

                lblRemainingTime.setText(String.format("Remaining Time: %02d:%02d", mins, secs));
            }
        });

        // btnBack
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        // btnConfirm
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String otp = "";
                for (JTextField tf : txtOtp) {
                    String digit = tf.getText();
                    if (digit == null || digit.isBlank()) {
                        showErrorMessage("Please, input the OTP properly");
                        return;
                    }
                    otp += digit;
                }

                try {
                    user = session.checkOtp(otp);
                    if (user == null) {
                        showErrorMessage("Wrong OTP!");
                        lblremainingAttempts.setText("Remaining Attempts: " + session.getRemainingAttempt());
                        return;
                    }
                    navigator.goTo(ResetPasswordPanel.CONSTRAINTS);
                } catch (Exception ex) {
                    showErrorMessage(ex.getMessage());
                    goBack();
                }
            }
            
        });
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private void goBack() {
        timer.stop();
        navigator.goBack();
    }

    private void placeComponents() {
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        // Text Fields
        SequentialGroup txtHorizontalGroup = groupLayout.createSequentialGroup().addContainerGap(0, 10000);
        ParallelGroup txtVerticalGroup = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);

        for (int i = 0; i < 6; i++) {
            txtOtp[i] = new JTextField(1);
            JTextField tf = txtOtp[i];
            int index = i;

            tf.setFont(new Font("SansSerif", Font.BOLD, 28));
            // Limit to 1 char only
            tf.setDocument(new PlainDocument() {
                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                    if (str == null) return;

                    if ((getLength() + str.length() <= 1) && (Character.isDigit(str.charAt(0)))) {
                        super.insertString(offs, str, a);
                        // Move to the next text Field
                        int nextIndex = index + 1;
                        nextIndex = (nextIndex < 6) ? nextIndex : 5;
                        txtOtp[nextIndex].requestFocusInWindow();
                    } else if (getLength() == 1 && str.length() == 1  && Character.isDigit(str.charAt(0))) {
                        // Change the number
                        tf.setText(str);
                        // Move to the next text Field
                        int nextIndex = index + 1;
                        nextIndex = (nextIndex < 6) ? nextIndex : 5;
                        txtOtp[nextIndex].requestFocusInWindow();
                    }
                }
            });

            tf.getInputMap().put(KeyStroke.getKeyStroke("BACK_SPACE"), "backspace");
            tf.getActionMap().put("backspace", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (tf.getText().isEmpty()) {
                        // Move to the previous text Field
                        int previousIndex = index - 1;
                        previousIndex = (previousIndex > 0) ? previousIndex : 0;
                        txtOtp[previousIndex].requestFocusInWindow();
                    } else {
                        tf.getActionMap().get(DefaultEditorKit.deletePrevCharAction).actionPerformed(e);
                    }
                }
            });

            txtHorizontalGroup.addComponent(tf);
            txtHorizontalGroup.addContainerGap(0, 10000);
            txtVerticalGroup.addComponent(tf);
        }

        // Placement
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblUsername)
                .addComponent(lblPrompt)
                .addGroup(txtHorizontalGroup)
                .addComponent(lblRemainingTime)
                .addComponent(lblremainingAttempts)
                .addGroup(groupLayout.createSequentialGroup()
                    .addComponent(btnBack)
                    .addComponent(btnNext))
        );

        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addComponent(lblUsername)
                .addComponent(lblPrompt)
                .addGroup(txtVerticalGroup)
                .addComponent(lblRemainingTime)
                .addComponent(lblremainingAttempts)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnNext))
        );
    }
}
