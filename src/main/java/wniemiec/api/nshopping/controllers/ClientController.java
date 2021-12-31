package wniemiec.api.nshopping.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wniemiec.api.nshopping.domain.Client;
import wniemiec.api.nshopping.dto.ClientDTO;
import wniemiec.api.nshopping.dto.ClientNewDTO;
import wniemiec.api.nshopping.services.ClientService;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


/**
 * Responsible for handling client requests.
 */
@RestController
@RequestMapping(value="/clients")
public class ClientController {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ClientService service;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ClientDTO>> getAll() {
        List<ClientDTO> categories = service.findAll();

        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Client> searchById(@PathVariable Integer id) {
        Client client = service.searchById(id);

        return ResponseEntity.ok(client);
    }

    @GetMapping(value="/email")
    public ResponseEntity<Client> searchByEmail(
        @RequestParam(value="value") String email
    ) {
        Client client = service.findByEmail(email);

        return ResponseEntity.ok(client);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value="/page")
    public ResponseEntity<Page<ClientDTO>> findPage(
        @RequestParam(value="page", defaultValue="0") Integer page,
        @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
        @RequestParam(value="orderBy", defaultValue="name") String orderBy,
        @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        Page<ClientDTO> categories = service.findPage(
            page, 
            linesPerPage, 
            orderBy, 
            direction
        );

        return ResponseEntity.ok().body(categories);
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO client) {
        Client response = service.insert(client);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value="/picture")
    public ResponseEntity<Void> uploadProfilePicture(
        @RequestParam(name="file") MultipartFile file
    ) {
        URI response = service.uploadProfilePicture(file);
        return ResponseEntity.created(response).build();
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Void> update(
        @Valid @RequestBody ClientDTO client, 
        @PathVariable Integer id
    ) {
        client.setId(id);
        service.update(client);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
