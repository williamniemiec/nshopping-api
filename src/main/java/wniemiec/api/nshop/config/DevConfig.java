package wniemiec.api.nshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wniemiec.api.nshop.services.DatabaseService;
import wniemiec.api.nshop.services.EmailService;
import wniemiec.api.nshop.services.SmtpEmailService;
import java.text.ParseException;


/**
 * Responsible for configuring development environment.
 */
@Configuration
@Profile("dev")
public class DevConfig {

    //------------------------------------------------------------------------
    //		Attributes
    //------------------------------------------------------------------------
    @Autowired
    private DatabaseService databaseService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;


    //------------------------------------------------------------------------
    //		Methods
    //------------------------------------------------------------------------
    @Bean
    public boolean instantiateDatabase() throws ParseException {
        if (!isCreateStrategy()) {
            return false;
        }

        databaseService.fillDatabase();

        return true;
    }

    private boolean isCreateStrategy() {
        return "create".equals(strategy);
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
