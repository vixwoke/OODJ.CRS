package crsAppPackage;

import java.awt.*;
    import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.swing.*;

// Email GUI Panel with operational "Specific Email List"

public class EmailNotificationPanel extends JPanel {
    public static final String CONSTRAINTS = "EMAIL NOTIFICATION";

    // UI Components
    private final JLabel lblTitle = new JLabel("Email Notification Center");
    private final JLabel lblRecipients = new JLabel("To:");
    private final JComboBox<String> cmbRecipients = new JComboBox<>(new String[] {
        "All Students", "Students Needing Recovery", "Specific Email List"
    });
    private final JLabel lblSpecificEmails = new JLabel("Enter emails (comma/semicolon/space separated):");
    private final JTextField txtSpecificEmails = new JTextField(40);
    private final JLabel lblSubject = new JLabel("Subject:");
    private final JTextField txtSubject = new JTextField(40);
    private final JLabel lblBody = new JLabel("Body:");
    private final JTextArea txtBody = new JTextArea(15, 40);
    private final JButton btnSend = new JButton("Send Notification");
    private final JScrollPane bodyScrollPane = new JScrollPane(txtBody);

    public EmailNotificationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Recipients
        gbc.gridy = 1; gbc.gridwidth = 1;
        add(lblRecipients, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(cmbRecipients, gbc);

        // Specific Email Field (initially hidden)
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblSpecificEmails, gbc);
        gbc.gridy = 3;
        add(txtSpecificEmails, gbc);
        lblSpecificEmails.setVisible(false);
        txtSpecificEmails.setVisible(false);

        // Subject
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        add(lblSubject, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtSubject, gbc);

        // Body
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        add(lblBody, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtBody.setLineWrap(true);
        txtBody.setWrapStyleWord(true);
        add(bodyScrollPane, gbc);

        // Send Button
        gbc.gridx = 1; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0; gbc.weighty = 0;
        add(btnSend, gbc);

        setActions();
    }

    private void setActions() {
        // Show/hide specific email input
        cmbRecipients.addActionListener(e -> {
            boolean specificSelected = cmbRecipients.getSelectedItem().equals("Specific Email List");
            lblSpecificEmails.setVisible(specificSelected);
            txtSpecificEmails.setVisible(specificSelected);
            revalidate();
            repaint();
        });

        // Send button
        btnSend.addActionListener(e -> {
            String subject = txtSubject.getText();
            String body = txtBody.getText();
            String recipientType = (String) cmbRecipients.getSelectedItem();

            if (subject.isBlank() || body.isBlank()) {
                JOptionPane.showMessageDialog(this, "Subject and Body cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<String> recipients = getRecipientList(recipientType);

            if (recipients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No recipients found for the selected category.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int answer = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to send this email to " + recipients.size() + " recipients?",
                    "Confirm Send", JOptionPane.YES_NO_OPTION);

            if (answer == JOptionPane.YES_OPTION) {
                try {
                    EmailManager.sendBulkEmail(recipients, subject, body);
                    JOptionPane.showMessageDialog(this, "Emails sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Reset fields
                    txtSubject.setText("");
                    txtBody.setText("");
                    txtSpecificEmails.setText("");
                } catch (MessagingException ex) {
                    JOptionPane.showMessageDialog(this, "Email sending failed. Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private List<String> getRecipientList(String type) {
        List<String> emails = new ArrayList<>();

        if (type.contains("All Students")) {
            emails.add("zenithiusimperia@gmail.com");
            emails.add("vansteinrichter@gmail.com");
        } else if (type.contains("Recovery")) {
            emails.add("tukanggores89@gmail.com");
        } else if (type.contains("Specific Email List")) {
            String input = txtSpecificEmails.getText();
            if (!input.isBlank()) {
                String[] splitEmails = input.split("[,;\\s]+");
                for (String email : splitEmails) {
                    if (!email.isBlank()) emails.add(email.trim());
                }
            }
        }

        return emails;
    }
}
