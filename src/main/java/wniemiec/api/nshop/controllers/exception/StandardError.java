package wniemiec.api.nshop.controllers.exception;

import java.io.Serializable;


/**
 * Responsible for representing standard errors (errors without a specific
 * error class).
 */
public class StandardError implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    public StandardError(long timestamp, Integer status, String error, 
                         String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }


    //-------------------------------------------------------------------------
    //		Getters & Setters
    //-------------------------------------------------------------------------
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
