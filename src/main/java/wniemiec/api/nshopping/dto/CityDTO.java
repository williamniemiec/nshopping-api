package wniemiec.api.nshopping.dto;

import java.io.Serializable;
import wniemiec.api.nshopping.domain.City;


/**
 * Responsible for representing a city received from a request.
 */
public class CityDTO implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public CityDTO() {
    }

    public CityDTO(City city) {
        id = city.getId();
        name = city.getName();
    }


    //-------------------------------------------------------------------------
    //		Getters & Setters
    //-------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
