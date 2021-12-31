package wniemiec.api.nshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wniemiec.api.nshopping.domain.Category;
import wniemiec.api.nshopping.domain.Product;
import wniemiec.api.nshopping.dto.ProductDTO;
import wniemiec.api.nshopping.repositories.CategoryRepository;
import wniemiec.api.nshopping.repositories.ProductRepository;
import wniemiec.api.nshopping.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;


/**
 * Responsible for providing product services.
 */
@Service
public class ProductService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public Product searchById(Integer id) {
        Optional<Product> order = repository.findById(id);

        return order.orElseThrow(() -> new ObjectNotFoundException(
            "Object not found! Id: " 
            + id + ", Type: " 
            + Product.class.getName()
        ));
    }

    public Page<ProductDTO> search(String name, List<Integer> categoryIds,
                                   Integer page, Integer linesPerPage,
                                   String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(
            page, 
            linesPerPage, 
            Sort.Direction.valueOf(direction), 
            orderBy
        );
        List<Category> categories = categoryRepository.findAllById(categoryIds);

        return repository
            .search(name, categories, pageRequest)
            .map(ProductDTO::new);
    }

    public Product findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
