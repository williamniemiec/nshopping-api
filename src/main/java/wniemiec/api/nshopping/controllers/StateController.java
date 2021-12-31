package wniemiec.api.nshopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wniemiec.api.nshopping.dto.CityDTO;
import wniemiec.api.nshopping.dto.StateDTO;
import wniemiec.api.nshopping.services.CityService;
import wniemiec.api.nshopping.services.StateService;
import java.util.List;


/**
 * Responsible for handling state requests.
 */
@RestController
@RequestMapping(value="/states")
public class StateController {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private StateService service;

    @Autowired
    private CityService cityService;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @GetMapping()
    public ResponseEntity<List<StateDTO>> findAllByOrderByName() {
        List<StateDTO> states = service.findAllByOrderByName();

        return ResponseEntity.ok().body(states);
    }

    @GetMapping(value="/{id}/cities")
    public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer id) {
        List<CityDTO> cities = cityService.findCities(id);

        return ResponseEntity.ok().body(cities);
    }
}
