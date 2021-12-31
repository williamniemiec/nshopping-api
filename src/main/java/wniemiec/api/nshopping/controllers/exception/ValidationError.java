package wniemiec.api.nshopping.controllers.exception;

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
    private ValidationError(long timestamp, Integer status, String error, 
                           String message, String path) {
        super(timestamp, status, error, message, path);
    }


    //-------------------------------------------------------------------------
    //		Builder
    //-------------------------------------------------------------------------
    public static class Builder {

        private long timestamp;
        private Integer status;
        private String error;
        private String message;
        private String path;

        public Builder() {
            timestamp = 0;
            status = -1;
            error = "undefined";
            message = "";
            path = "";
        }

        public Builder timestamp(long time) {
            timestamp = time;

            return this;
        }

        public Builder status(Integer statusCode) {
            status = statusCode;

            return this;
        }

        public Builder error(String errorType) {
            error = errorType;

            return this;
        }

        public Builder message(String errorMessage) {
            message = errorMessage;

            return this;
        }

        public Builder path(String sourcePath) {
            path = sourcePath;

            return this;
        }

        public ValidationError build() {
            return new ValidationError(
                timestamp, 
                status, 
                error, 
                message, 
                path
            );
        }
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
