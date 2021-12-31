package wniemiec.api.nshopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


/**
 * Responsible for representing a product.
 */
@Entity
public class Product implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    private double price;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name="product_category",
        joinColumns=@JoinColumn(name="id_product"),
        inverseJoinColumns=@JoinColumn(name="id_category")
    )
    private List<Category> categories;

    @JsonIgnore
    @OneToMany(mappedBy="id.product")
    private transient Set<ClientOrderItem> orderItems = new HashSet<>();


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public Product() {
        categories = new ArrayList<>();
    }

    public Product(Integer id, String name, double price) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Product product = (Product) o;
        
        return id.equals(product.id);
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

    @JsonIgnore
    public List<ClientOrder> getOrders() {
        List<ClientOrder> ordersList = new ArrayList<>();

        for (ClientOrderItem orderItem : orderItems) {
            ordersList.add(orderItem.getOrder());
        }

        return ordersList;
    }

    public Set<ClientOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<ClientOrderItem> orders) {
        this.orderItems = orders;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
