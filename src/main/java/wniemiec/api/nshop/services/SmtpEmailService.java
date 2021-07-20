package wniemiec.api.nshop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage message) {
        LOG.info("Sending email...");
        mailSender.send(message);
        LOG.info("Email has sent");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        LOG.info("Sending email...");
        javaMailSender.send(message);
        LOG.info("Email has sent");
    }
}
