package wniemiec.api.nshop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.internet.MimeMessage;


/**
 * Responsible for providing STMP email services.
 */
public class SmtpEmailService extends AbstractEmailService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger logger;


    //-------------------------------------------------------------------------
    //		Initialization blocks
    //-------------------------------------------------------------------------
    static {
        logger = LoggerFactory.getLogger(MockEmailService.class);
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Sending email...");
        mailSender.send(message);
        logger.info("Email has sent");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        logger.info("Sending email...");
        javaMailSender.send(message);
        logger.info("Email has sent");
    }
}
