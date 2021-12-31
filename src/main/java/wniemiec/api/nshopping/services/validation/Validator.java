package wniemiec.api.nshop.services.validation;

import wniemiec.api.nshop.controllers.exception.FieldMessage;
import javax.validation.ConstraintValidatorContext;
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
