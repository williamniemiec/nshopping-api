package wniemiec.api.nshopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wniemiec.api.nshopping.domain.Payment;


/**
 * Responsible for storing and retrieving payment data.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
