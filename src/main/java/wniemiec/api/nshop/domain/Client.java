package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import wniemiec.api.nshop.domain.enums.ClientType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Client implements Serializable {

    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique=true)
    private String email;

    private String documentId;
    private Integer type;

    @OneToMany(mappedBy="client", cascade=CascadeType.ALL)
    private List<Address> addresses;

    @ElementCollection
    @CollectionTable(name="phones")
    private Set<String> phones;

    @JsonIgnore
    @OneToMany(mappedBy="client")
    private List<ClientOrder> clientOrders;

    public Client() {
        addresses = new ArrayList<>();
        phones = new HashSet<>();
        clientOrders = new ArrayList<>();
    }

    public Client(Integer id, String name, String email, String documentId, ClientType type) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.documentId = documentId;

        if (type != null)
            this.type = type.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public ClientType getType() {
        return ClientType.toEnum(type);
    }

    public void setType(ClientType type) {
        this.type = type.getId();
    }

    public Set<String> getPhones() {
        return phones;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<ClientOrder> getClientOrders() {
        return clientOrders;
    }
}
