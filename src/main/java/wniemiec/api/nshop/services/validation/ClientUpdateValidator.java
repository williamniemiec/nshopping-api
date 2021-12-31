package wniemiec.api.nshop.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import wniemiec.api.nshop.controllers.exception.FieldMessage;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.dto.ClientDTO;
import wniemiec.api.nshop.repositories.ClientRepository;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Responsible for validating client updates.
 */
public class ClientUpdateValidator extends Validator 
implements ConstraintValidator<ClientUpdate, ClientDTO> {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientRepository clientRepository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public boolean isValid(ClientDTO updatedClient, ConstraintValidatorContext context) {
        List<FieldMessage> errors = new ArrayList<>();
        
        Integer id = extractIdFromRequest();

        if (hasRegisteredEmailExceptClient(updatedClient.getEmail(), id)) {
            errors.add(new FieldMessage("email", "Email already registered"));
        }

        displayErrors(context, errors);

        return errors.isEmpty();
    }

    private Integer extractIdFromRequest() {
        Integer id = null;
        Map<?, ?> map = new HashMap<>();
        Object obj = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (obj instanceof Map<?, ?>) {
            map = (Map<?, ?>) obj;
        }

        if (map.containsKey("id")) {
            id = Integer.parseInt((String) map.get("id"));
        }
        
        return id;
    }

    private boolean hasRegisteredEmailExceptClient(String email, Integer clientId) {
        Client client = clientRepository.findByEmail(email);
        
        return  (client != null)
                && !client.getId().equals(clientId);
    }
}
