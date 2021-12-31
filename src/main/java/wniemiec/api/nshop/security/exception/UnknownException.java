package wniemiec.api.nshop.security.exception;


/**
 * Responsible for representing unknown runtime errors.
 */
public class UnknownException extends RuntimeException {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    public UnknownException(String message) {
        super(message);
    }
}
