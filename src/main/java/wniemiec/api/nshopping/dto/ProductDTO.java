package wniemiec.api.nshopping.dto;

import java.io.Serializable;
import wniemiec.api.nshopping.domain.Product;


/**
 * Responsible for representing a product received from a request.
 */
public class ProductDTO implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private double price;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        price = getPrice();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
