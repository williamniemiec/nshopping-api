package wniemiec.api.nshopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wniemiec.api.nshopping.domain.ClientOrder;
import wniemiec.api.nshopping.dto.ClientOrderDTO;
import wniemiec.api.nshopping.services.ClientOrderService;
import javax.validation.Valid;
import java.net.URI;


/**
 * Responsible for handling order request.
 */
@RestController
@RequestMapping(value="/orders")
public class ClientOrderController {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ClientOrderService service;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @GetMapping()
    public ResponseEntity<Page<ClientOrder>> findPage(
        @RequestParam(value="page", defaultValue="0") Integer page,
        @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
        @RequestParam(value="orderBy", defaultValue="date") String orderBy,
        @RequestParam(value="direction", defaultValue="DESC") String direction
    ) {
        Page<ClientOrder> orders = service.findPage(
            page, 
            linesPerPage, 
            orderBy, 
            direction
        );

        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<ClientOrder> searchById(@PathVariable Integer id) {
        ClientOrder order = service.searchById(id);

        return ResponseEntity.ok(order);
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientOrderDTO order) {
        ClientOrder newOrder = service.insert(order);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newOrder.getId())
            .toUri();

        return ResponseEntity.created(uri).build();
    }    
}
