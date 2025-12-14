package crsAppPackage;

import java.time.*;
import java.time.temporal.ChronoUnit;
import javax.mail.*;
import javax.mail.internet.*;

public class PasswordRecoverySession {
    private static final int MAXIMUM_ATTEMPTS = 5;
    private static final int MAXIMUM_TIME_IN_MINUTES = 5;
    private final User user;
    private final LocalDateTime creationTime;
    private final LocalDateTime endingTime;
    private String otp;
    private int manyAttempts = 0;

    public PasswordRecoverySession(User user) {
        this.user = user;
        creationTime = LocalDateTime.now();
        // Add the maximum time spent
        endingTime = creationTime.plus(MAXIMUM_TIME_IN_MINUTES, ChronoUnit.MINUTES);
        generateOtp();
        sendOtp();
    }

    public String getUsername() {
        return (user.getUsername() == null) ? "" : user.getUsername();
    }

    public String getEmail() {
        return (user.getEmail() == null) ? "" : user.getEmail();
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public int getRemainingAttempt() {
        int attemptLeft = MAXIMUM_ATTEMPTS - manyAttempts;
        return (attemptLeft < 0) ? 0 : attemptLeft;
    }

    public User checkOtp(String otp) {
        manyAttempts += 1;
        if (manyAttempts > MAXIMUM_ATTEMPTS) {
            throw new RuntimeException("Maximum Attempt has been reached");
        }
        if (LocalDateTime.now().isAfter(endingTime)) {
            throw new RuntimeException("Session has ended! Try again next time!");
        }

        if (this.otp.equals(otp)) {
            return user;
        }

        // Immediately tell if  the maximum attept has been reached
        if (manyAttempts == MAXIMUM_ATTEMPTS) {
            throw new RuntimeException("Maximum Attempt has been reached");
        }

        return null;
    }

    private void generateOtp() {
        otp = "";

        for (int i = 0; i < 6; i++) {
            otp += String.valueOf(((int) (Math.random() * 10)));
        }
    }

    private void sendOtp() {
        try {
            EmailManager.sendEmail(getEmail(), "Your otp number is:", otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Couldn't send the email");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurs when sending email");
        }
    }
}
