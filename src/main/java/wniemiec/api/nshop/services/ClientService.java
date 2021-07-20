package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wniemiec.api.nshop.domain.Address;
import wniemiec.api.nshop.domain.City;
import wniemiec.api.nshop.domain.Client;
import wniemiec.api.nshop.domain.enums.ClientType;
import wniemiec.api.nshop.domain.enums.Profile;
import wniemiec.api.nshop.dto.ClientDTO;
import wniemiec.api.nshop.dto.ClientNewDTO;
import wniemiec.api.nshop.security.UserSpringSecurity;
import wniemiec.api.nshop.services.exceptions.AuthorizationException;
import wniemiec.api.nshop.services.exceptions.DataIntegrityException;
import wniemiec.api.nshop.services.exceptions.ObjectNotFoundException;
import wniemiec.api.nshop.repositories.AddressRepository;
import wniemiec.api.nshop.repositories.ClientRepository;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private S3Service s3service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Client searchById(Integer id) {
        UserSpringSecurity user = UserService.authenticatedUser();
        if (user == null || (!user.hasRole(Profile.ADMIN) && !id.equals(user.getId())))
            throw new AuthorizationException("Access denied");

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
        return update(new Client(client.getId(), client.getName(), client.getEmail(), null, null, null));
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
            ClientType.toEnum(client.getType()),
            passwordEncoder.encode(client.getPassword())
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

    public Client findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Client findByEmail(String email) {
        Client client = repository.findByEmail(email);
        UserSpringSecurity user = UserService.authenticatedUser();

        if (user == null || (!user.hasRole(Profile.ADMIN) && !client.getId().equals(user.getId())))
            throw new AuthorizationException("Access denied");

        return client;
    }

    public void save(Client client) {
        repository.save(client);
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSpringSecurity user = UserService.authenticatedUser();
        if (user == null)
            throw new AuthorizationException("Access denied");

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);
        String filename = prefix + user.getId() + ".jpg";

        return s3service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), filename, "image");
    }
}
