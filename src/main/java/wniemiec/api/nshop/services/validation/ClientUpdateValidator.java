package wniemiec.api.nshop.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import wniemiec.api.nshop.controllers.exception.FieldMessage;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.domain.enums.ClientType;
import wniemiec.api.nshop.dto.ClientDTO;
import wniemiec.api.nshop.dto.ClientNewDTO;
import wniemiec.api.nshop.repositories.ClientRepository;
import wniemiec.api.nshop.services.validation.util.BR;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ClientUpdate name) {

    }

    @Override
    public boolean isValid(ClientDTO obj, ConstraintValidatorContext context) {
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        List<FieldMessage> list = new ArrayList<>();

        Integer id = Integer.parseInt(map.get("id"));

        if (hasRegisteredEmailExceptClient(obj.getEmail(), id))
            list.add(new FieldMessage("email", "Email already registered"));

        for (FieldMessage field : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(field.getMessage())
                .addPropertyNode(field.getFieldName())
                .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private boolean hasRegisteredEmailExceptClient(String email, Integer clientId) {
        Client client = clientRepository.findByEmail(email);
        return (client != null && !client.getId().equals(clientId));
    }
}
