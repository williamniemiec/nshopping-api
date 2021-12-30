package wniemiec.api.nshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wniemiec.api.nshop.services.EmailService;
import wniemiec.api.nshop.services.SmtpEmailService;
import java.text.ParseException;


/**
 * Responsible for configuring production environment.
 */
@Configuration
@Profile("prod")
public class ProdConfig {

    //------------------------------------------------------------------------
    //		Methods
    //------------------------------------------------------------------------
    @Bean
    public boolean instantiateDatabase() throws ParseException {
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
