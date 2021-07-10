package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wniemiec.api.nshop.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
}
