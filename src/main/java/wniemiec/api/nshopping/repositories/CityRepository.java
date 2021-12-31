package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wniemiec.api.nshop.domain.City;
import java.util.List;


/**
 * Responsible for storing and retrieving city data.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Transactional(readOnly=true)
    List<City> findCitiesByStateIdOrderByName(Integer stateId);
}
