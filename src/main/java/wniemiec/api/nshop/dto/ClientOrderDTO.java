package wniemiec.api.nshop.dto;

import wniemiec.api.nshop.domain.Address;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.domain.ClientOrder;
import wniemiec.api.nshop.domain.ClientOrderItem;
import wniemiec.api.nshop.domain.Payment;
import wniemiec.api.nshop.domain.enums.PaymentStatus;
import java.io.Serializable;
import java.util.*;

public class ClientOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Payment payment;
    private Client client;
    private Address deliveryAddress;
    private Set<ClientOrderItem> products = new HashSet<>();

    public ClientOrderDTO() {
    }

    public ClientOrder toClientOrder(Client client) {
        ClientOrder clientOrder = new ClientOrder(
            null,
            new Date(),
            client,
            deliveryAddress
        );

        clientOrder.setProducts(products);
        clientOrder.setPayment(payment);
        clientOrder.getPayment().setStatus(PaymentStatus.PENDING);
        clientOrder.getPayment().setClientOrder(clientOrder);

        return clientOrder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result + ((deliveryAddress == null) ? 0 : deliveryAddress.hashCode());
        result = prime * result + ((payment == null) ? 0 : payment.hashCode());
        result = prime * result + ((products == null) ? 0 : products.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientOrderDTO other = (ClientOrderDTO) obj;
        if (client == null) {
            if (other.client != null)
                return false;
        } else if (!client.equals(other.client))
            return false;
        if (deliveryAddress == null) {
            if (other.deliveryAddress != null)
                return false;
        } else if (!deliveryAddress.equals(other.deliveryAddress))
            return false;
        if (payment == null) {
            if (other.payment != null)
                return false;
        } else if (!payment.equals(other.payment))
            return false;
        if (products == null) {
            if (other.products != null)
                return false;
        } else if (!products.equals(other.products))
            return false;
        return true;
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

    public Set<ClientOrderItem> getProducts() {
        return products;
    }

    public void setProducts(Set<ClientOrderItem> products) {
        this.products = products;
    }
}
