package wniemiec.api.nshopping.services;

import org.springframework.stereotype.Service;

import wniemiec.api.nshopping.domain.BoletoPayment;
import java.util.Calendar;
import java.util.Date;


/**
 * Responsible for providing boleto payment services.
 */
@Service
public class BoletoPaymentService {

    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public void fillPayment(BoletoPayment payment, Date paymentDate) {
        Calendar expirationDate = generateExpirationDate(paymentDate);
        
        payment.setExpirationDay(expirationDate.getTime());
    }

    private Calendar generateExpirationDate(Date paymentDate) {
        Calendar expirationDate = Calendar.getInstance();
        
        expirationDate.setTime(paymentDate);
        expirationDate.add(Calendar.DAY_OF_MONTH, 7);

        return expirationDate;
    }
}
