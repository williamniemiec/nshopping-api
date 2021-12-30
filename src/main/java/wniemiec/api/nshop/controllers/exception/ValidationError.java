package wniemiec.api.nshop.controllers.exception;

import java.util.ArrayList;
import java.util.List;


/**
 * Responsible for representing validation errors.
 */
public class ValidationError extends StandardError {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private List<FieldMessage> errors = new ArrayList<>();


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    public ValidationError(long timestamp, Integer status, String error, 
                           String message, String path) {
        super(timestamp, status, error, message, path);
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }


    //-------------------------------------------------------------------------
    //		Getters
    //-------------------------------------------------------------------------
    public List<FieldMessage> getErrors() {
        return errors;
    }
}
