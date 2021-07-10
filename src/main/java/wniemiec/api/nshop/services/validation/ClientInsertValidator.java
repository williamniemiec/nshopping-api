package wniemiec.api.nshop.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import wniemiec.api.nshop.domain.enums.ClientType;
import wniemiec.api.nshop.dto.ClientNewDTO;
import wniemiec.api.nshop.repositories.ClientRepository;
import wniemiec.api.nshop.resources.exception.FieldMessage;
import wniemiec.api.nshop.services.validation.util.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ClientInsert name) {

    }

    @Override
    public boolean isValid(ClientNewDTO obj, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (obj.getType().equals(ClientType.NATURAL_PERSON.getId()) && !BR.isValidCPF(obj.getDocumentId()))
            list.add(new FieldMessage("documentId", "Invalid CPF"));

        if (obj.getType().equals(ClientType.LEGAL_PERSON.getId()) && !BR.isValidCNPJ(obj.getDocumentId()))
            list.add(new FieldMessage("documentId", "Invalid CNPJ"));

        if (hasRegisteredEmail(obj.getEmail()))
            list.add(new FieldMessage("email", "Email already registered"));

        for (FieldMessage field : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(field.getMessage())
                .addPropertyNode(field.getFieldName())
                .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private boolean hasRegisteredEmail(String email) {
        return (clientRepository.findByEmail(email) != null);
    }
}
