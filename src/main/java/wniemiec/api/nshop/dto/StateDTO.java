package wniemiec.api.nshop.dto;

import org.hibernate.validator.constraints.Length;
import wniemiec.api.nshop.domain.State;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class StateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public StateDTO() {
    }

    public StateDTO(State state) {
        id = state.getId();
        name = state.getName();
    }

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
