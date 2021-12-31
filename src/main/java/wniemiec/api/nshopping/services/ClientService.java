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
import wniemiec.api.nshop.domain.Client;
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


/**
 * Responsible for providing client services.
 */
@Service
public class ClientService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
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


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public Client searchById(Integer id) {
        if (!hasSearchAuthorization(id))
            throw generateAccessDeniedException();

        Optional<Client> client = repository.findById(id);

        return client.orElseThrow(() -> generateObjectNotFoundException(id));
    }

    private boolean hasSearchAuthorization(Integer id) {
        UserSpringSecurity authenticatedUser = UserService.authenticatedUser();

        if (authenticatedUser == null) {
            return false;
        }

        return  authenticatedUser.hasRole(Profile.ADMIN) 
                || authenticatedUser.getId().equals(id);
    }

    private AuthorizationException generateAccessDeniedException() {
        return new AuthorizationException("Access denied");
    }

    private ObjectNotFoundException generateObjectNotFoundException(Integer id) {
        return new ObjectNotFoundException(
            "Object not found! Id: " 
            + id
            + ", Type: " 
            + Client.class.getName()
        );
    }

    public Client update(Client client) {
        if (client.getId() == null) {
            throw generateObjectNotFoundException(client.getId());
        }

        Client currentClient = findById(client.getId());

        client.setDocumentId(currentClient.getDocumentId());
        client.setType(currentClient.getType());

        return repository.save(client);
    }

    public Client update(ClientDTO clientDto) {
        return update(clientDto.toClient());
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("It is not possible to delete a " + 
                                             "client with orders");
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
        PageRequest pageRequest = PageRequest.of(
            page, 
            linesPerPage, 
            Sort.Direction.valueOf(direction), 
            orderBy
        );
        Page<Client> pages = repository.findAll(pageRequest);

        return pages.map(ClientDTO::new);
    }

    @Transactional
    public Client insert(ClientNewDTO clientDto) {
        Client newClient = clientDto.toClient();
        
        encriptClientPassword(newClient);

        addressRepository.save(newClient.getAddresses().get(0));

        return insert(newClient);
    }

    private void encriptClientPassword(Client client) {
        String password = client.getPassword();
        
        client.setPassword(
            passwordEncoder.encode(password)
        );
    }

    public Client insert(Client client) {
        return repository.save(client);
    }

    public Client findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Client findByEmail(String email) {
        Client client = repository.findByEmail(email);

        if (hasSearchAuthorization(client.getId())) {
            throw generateAccessDeniedException();
        }

        return client;
    }

    public void save(Client client) {
        repository.save(client);
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        if (!hasUploadPictureAuthorization()) {
            throw generateAccessDeniedException();
        }

        BufferedImage jpgImage = parseImage(multipartFile);

        return s3service.uploadFile(
            imageService.getInputStream(jpgImage, "jpg"), 
            generateImageFilename(), 
            "image"
        );
    }

    private boolean hasUploadPictureAuthorization() {
        UserSpringSecurity user = UserService.authenticatedUser();

        return (user != null);
    }

    private BufferedImage parseImage(MultipartFile multipartFile) {
        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);
        
        return jpgImage;
    }

    private String generateImageFilename() {
        UserSpringSecurity user = UserService.authenticatedUser();

        return prefix + user.getId() + ".jpg";
    }
}
