package wniemiec.api.nshopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wniemiec.api.nshopping.services.EmailService;
import wniemiec.api.nshopping.services.SmtpEmailService;
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
