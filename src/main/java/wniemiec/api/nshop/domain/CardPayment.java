package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import wniemiec.api.nshop.domain.enums.PaymentStatus;

import javax.persistence.Entity;

@Entity
@JsonTypeName("cardPayment")
public class CardPayment extends Payment {

    private static long serialVersionUID = 1L;

    private Integer installments;

    public CardPayment() {
        super();
    }

    public CardPayment(Integer id, PaymentStatus status, ClientOrder clientOrder, Integer installments) {
        super(id, status, clientOrder);
        this.installments = installments;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }
}
