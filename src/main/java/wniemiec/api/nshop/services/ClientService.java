package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wniemiec.api.nshop.domain.Address;
import wniemiec.api.nshop.domain.Category;
import wniemiec.api.nshop.domain.City;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.domain.enums.ClientType;
import wniemiec.api.nshop.dto.CategoryDTO;
import wniemiec.api.nshop.dto.ClientDTO;
import wniemiec.api.nshop.dto.ClientNewDTO;
import wniemiec.api.nshop.exceptions.DataIntegrityException;
import wniemiec.api.nshop.exceptions.ObjectNotFoundException;
import wniemiec.api.nshop.repositories.AddressRepository;
import wniemiec.api.nshop.repositories.ClientRepository;
import wniemiec.api.nshop.repositories.StateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    public Client searchById(Integer id) {
        Optional<Client> client = repository.findById(id);

        return client.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Client.class.getName()
        ));
    }

    public Client update(Client client) {
        if (client.getId() == null) {
            throw new ObjectNotFoundException(
                    "Object not found! Id: " + client.getId() + ", Type: " + Client.class.getName()
            );
        }

        Client currentClient = repository.findById(client.getId()).get();
        client.setDocumentId(currentClient.getDocumentId());
        client.setType(currentClient.getType());

        return repository.save(client);
    }

    public Client update(ClientDTO client) {
        return update(new Client(client.getId(), client.getName(), client.getEmail(), null, null));
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("It is not possible to delete a client with orders");
        }
    }

    public List<ClientDTO> findAll() {
        List<ClientDTO> clients = new ArrayList<>();

        for (Client client : repository.findAll()) {
            clients.add(new ClientDTO(client));
        }

        return clients;
    }

    public Page<ClientDTO> findPage(Integer page, Integer linesPerPage,
                                    String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<Client> pages = repository.findAll(pageRequest);

        return pages.map(ClientDTO::new);
    }

    @Transactional
    public Client insert(ClientNewDTO client) {
        Client newClient = new Client(
            null,
            client.getName(),
            client.getEmail(),
            client.getDocumentId(),
            ClientType.toEnum(client.getType())
        );

        City city = new City(client.getCityId(), null, null);

        Address address = new Address(
            null,
            client.getStreetName(),
            client.getNumber(),
            client.getApt(),
            client.getDistrict(),
            client.getZip(),
            newClient,
            city
        );

        newClient.getAddresses().add(address);
        newClient.getPhones().add(client.getPhone1());

        if (client.getPhone2() != null)
            newClient.getPhones().add(client.getPhone2());

        if (client.getPhone3() != null)
            newClient.getPhones().add(client.getPhone3());

        addressRepository.save(address);

        return insert(newClient);
    }

    public Client insert(Client client) {
        return repository.save(client);
    }
}
