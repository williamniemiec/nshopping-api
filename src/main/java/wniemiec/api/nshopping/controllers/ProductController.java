package wniemiec.api.nshopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wniemiec.api.nshopping.controllers.util.UrlUtils;
import wniemiec.api.nshopping.domain.Product;
import wniemiec.api.nshopping.dto.ProductDTO;
import wniemiec.api.nshopping.services.ProductService;
import java.util.List;


/**
 * Responsible for handling product requests.
 */
@RestController
@RequestMapping(value="/products")
public class ProductController {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ProductService service;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> findPage(
        @RequestParam(value="name", defaultValue="") String name,
        @RequestParam(value="categories", defaultValue="") String categoryIds,
        @RequestParam(value="page", defaultValue="0") Integer page,
        @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
        @RequestParam(value="orderBy", defaultValue="name") String orderBy,
        @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        String decodedName = UrlUtils.decodeParam(name);
        List<Integer> decodedCategoryIds = UrlUtils.decodeIntList(categoryIds);
        Page<ProductDTO> categories = service.search(
            decodedName, 
            decodedCategoryIds, 
            page,
            linesPerPage, 
            orderBy, 
            direction
        );

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Product> searchById(@PathVariable Integer id) {
        Product order = service.searchById(id);

        return ResponseEntity.ok(order);
    }
}
