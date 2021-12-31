package wniemiec.api.nshop.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import wniemiec.api.nshop.controllers.exception.FieldMessage;
import wniemiec.api.nshop.domain.enums.ClientType;
import wniemiec.api.nshop.dto.ClientNewDTO;
import wniemiec.api.nshop.repositories.ClientRepository;
import wniemiec.api.nshop.services.validation.util.BrazilianDocumentValidator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


/**
 * Responsible for validating client insertions.
 */
public class ClientInsertValidator extends Validator 
implements ConstraintValidator<ClientInsert, ClientNewDTO> {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ClientRepository clientRepository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public boolean isValid(ClientNewDTO newClient, ConstraintValidatorContext context) {
        List<FieldMessage> errors = new ArrayList<>();

        if (isNaturalPerson(newClient) && !hasValidCpf(newClient))
            errors.add(new FieldMessage("documentId", "Invalid CPF"));

        if (isLegalPerson(newClient) && !hasValidCnpj(newClient))
            errors.add(new FieldMessage("documentId", "Invalid CNPJ"));

        if (hasRegisteredEmail(newClient.getEmail()))
            errors.add(new FieldMessage("email", "Email already registered"));

        displayErrors(context, errors);

        return errors.isEmpty();
    }

    private boolean isNaturalPerson(ClientNewDTO obj) {
        return obj.getType().equals(ClientType.NATURAL_PERSON.getId());
    }

    private boolean hasValidCpf(ClientNewDTO newClient) {
        return BrazilianDocumentValidator.isValidCpf(newClient.getDocumentId());
    }

    private boolean hasValidCnpj(ClientNewDTO newClient) {
        return BrazilianDocumentValidator.isValidCnpj(newClient.getDocumentId());
    }

    private boolean isLegalPerson(ClientNewDTO newClient) {
        return newClient.getType().equals(ClientType.LEGAL_PERSON.getId());
    }

    private boolean hasRegisteredEmail(String email) {
        return (clientRepository.findByEmail(email) != null);
    }
}
