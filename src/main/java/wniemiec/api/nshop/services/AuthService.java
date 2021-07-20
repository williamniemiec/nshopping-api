package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.services.exceptions.ObjectNotFoundException;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email) {
        Client client = clientService.findByEmail(email);

        if (client == null)
            throw new ObjectNotFoundException("There is no user with such email");

        String newPassword = newPassword();
        client.setPassword(passwordEncoder.encode(newPassword));

        clientService.save(client);
        emailService.sendNewPasswordEmail(client, newPassword);
    }

    private String newPassword() {
        char[] arr = new char[10];

        for (int i = 0; i < 10; i++) {
            arr[i] = randomChar();
        }

        return new String(arr);
    }

    private char randomChar() {
        int opt = random.nextInt(3);

        if (opt == 0) { // Number
            return (char) (random.nextInt(10) + 48);
        }
        else if (opt == 1) {  // Upper case letter
            return (char) (random.nextInt(26) + 67);
        }
        else {  // Lower case letter
            return (char) (random.nextInt(26) + 97);
        }
    }
}
