package wniemiec.api.nshopping.controllers.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wniemiec.api.nshopping.services.exceptions.AuthorizationException;
import wniemiec.api.nshopping.services.exceptions.DataIntegrityException;
import wniemiec.api.nshopping.services.exceptions.FileException;
import wniemiec.api.nshopping.services.exceptions.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;


/**
 * Responsible for handling controllers exceptions.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,
                                                        HttpServletRequest request) {
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.NOT_FOUND.value())
            .error("Not found")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e,
                                                       HttpServletRequest request) {
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Data integrity")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> dataValidation(MethodArgumentNotValidException e,
                                                        HttpServletRequest request) {
        ValidationError error = new ValidationError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .error("Validation error")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        for (FieldError field : e.getBindingResult().getFieldErrors()) {
            error.addError(
                field.getField(), 
                field.getDefaultMessage()
            );
        }

        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(error);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(AuthorizationException e,
                                                       HttpServletRequest request) {
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.FORBIDDEN.value())
            .error("Authorization")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(error);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException e,
                                              HttpServletRequest request) {
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("File")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e,
                                                       HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatusCode());
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(httpStatus.value())
            .error("Amazon service")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(httpStatus)
            .body(error);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e,
                                                      HttpServletRequest request) {
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Amazon client")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonClientException e,
                                                  HttpServletRequest request) {
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Amazon S3")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> unknown(Exception e, 
                                                 HttpServletRequest request) {
        StandardError error = new StandardError.Builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Unknown")
            .message(e.getMessage())
            .path(request.getRequestURI())
            .build();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
    }
}
