package wniemiec.api.nshop.services.exceptions;


/**
 * Responsible for handling authorization exceptions.
 */
public class AuthorizationException extends RuntimeException {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    
    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
