package wniemiec.api.nshopping.dto;

import org.hibernate.validator.constraints.Length;
import wniemiec.api.nshopping.domain.Client;
import wniemiec.api.nshopping.services.validation.ClientUpdate;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * Responsible for representing a client received from a request.
 */
@ClientUpdate
public class ClientDTO implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Integer id;

    @NotEmpty(message="Required field")
    @Length(min=5, max=120, message="Minimum length: 5; Maximum length: 80")
    private String name;

    @NotEmpty(message="Required field")
    @Email(message="Invalid email")
    @Column(unique=true)
    private String email;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public Client toClient() {
        return new Client(id, name, email, null, null, null);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
