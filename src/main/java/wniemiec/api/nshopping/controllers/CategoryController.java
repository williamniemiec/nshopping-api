package wniemiec.api.nshopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wniemiec.api.nshopping.domain.Category;
import wniemiec.api.nshopping.dto.CategoryDTO;
import wniemiec.api.nshopping.services.CategoryService;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


/**
 * Responsible for handling category requests.
 */
@RestController
@RequestMapping(value="/categories")
public class CategoryController {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private CategoryService service;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> categories = service.findAll();

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value="/page")
    public ResponseEntity<Page<CategoryDTO>> findPage(
        @RequestParam(value="page", defaultValue="0") Integer page,
        @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
        @RequestParam(value="orderBy", defaultValue="name") String orderBy,
        @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        Page<CategoryDTO> categories = service.findPage(
            page, 
            linesPerPage, 
            orderBy, 
            direction
        );

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Category> searchById(@PathVariable Integer id) {
        Category category = service.findById(id);

        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO category) {
        Category response = service.insert(category);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value="/{id}")
    public ResponseEntity<Void> update(
        @Valid @RequestBody CategoryDTO category, 
        @PathVariable Integer id
    ) {
        category.setId(id);
        service.update(category);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
