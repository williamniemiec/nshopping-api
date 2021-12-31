package wniemiec.api.nshop.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import wniemiec.api.nshop.domain.enums.PaymentStatus;
import javax.persistence.Entity;
import java.util.Date;


/**
 * Responsible for representing boleto payment type.
 */
@Entity
@JsonTypeName("boletoPayment")
public class BoletoPayment extends Payment {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Date expirationDay;
    private Date paymentDay;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public BoletoPayment() {
        super();
    }

    public BoletoPayment(Integer id, PaymentStatus status, 
                         ClientOrder clientOrder, Date expirationDay, 
                         Date paymentDay) {
        super(id, status, clientOrder);
        this.expirationDay = expirationDay;
        this.paymentDay = paymentDay;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();

        result = prime * result + ((expirationDay == null) ? 0 : expirationDay.hashCode());
        result = prime * result + ((paymentDay == null) ? 0 : paymentDay.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (!super.equals(obj)) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        BoletoPayment other = (BoletoPayment) obj;
        
        if (expirationDay == null) {
            if (other.expirationDay != null) {
                return false;
            }
        } 
        else if (!expirationDay.equals(other.expirationDay)) {
            return false;
        }

        if (paymentDay == null) {
            if (other.paymentDay != null) {
                return false;
            }
        } 
        else if (!paymentDay.equals(other.paymentDay)) {
            return false;
        }

        return true;
    }
    

    //-------------------------------------------------------------------------
    //		Getters & Setters
    //-------------------------------------------------------------------------
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
