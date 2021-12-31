package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.Payment;
import wniemiec.api.nshop.repositories.PaymentRepository;


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
