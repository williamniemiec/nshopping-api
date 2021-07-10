package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.Payment;
import wniemiec.api.nshop.repositories.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public void save(Payment payment) {
        repository.save(payment);
    }
}
