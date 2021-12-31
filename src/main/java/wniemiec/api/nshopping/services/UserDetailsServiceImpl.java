package wniemiec.api.nshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wniemiec.api.nshopping.domain.Client;
import wniemiec.api.nshopping.repositories.ClientRepository;
import wniemiec.api.nshopping.security.UserSpringSecurity;


/**
 * Responsible for providing user services.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ClientRepository repository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public UserDetails loadUserByUsername(String email) 
    throws UsernameNotFoundException {
        Client client = repository.findByEmail(email);

        if (client == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSpringSecurity(
            client.getId(),
            client.getEmail(),
            client.getPassword(),
            client.getProfiles()
        );
    }
}
