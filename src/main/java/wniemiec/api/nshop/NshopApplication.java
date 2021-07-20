package wniemiec.api.nshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wniemiec.api.nshop.services.DatabaseService;
import wniemiec.api.nshop.services.S3Service;

@SpringBootApplication
public class NshopApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NshopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
