package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.domain.ClientOrder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String supportMail;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(ClientOrder order) {
        SimpleMailMessage message = generateSimpleMailMessageFromClientOrder(order);
        sendEmail(message);
    }

    protected SimpleMailMessage generateSimpleMailMessageFromClientOrder(ClientOrder order) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(supportMail);
        message.setTo(order.getClient().getEmail());
        message.setSubject("Your order has confirmed! Code: " + order.getId());
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setText(order.toString());

        return message;
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(ClientOrder order) {
        try {
            MimeMessage message = generateMimeMessageFromClientOrder(order);
            sendHtmlEmail(message);
        }
        catch (MessagingException e) {
            sendOrderConfirmationEmail(order);
        }
    }

    protected MimeMessage generateMimeMessageFromClientOrder(ClientOrder order) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setFrom(supportMail);
        messageHelper.setTo(order.getClient().getEmail());
        messageHelper.setSubject("Order has confirmed! Code: " + order.getId());
        messageHelper.setSentDate(new Date(System.currentTimeMillis()));
        messageHelper.setText(htmlFromTemplateClientOrder(order), true);

        return message;
    }

    protected String htmlFromTemplateClientOrder(ClientOrder order) {
        Context context = new Context();

        context.setVariable("order", order);

        return templateEngine.process("email/order-confirmation", context);
    }

    @Override
    public void sendNewPasswordEmail(Client client, String newPassword) {
        SimpleMailMessage message = prepareNewPasswordEmail(client, newPassword);
        sendEmail(message);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(supportMail);
        message.setTo(client.getEmail());
        message.setSubject("Password recovery");
        message.setSentDate(new Date(System.currentTimeMillis()));
        message.setText("New password: " + newPassword);

        return message;
    }
}
