package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.dto.StateDTO;
import wniemiec.api.nshop.repositories.StateRepository;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Responsible for providing state services.
 */
@Service
public class StateService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private StateRepository repository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public List<StateDTO> findAllByOrderByName() {
        return repository
            .findAllByOrderByName()
            .stream()
            .map(state -> new StateDTO(state))
            .collect(Collectors.toList());
    }
}
