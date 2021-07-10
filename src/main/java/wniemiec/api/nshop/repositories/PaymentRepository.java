package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wniemiec.api.nshop.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
