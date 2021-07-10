package wniemiec.api.nshop.dto;

import org.hibernate.validator.constraints.Length;
import wniemiec.api.nshop.services.validation.ClientInsert;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClientInsert
public class ClientNewDTO implements Serializable {

    private static long serialVersionUID = 1L;

    @NotEmpty(message="Required field")
    @Length(min=5, max=120, message="Minimum length: 5; Maximum length: 80")
    private String name;

    @NotEmpty(message="Required field")
    @Email(message="Invalid email")
    private String email;

    @NotEmpty(message="Required field")
    private String documentId;

    private Integer type;

    @NotEmpty(message="Required field")
    private String streetName;

    @NotEmpty(message="Required field")
    private String number;

    private String apt;
    private String district;

    @NotEmpty(message="Required field")
    private String zip;

    private Integer cityId;

    @NotEmpty(message="Required field")
    private String phone1;

    private String phone2;
    private String phone3;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setId_city(Integer cityId) {
        this.cityId = cityId;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }
}
