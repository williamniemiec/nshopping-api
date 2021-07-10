package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class ClientOrder implements Serializable {

    private static long serialVersionUID = 1L;

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
    private Set<ClientOrderItem> products = new HashSet<>();

    public ClientOrder() {
    }

    public ClientOrder(Integer id, Date date, Client client, Address deliveryAddress) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
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
