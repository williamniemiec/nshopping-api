package wniemiec.api.nshopping.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import wniemiec.api.nshopping.domain.enums.PaymentStatus;
import javax.persistence.Entity;


/**
 * Responsible for representing card payment type.
 */
@Entity
@JsonTypeName("cardPayment")
public class CardPayment extends Payment {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private Integer installments;


    //-------------------------------------------------------------------------
    //		Constructors
    //-------------------------------------------------------------------------
    public CardPayment() {
        super();
    }

    public CardPayment(Integer id, PaymentStatus status, ClientOrder clientOrder, 
                       Integer installments) {
        super(id, status, clientOrder);
        this.installments = installments;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        
        result = prime * result + ((installments == null) ? 0 : installments.hashCode());
        
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
        
        CardPayment other = (CardPayment) obj;

        return haveSameInstallments(other.installments);
    }

    private boolean haveSameInstallments(Integer otherInstallments) {
        boolean sameInstallments = true;

        if (installments == null) {
            if (otherInstallments != null) {
                sameInstallments = false;
            }
        } 
        else if (!installments.equals(otherInstallments)) {
            sameInstallments = false;
        }

        return sameInstallments;
    }


    //-------------------------------------------------------------------------
    //		Getters & Setters
    //-------------------------------------------------------------------------
    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }
}
