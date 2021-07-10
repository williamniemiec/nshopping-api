package wniemiec.api.nshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wniemiec.api.nshop.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
