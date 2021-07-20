package wniemiec.api.nshop.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.dto.ClientDTO;
import wniemiec.api.nshop.dto.ClientNewDTO;
import wniemiec.api.nshop.services.ClientService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {

    @Autowired
    private ClientService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Client> searchById(@PathVariable Integer id) {
        Client client = service.searchById(id);

        return ResponseEntity.ok(client);
    }

    @RequestMapping(value="/email", method=RequestMethod.GET)
    public ResponseEntity<Client> searchByEmail(@RequestParam(value="value") String email) {
        Client client = service.findByEmail(email);

        return ResponseEntity.ok(client);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO client) {
        Client response = service.insert(client);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO Client, @PathVariable Integer id) {
        Client.setId(id);
        Client response = service.update(Client);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<ClientDTO>> getAll() {
        List<ClientDTO> categories = service.findAll();

        return ResponseEntity.ok().body(categories);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Page<ClientDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        Page<ClientDTO> categories = service.findPage(page, linesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(categories);
    }

    @RequestMapping(value="/picture", method=RequestMethod.POST)
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
        URI response = service.uploadProfilePicture(file);
        return ResponseEntity.created(response).build();
    }
}
