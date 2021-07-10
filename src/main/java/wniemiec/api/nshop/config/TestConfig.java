package wniemiec.api.nshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import wniemiec.api.nshop.services.DatabaseService;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DatabaseService databaseService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        databaseService.fillDatabase();

        return true;
    }
}
