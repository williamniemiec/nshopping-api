package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.Category;
import wniemiec.api.nshop.dto.CategoryDTO;
import wniemiec.api.nshop.services.exceptions.DataIntegrityException;
import wniemiec.api.nshop.services.exceptions.ObjectNotFoundException;
import wniemiec.api.nshop.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category findById(Integer id) {
        Optional<Category> cat = repository.findById(id);

        return cat.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Category.class.getName()
        ));
    }

    public Category insert(Category category) {
        category.setId(null);   // Forces the insertion of a new record,
                                // preventing it from being an update
        return repository.save(category);
    }

    public Category insert(CategoryDTO category) {
        return insert(new Category(category.getId(), category.getName()));
    }

    public Category update(Category category) {
        if (category.getId() == null) {
            throw new ObjectNotFoundException(
                    "Object not found! Id: " + category.getId() + ", Type: " + Category.class.getName()
            );
        }

        Category currentCategory = repository.findById(category.getId()).get();
        category.setProducts(currentCategory.getProducts());

        return repository.save(category);
    }

    public Category update(CategoryDTO category) {
        return update(new Category(category.getId(), category.getName()));
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("It is not possible to delete a category with products");
        }
    }

    public List<CategoryDTO> findAll() {
        List<CategoryDTO> categories = new ArrayList<>();

        for (Category category : repository.findAll()) {
            categories.add(new CategoryDTO(category));
        }

        return categories;
    }

    public Page<CategoryDTO> findPage(Integer page, Integer linesPerPage,
                                      String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Category> pages = repository.findAll(pageRequest);

        return pages.map(CategoryDTO::new);
    }
}
