package wniemiec.api.nshop.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wniemiec.api.nshop.domain.City;
import wniemiec.api.nshop.dto.CityDTO;
import wniemiec.api.nshop.dto.StateDTO;
import wniemiec.api.nshop.services.CityService;
import wniemiec.api.nshop.services.StateService;

import java.util.List;

@RestController
@RequestMapping(value="/states")
public class StateResource {

    @Autowired
    private StateService service;

    @Autowired
    private CityService cityService;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<StateDTO>> findAllByOrderByName() {
        List<StateDTO> states = service.findAllByOrderByName();

        return ResponseEntity.ok().body(states);
    }

    @RequestMapping(value="/{id}/cities", method=RequestMethod.GET)
    public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer id) {
        List<CityDTO> cities = cityService.findCities(id);

        return ResponseEntity.ok().body(cities);
    }
}
