package wniemiec.api.nshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wniemiec.api.nshopping.domain.Category;
import wniemiec.api.nshopping.dto.CategoryDTO;
import wniemiec.api.nshopping.repositories.CategoryRepository;
import wniemiec.api.nshopping.services.exceptions.DataIntegrityException;
import wniemiec.api.nshopping.services.exceptions.ObjectNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Responsible for providing category services.
 */
@Service
public class CategoryService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private CategoryRepository categoryRepository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public Category findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);

        return category.orElseThrow(() -> generateObjectNotFoundException(id));
    }

    private ObjectNotFoundException generateObjectNotFoundException(Integer id) {
        return new ObjectNotFoundException(
            "Object not found! Id: " 
            + id 
            + ", Type: " 
            + Category.class.getName()
        );
    }

    public Category insert(Category category) {
        ensureCategoryWillBeCreated(category);

        return categoryRepository.save(category);
    }

    /**
     * Forces the insertion of a new record, preventing it from being an update.
     * 
     * @param       category Category that will be created
     */
    private void ensureCategoryWillBeCreated(Category category) {
        category.setId(null);
    }

    public Category insert(CategoryDTO category) {
        return insert(new Category(category.getId(), category.getName()));
    }

    public void update(Category category) {
        if (category.getId() == null) {
            throw generateObjectNotFoundException(category.getId());
        }

        Category currentCategory = findById(category.getId());
        
        category.setProducts(currentCategory.getProducts());
        categoryRepository.save(category);
    }

    public void update(CategoryDTO category) {
        update(new Category(
            category.getId(), 
            category.getName()
        ));
    }

    public void delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("It is not possible to delete a " +
                                             "category with products");
        }
    }

    public List<CategoryDTO> findAll() {
        List<CategoryDTO> categories = new ArrayList<>();

        for (Category category : categoryRepository.findAll()) {
            categories.add(new CategoryDTO(category));
        }

        return categories;
    }

    public Page<CategoryDTO> findPage(Integer page, Integer linesPerPage,
                                      String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(
            page, 
            linesPerPage, 
            Sort.Direction.valueOf(direction), 
            orderBy
        );
        Page<Category> pages = categoryRepository.findAll(pageRequest);

        return pages.map(CategoryDTO::new);
    }
}
