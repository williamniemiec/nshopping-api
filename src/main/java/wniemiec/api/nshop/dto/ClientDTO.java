package wniemiec.api.nshop.dto;

import org.hibernate.validator.constraints.Length;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.services.validation.ClientUpdate;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClientUpdate
public class ClientDTO implements Serializable {

    private static long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message="Required field")
    @Length(min=5, max=120, message="Minimum length: 5; Maximum length: 80")
    private String name;

    @NotEmpty(message="Required field")
    @Email(message="Invalid email")
    @Column(unique=true)
    private String email;

    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
