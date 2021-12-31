package wniemiec.api.nshopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wniemiec.api.nshopping.domain.ClientOrderItem;


/**
 * Responsible for storing and retrieving items from client orders.
 */
public interface ClientOrderItemRepository extends JpaRepository<ClientOrderItem, Integer> {
}
