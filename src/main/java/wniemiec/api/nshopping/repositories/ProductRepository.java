package wniemiec.api.nshopping.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wniemiec.api.nshopping.domain.Category;
import wniemiec.api.nshopping.domain.Product;
import java.util.List;


/**
 * Responsible for storing and retrieving product data.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional(readOnly=true)
    @Query(
        "SELECT DISTINCT prod " +
        "FROM Product prod " +
        "INNER JOIN prod.categories cat " +
        "WHERE  prod.name LIKE %:name% " +
        "       AND cat IN :categories"
    )
    Page<Product> search(
        @Param("name") String name,
        @Param("categories") List<Category> categories,
        Pageable pageRequest
    );
}
