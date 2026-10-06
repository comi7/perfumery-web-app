package rs.ac.bg.fon.perfumeryproject.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author Milica
 */

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String token) {
        String link = "http://localhost:3000/complete-registration?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Parfumerie - Complete your registration");
        message.setText(
            "Thank you for registering at Parfumerie!\n\n" +
            "Click the link below to complete your registration:\n\n" +
            link + "\n\n" +
            "This link expires in 24 hours.\n\n" +
            "If you did not request this, please ignore this email."
        );

        mailSender.send(message);
    }
    


    public void sendOrderConfirmationEmail(String toEmail, byte[] pdfBytes, Integer orderId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Parfumerie - Order #" + orderId + " Confirmation");
            helper.setText(
                "<h2>Thank you for your order!</h2>" +
                "<p>Your order <strong>#" + orderId + "</strong> has been successfully placed.</p>" +
                "<p>Please find your order details in the attached PDF.</p>" +
                "<br><p>Parfumerie Team</p>",
                true
            );

            helper.addAttachment("Order_" + orderId + ".pdf",
                new ByteArrayResource(pdfBytes),
                "application/pdf");

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send order email: " + e.getMessage());
        }
    }
}
