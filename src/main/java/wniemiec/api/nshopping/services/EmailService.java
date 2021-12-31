package wniemiec.api.nshopping.services;

import org.springframework.mail.SimpleMailMessage;
import wniemiec.api.nshopping.domain.Client;
import wniemiec.api.nshopping.domain.ClientOrder;
import javax.mail.internet.MimeMessage;


/**
 * Responsible for defining email services.
 */
public interface EmailService {

    void sendOrderConfirmationEmail(ClientOrder order);
    void sendEmail(SimpleMailMessage message);
    void sendOrderConfirmationHtmlEmail(ClientOrder order);
    void sendHtmlEmail(MimeMessage message);
    void sendNewPasswordEmail(Client client, String newPassword);
}
