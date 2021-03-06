package wniemiec.api.nshopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import wniemiec.api.nshopping.domain.enums.PaymentStatus;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * Responsible for representing a payment.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(
    use=JsonTypeInfo.Id.NAME, 
    include=JsonTypeInfo.As.PROPERTY, 
    property="@type"
)
public abstract class Payment implements Serializable {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    
    private Integer status;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="id_order")
    @MapsId
    private ClientOrder clientOrder;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    protected Payment() {
    }

    protected Payment(Integer id, PaymentStatus status, ClientOrder clientOrder) {
        this.id = id;
        this.status = (status == null) ? null : status.getId();
        this.clientOrder = (clientOrder == null) ? null : clientOrder;
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

        Payment payment = (Payment) o;

        return Objects.equals(id, payment.id);
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

    public PaymentStatus getStatus() {
        return PaymentStatus.toEnum(status);
    }

    public void setStatus(PaymentStatus status) {
        this.status = status.getId();
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }
}
