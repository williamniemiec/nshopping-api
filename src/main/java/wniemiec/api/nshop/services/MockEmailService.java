package wniemiec.api.nshop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import javax.mail.internet.MimeMessage;


/**
 * Responsible for providing fake email services.
 */
public class MockEmailService extends AbstractEmailService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
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
        logger.info("{}", message);
        logger.info("Email has sent");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        logger.info("Sending html email...");
        logger.info("{}", message);
        logger.info("Email has sent");
    }
}
