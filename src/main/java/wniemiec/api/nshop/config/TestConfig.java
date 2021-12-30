package wniemiec.api.nshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wniemiec.api.nshop.services.DatabaseService;
import wniemiec.api.nshop.services.EmailService;
import wniemiec.api.nshop.services.MockEmailService;
import java.text.ParseException;


/**
 * Responsible for configuring test environment.
 */
@Configuration
@Profile("test")
public class TestConfig {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private DatabaseService databaseService;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Bean
    public boolean instantiateDatabase() throws ParseException {
        databaseService.fillDatabase();

        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
