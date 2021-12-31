package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Responsible for representing a client order.
 */
@Entity
public class ClientOrder implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private Date date;

    @OneToOne(mappedBy="clientOrder", cascade=CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name="id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name="id_delivery_address")
    private Address deliveryAddress;

    @OneToMany(mappedBy="id.clientOrder")
    private transient Set<ClientOrderItem> products = new HashSet<>();


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public ClientOrder() {
    }

    public ClientOrder(Integer id, Date date, Client client, Address deliveryAddress) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientOrder{");
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        sb.append("id=").append(getId());
        sb.append(", date=").append(sdf.format(getDate()));
        sb.append(", client=").append(client.getName());
        sb.append(", status=").append(getPayment().getStatus().getLabel());
        sb.append(", details=[\n");
        getProducts().forEach(product -> sb.append(product.toString()).append('\n'));
        sb.append(']');
        sb.append(", Total=").append(nf.format(getTotal()));
        sb.append('}');

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ClientOrder clientOrder = (ClientOrder) o;

        return Objects.equals(id, clientOrder.id);
    }


    //-------------------------------------------------------------------------
    //		Getters & Setters
    //-------------------------------------------------------------------------
    public Set<ClientOrderItem> getProducts() {
        return products;
    }

    public void setProducts(Set<ClientOrderItem> products) {
        this.products = products;
    }

    public double getTotal() {
        double total = 0.0;

        for (ClientOrderItem orderItem : products) {
            total += orderItem.getTotal();
        }

        return total;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
