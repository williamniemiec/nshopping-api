package wniemiec.api.nshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wniemiec.api.nshop.services.DatabaseService;
import wniemiec.api.nshop.services.EmailService;
import wniemiec.api.nshop.services.MockEmailService;
import wniemiec.api.nshop.services.SmtpEmailService;

import java.text.ParseException;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
