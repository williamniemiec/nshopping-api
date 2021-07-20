package wniemiec.api.nshop.services;

import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.domain.ClientOrder;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(ClientOrder order);
    void sendEmail(SimpleMailMessage message);
    void sendOrderConfirmationHtmlEmail(ClientOrder order);
    void sendHtmlEmail(MimeMessage message);
    void sendNewPasswordEmail(Client client, String newPassword);
}
