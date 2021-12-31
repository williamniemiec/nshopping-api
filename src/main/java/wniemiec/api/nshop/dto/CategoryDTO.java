package wniemiec.api.nshop.dto;

import org.hibernate.validator.constraints.Length;
import wniemiec.api.nshop.domain.Category;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Responsible for representing a category received from a request.
 */
public class CategoryDTO implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Integer id;

    @NotEmpty(message="Required field")
    @Length(min=5, max=80, message="Minimum length: 5; Maximum length: 80")
    private String name;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public CategoryDTO() {
    }

    public CategoryDTO(Category category) {
        id = category.getId();
        name = category.getName();
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
