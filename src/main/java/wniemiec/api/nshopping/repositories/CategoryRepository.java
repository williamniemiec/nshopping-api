package wniemiec.api.nshopping.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wniemiec.api.nshopping.domain.Category;


/**
 * Responsible for storing and retrieving category data.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
