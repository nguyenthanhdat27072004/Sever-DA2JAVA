package util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Lớp để gửi email.
 */
public class EmailService {

    private static final String HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String SENDER_EMAIL = "datnguyenthanh2707@gmail.com";
    private static final String SENDER_PASSWORD = "your-password";


    public static boolean sendEmail(String toEmail, String subject, String message) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(HOST_NAME);
            email.setSmtpPort(SMTP_PORT);
            email.setAuthenticator(new DefaultAuthenticator(SENDER_EMAIL, SENDER_PASSWORD));
            email.setSSLOnConnect(true);
            email.setFrom(SENDER_EMAIL);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(toEmail);
            email.send();
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }
}
