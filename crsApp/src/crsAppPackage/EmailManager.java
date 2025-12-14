/*package crsAppPackage;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


// Class utility for handling connection and email delivery (SMTP).

public class EmailManager {

    private static final String SENDER_EMAIL = "your.application.email@example.com";
    private static final String SENDER_PASSWORD = "your-app-password"; 

    private static Session getSession() {
        Properties props = new Properties();
        
        // Gmail configuration examples
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587"); 


        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    
    public static void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        if (recipientEmail == null || recipientEmail.isBlank()) {
            throw new MessagingException("Recipient email cannot be blank.");
        }
        
        Session session = getSession();
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            
        } catch (MessagingException e) {
            System.err.println("Error sending email to " + recipientEmail + ": " + e.getMessage());
            throw new MessagingException("Failed to send email: " + e.getMessage());
        }
    }

    
    public static void sendBulkEmail(java.util.List<String> recipientEmails, String subject, String body) throws MessagingException {
        Session session = getSession();
        
        for (String email : recipientEmails) {
            try {
                
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject(subject);
                message.setText(body);
                
                Transport.send(message);
                
                
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException ignored) {}

            } catch (MessagingException e) {
                System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            }
        }
    }
}
*/