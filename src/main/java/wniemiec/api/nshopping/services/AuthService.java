package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.services.exceptions.ObjectNotFoundException;
import java.util.Random;


/**
 * Responsible for providing authentication services.
 */
@Service
public class AuthService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ClientService clientService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    public AuthService() {
        random = new Random();
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public void sendNewPassword(String email) {
        Client client = clientService.findByEmail(email);

        if (client == null)
            throw new ObjectNotFoundException("There is no user with such email");

        String newPassword = generateRandomPasswordForClient(client);
        
        saveClientPassword(client, newPassword);
    }

    private void saveClientPassword(Client client, String newPassword) {
        clientService.save(client);
        emailService.sendNewPasswordEmail(client, newPassword);
    }

    private String generateRandomPasswordForClient(Client client) {
        String newPassword = generateRandomPassword();
        
        client.setPassword(passwordEncoder.encode(newPassword));

        return newPassword;
    }

    private String generateRandomPassword() {
        char[] password = new char[10];

        for (int i = 0; i < 10; i++) {
            password[i] = generateRandomCharacter();
        }

        return new String(password);
    }

    private char generateRandomCharacter() {
        char character;
        int opt = random.nextInt(3);

        if (opt == 0) {
            character = generateRandomNumber();
        }
        else if (opt == 1) {  
            character = generateRandomUpperCaseLetter();
        }
        else {
            character = generateRandomLowerCaseLetter();
        }

        return character;
    }

    private char generateRandomNumber() {
        return (char) (random.nextInt(10) + 48);
    }

    private char generateRandomUpperCaseLetter() {
        return (char) (random.nextInt(26) + 67);
    }

    private char generateRandomLowerCaseLetter() {
        return (char) (random.nextInt(26) + 97);
    }
}
