package wniemiec.api.nshopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wniemiec.api.nshopping.domain.State;
import java.util.List;


/**
 * Responsible for storing and retrieving state data.
 */
@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

    @Transactional(readOnly=true)
    List<State> findAllByOrderByName();
}
