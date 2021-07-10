package wniemiec.api.nshop.services;

import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.BoletoPayment;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoPaymentService {

    public void fillPayment(BoletoPayment payment, Date paymentDate) {
        // expiration date: +1 week
        // ToDo: Replace by request to some web service
        Calendar cal = Calendar.getInstance();
        cal.setTime(paymentDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        payment.setExpirationDay(cal.getTime());
    }
}
