package crsAppPackage;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Class utility untuk mengelola koneksi dan pengiriman email (SMTP).
 * Catatan: Membutuhkan library JavaMail API (javax.mail) di classpath.
 */
public class EmailManager {

    // Ganti dengan kredensial email Anda yang sebenarnya (atau gunakan file konfigurasi)
    private static final String SENDER_EMAIL = "your.application.email@example.com";
    private static final String SENDER_PASSWORD = "your-app-password"; // Gunakan App Password jika memakai Gmail/Outlook

    private static Session getSession() {
        Properties props = new Properties();
        
        // Contoh konfigurasi Gmail
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS untuk keamanan
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587"); // Port standar untuk TLS

        // Tambahkan konfigurasi lain jika diperlukan (misalnya, untuk debugging)
        // props.put("mail.debug", "true"); 

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    /**
     * Mengirim email ke satu penerima.
     * @param recipientEmail Alamat email penerima.
     * @param subject Subjek email.
     * @param body Isi email.
     * @throws MessagingException jika pengiriman gagal.
     */
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

    /**
     * Mengirim email secara massal ke daftar penerima.
     * @param recipientEmails List alamat email penerima.
     * @param subject Subjek email.
     * @param body Isi email.
     * @throws MessagingException jika ada kegagalan total.
     */
    public static void sendBulkEmail(java.util.List<String> recipientEmails, String subject, String body) throws MessagingException {
        Session session = getSession();
        
        for (String email : recipientEmails) {
            try {
                // Menggunakan koneksi yang sama untuk mengirim banyak email
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject(subject);
                message.setText(body);
                
                Transport.send(message);
                
                // Jeda sebentar untuk menghindari throttling oleh server SMTP
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException ignored) {}

            } catch (MessagingException e) {
                // Mencatat error dan melanjutkan ke penerima berikutnya
                System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            }
        }
        // Catatan: Anda mungkin ingin mengembalikan daftar email yang gagal dikirim.
    }
}