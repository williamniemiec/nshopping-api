package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wniemiec.api.nshop.domain.ClientOrderItem;

public interface ClientOrderItemRepository extends JpaRepository<ClientOrderItem, Integer> {
}
