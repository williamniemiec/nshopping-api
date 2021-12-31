package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wniemiec.api.nshop.domain.Address;


/**
 * Responsible for storing and retrieving address data.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
