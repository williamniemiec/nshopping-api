package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import wniemiec.api.nshop.domain.enums.PaymentStatus;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@JsonTypeName("boletoPayment")
public class BoletoPayment extends Payment {

    private static long serialVersionUID = 1L;

    private Date expirationDay;
    private Date paymentDay;

    public BoletoPayment() {
        super();
    }

    public BoletoPayment(Integer id, PaymentStatus status, ClientOrder clientOrder, Date expirationDay, Date paymentDay) {
        super(id, status, clientOrder);
        this.expirationDay = expirationDay;
        this.paymentDay = paymentDay;
    }

    public Date getExpirationDay() {
        return expirationDay;
    }

    public void setExpirationDay(Date expirationDay) {
        this.expirationDay = expirationDay;
    }

    public Date getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(Date paymentDay) {
        this.paymentDay = paymentDay;
    }
}
