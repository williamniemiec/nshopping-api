package wniemiec.api.nshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wniemiec.api.nshop.domain.Category;
import wniemiec.api.nshop.dto.CategoryDTO;
import wniemiec.api.nshop.services.CategoryService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value="/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @RequestMapping(method=RequestMethod.GET, value="/{id}")
    public ResponseEntity<Category> searchById(@PathVariable Integer id) {
        Category cat = service.findById(id);

        return ResponseEntity.ok(cat);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method=RequestMethod.POST)
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
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoryDTO category, @PathVariable Integer id) {
        category.setId(id);
        Category response = service.update(category);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> categories = service.findAll();

        return ResponseEntity.ok().body(categories);
    }

    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Page<CategoryDTO>> findPage(
        @RequestParam(value="page", defaultValue="0") Integer page,
        @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
        @RequestParam(value="orderBy", defaultValue="name") String orderBy,
        @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        Page<CategoryDTO> categories = service.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(categories);
    }
}
