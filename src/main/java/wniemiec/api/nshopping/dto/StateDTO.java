package wniemiec.api.nshopping.dto;

import java.io.Serializable;
import wniemiec.api.nshopping.domain.State;


/**
 * Responsible for representing a state received from a request.
 */
public class StateDTO implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public StateDTO() {
    }

    public StateDTO(State state) {
        id = state.getId();
        name = state.getName();
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
