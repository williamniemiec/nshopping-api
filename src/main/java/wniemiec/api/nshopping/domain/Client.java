package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import wniemiec.api.nshop.domain.enums.ClientType;
import wniemiec.api.nshop.domain.enums.Profile;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Responsible for representing a client.
 */
@Entity
public class Client implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String name;

    @Column(unique=true)
    private String email;

    @JsonIgnore
    private String password;

    private String documentId;
    private Integer type;

    @OneToMany(mappedBy="client", cascade=CascadeType.ALL)
    private List<Address> addresses;

    @ElementCollection
    @CollectionTable(name="phones")
    private Set<String> phones;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="profiles")
    private Set<Integer> profiles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="client")
    private List<ClientOrder> clientOrders;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public Client() {
        addresses = new ArrayList<>();
        phones = new HashSet<>();
        clientOrders = new ArrayList<>();
        addProfile(Profile.CLIENT);
    }

    public Client(Integer id, String name, String email, String documentId, 
                  ClientType type, String password) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.documentId = documentId;
        this.password = password;

        if (type != null) {
            this.type = type.getId();
        }
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public void addProfile(Profile profile) {
        this.profiles.add(profile.getId());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Profile> getProfiles() {
        return profiles
            .stream()
            .map(profile -> Profile.toEnum(profile))
            .collect(Collectors.toSet());
    }
}
