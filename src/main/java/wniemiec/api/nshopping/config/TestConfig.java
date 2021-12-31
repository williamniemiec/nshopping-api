package wniemiec.api.nshopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wniemiec.api.nshopping.services.DatabaseService;
import wniemiec.api.nshopping.services.EmailService;
import wniemiec.api.nshopping.services.MockEmailService;
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
