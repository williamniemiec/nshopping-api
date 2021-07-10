package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wniemiec.api.nshop.domain.ClientOrder;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {
}
