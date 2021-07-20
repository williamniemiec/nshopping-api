package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.dto.CityDTO;
import wniemiec.api.nshop.repositories.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<CityDTO> findCities(Integer stateId) {
        return repository
            .findCitiesByStateIdOrderByName(stateId)
            .stream()
            .map(city -> new CityDTO(city))
            .collect(Collectors.toList());
    }
}
