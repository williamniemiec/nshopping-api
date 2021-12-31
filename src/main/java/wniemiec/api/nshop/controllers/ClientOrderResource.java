package wniemiec.api.nshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wniemiec.api.nshop.domain.ClientOrder;
import wniemiec.api.nshop.dto.ClientDTO;
import wniemiec.api.nshop.services.ClientOrderService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/orders")
public class ClientOrderResource {

    @Autowired
    private ClientOrderService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<ClientOrder> searchById(@PathVariable Integer id) {
        ClientOrder order = service.searchById(id);

        return ResponseEntity.ok(order);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientOrder order) {
        ClientOrder newOrder = service.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrder.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<ClientOrder>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="date") String orderBy,
            @RequestParam(value="direction", defaultValue="DESC") String direction
    ) {
        Page<ClientOrder> orders = service.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(orders);
    }
}