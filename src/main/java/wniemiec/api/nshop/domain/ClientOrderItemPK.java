package wniemiec.api.nshop.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClientOrderItemPK implements Serializable {

    private static long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name="id_order")
    private ClientOrder clientOrder;

    @ManyToOne
    @JoinColumn(name="id_product")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ClientOrderItemPK that = (ClientOrderItemPK) o;

        return Objects.equals(clientOrder, that.clientOrder) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientOrder, product);
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
