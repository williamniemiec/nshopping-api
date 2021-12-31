package wniemiec.api.nshopping.controllers.exception;

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
    protected StandardError(long timestamp, Integer status, String error, 
                            String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
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

        public StandardError build() {
            return new StandardError(
                timestamp, 
                status, 
                error, 
                message, 
                path
            );
        }
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
