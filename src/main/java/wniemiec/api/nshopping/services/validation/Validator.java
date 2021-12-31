package wniemiec.api.nshopping.services.validation;

import javax.validation.ConstraintValidatorContext;
import wniemiec.api.nshopping.controllers.exception.FieldMessage;
import java.util.List;


/**
 * Responsible for representing validators.
 */
public abstract class Validator {
    
    protected void displayErrors(ConstraintValidatorContext context, 
                                 List<FieldMessage> errors) {
        for (FieldMessage error : errors) {
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate(error.getMessage())
                .addPropertyNode(error.getFieldName())
                .addConstraintViolation();
        }
    }
}
