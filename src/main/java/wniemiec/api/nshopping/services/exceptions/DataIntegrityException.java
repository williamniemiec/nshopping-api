package wniemiec.api.nshopping.services.exceptions;


/**
 * Responsible for handling data exceptions.
 */
public class DataIntegrityException extends RuntimeException {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public DataIntegrityException(String message) {
        super(message);
    }

    public DataIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
