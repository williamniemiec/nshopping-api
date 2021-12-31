package wniemiec.api.nshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshopping.domain.Payment;
import wniemiec.api.nshopping.repositories.PaymentRepository;


/**
 * Responsible for providing payment services.
 */
@Service
public class PaymentService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private PaymentRepository repository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public void save(Payment payment) {
        repository.save(payment);
    }
}
