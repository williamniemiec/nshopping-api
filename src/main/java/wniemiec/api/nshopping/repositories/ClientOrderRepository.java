package wniemiec.api.nshopping.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wniemiec.api.nshopping.domain.Client;
import wniemiec.api.nshopping.domain.ClientOrder;


/**
 * Responsible for storing and retrieving client orders.
 */
@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {

    @Transactional(readOnly=true)
    Page<ClientOrder> findByClient(Client order, Pageable pageRequest);
}
