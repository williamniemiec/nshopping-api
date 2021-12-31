package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wniemiec.api.nshop.domain.ClientOrderItem;


/**
 * Responsible for storing and retrieving items from client orders.
 */
public interface ClientOrderItemRepository extends JpaRepository<ClientOrderItem, Integer> {
}
