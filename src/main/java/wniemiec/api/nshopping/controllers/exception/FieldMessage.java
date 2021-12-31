package wniemiec.api.nshopping.controllers.exception;

import java.io.Serializable;


/**
 * Responsible for representing an error along with its message.
 */
public class FieldMessage implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private String fieldName;
    private String message;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public FieldMessage() {

    }

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }


    //-------------------------------------------------------------------------
    //		Getters & Setters
    //-------------------------------------------------------------------------
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
