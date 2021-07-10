package wniemiec.api.nshop.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wniemiec.api.nshop.domain.Product;
import wniemiec.api.nshop.dto.CategoryDTO;
import wniemiec.api.nshop.dto.ProductDTO;
import wniemiec.api.nshop.resources.util.URLUtils;
import wniemiec.api.nshop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping(value="/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<Product> searchById(@PathVariable Integer id) {
        Product order = service.searchById(id);

        return ResponseEntity.ok(order);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<ProductDTO>> findPage(
            @RequestParam(value="name", defaultValue="") String name,
            @RequestParam(value="categories", defaultValue="") String categoryIds,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        String decodedName = URLUtils.decodeParam(name);
        List<Integer> decodedCategoryIds = URLUtils.decodeIntList(categoryIds);

        Page<ProductDTO> categories = service.search(decodedName, decodedCategoryIds, page,
                                                     linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(categories);
    }
}
