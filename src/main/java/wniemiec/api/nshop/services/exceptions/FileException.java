package wniemiec.api.nshop.services.exceptions;


/**
 * Responsible for handling file exceptions.
 */
public class FileException extends RuntimeException {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    
    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
