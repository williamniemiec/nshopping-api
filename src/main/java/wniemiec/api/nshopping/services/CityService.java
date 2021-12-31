package wniemiec.api.nshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshopping.dto.CityDTO;
import wniemiec.api.nshopping.repositories.CityRepository;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Responsible for providing city services.
 */
@Service
public class CityService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private CityRepository repository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public List<CityDTO> findCities(Integer stateId) {
        return repository
            .findCitiesByStateIdOrderByName(stateId)
            .stream()
            .map(CityDTO::new)
            .collect(Collectors.toList());
    }
}
