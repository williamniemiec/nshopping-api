package wniemiec.api.nshopping.services;

import org.springframework.security.core.context.SecurityContextHolder;
import wniemiec.api.nshopping.security.UserSpringSecurity;


/**
 * Responsible for providing user authentication services.
 */
public class UserService {

    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    private UserService() {
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public static UserSpringSecurity authenticatedUser() {
        try {
            return (UserSpringSecurity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}
