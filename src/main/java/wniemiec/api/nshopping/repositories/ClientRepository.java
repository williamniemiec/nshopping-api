package wniemiec.api.nshopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wniemiec.api.nshopping.domain.Client;


/**
 * Responsible for storing and retrieving client data.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Transactional(readOnly=true)
    Client findByEmail(String email);
}
