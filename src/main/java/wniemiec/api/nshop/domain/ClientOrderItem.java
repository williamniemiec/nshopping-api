package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;


/**
 * Responsible for representing an item from a client order.
 */
@Entity
public class ClientOrderItem {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @JsonIgnore
    @EmbeddedId
    private ClientOrderItemPK id = new ClientOrderItemPK();

    private Double discount;
    private Integer amount;
    private Double price;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public ClientOrderItem() {
    }

    public ClientOrderItem(ClientOrder order, Product product, Double discount, 
                           Integer amount, Double price) {
        id.setClientOrder(order);
        id.setProduct(product);
        this.discount = discount;
        this.amount = amount;
        this.price = price;
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

        ClientOrderItem that = (ClientOrderItem) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientOrderItem{");
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        sb.append("name=").append(getProduct().getName());
        sb.append(", amount=").append(amount);
        sb.append(", price=").append(nf.format(price));
        sb.append(", total=").append(nf.format(getTotal()));
        sb.append('}');

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    //-------------------------------------------------------------------------
    //		Getters & Setters
    //-------------------------------------------------------------------------
    public double getTotal() {
        return ((price - discount) * amount);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    @JsonIgnore
    public ClientOrder getOrder() {
        return id.getClientOrder();
    }

    public void setProduct(Product p) {
        id.setProduct(p);
    }

    public void setOrder(ClientOrder order) {
        id.setClientOrder(order);
    }

    public ClientOrderItemPK getId() {
        return id;
    }

    public void setId(ClientOrderItemPK id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
