package wniemiec.api.nshopping.services.exceptions;


/**
 * Responsible for handling object not found exceptions.
 */
public class ObjectNotFoundException extends RuntimeException {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    
    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
