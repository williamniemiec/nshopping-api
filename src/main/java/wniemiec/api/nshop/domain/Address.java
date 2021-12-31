package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * Responsible for representing an address.
 */
@Entity
public class Address implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String streetName;
    private String number;
    private String apt;
    private String district;
    private String zip;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name="id_city")
    private City city;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public Address() {
    }

    public Address(Integer id, String streetName, String number, String apt,
                   String district, String zip, Client client, City city) {
        this.id = id;
        this.streetName = streetName;
        this.number = number;
        this.apt = apt;
        this.district = district;
        this.zip = zip;
        this.client = client;
        this.city = city;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Address address = (Address) o;

        return Objects.equals(id, address.id);
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

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
