package wniemiec.api.nshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wniemiec.api.nshopping.domain.*;
import wniemiec.api.nshopping.dto.ClientOrderDTO;
import wniemiec.api.nshopping.repositories.ClientOrderRepository;
import wniemiec.api.nshopping.security.UserSpringSecurity;
import wniemiec.api.nshopping.services.exceptions.AuthorizationException;
import wniemiec.api.nshopping.services.exceptions.ObjectNotFoundException;
import java.util.Optional;


/**
 * Responsible for providing client order services.
 */
@Service
public class ClientOrderService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ClientOrderRepository repository;

    @Autowired
    private BoletoPaymentService boletoService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientOrderItemService clientOrderItemService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public ClientOrder searchById(Integer id) {
        Optional<ClientOrder> order = repository.findById(id);

        return order.orElseThrow(() -> new ObjectNotFoundException(
            "Object not found! Id: " 
            + id 
            + ", Type: " 
            + ClientOrder.class.getName()
        ));
    }

    public ClientOrder insert(ClientOrderDTO orderDto) {
        ClientOrder order = orderDto.toClientOrder(
            clientService.findById(orderDto.getClient().getId())
        );
              
        storeClientOrder(order);
        storeClientOrderItems(order);
        storeClientOrderPayment(order);

        emailService.sendOrderConfirmationHtmlEmail(order);

        return order;
    }

    private void storeClientOrder(ClientOrder order) {
        ClientOrder storedOrder = repository.save(order);

        order.setId(storedOrder.getId());
    }

    private void fillOrderItemsUsingItsId(ClientOrder storedOrder) {
        for (ClientOrderItem orderItem : storedOrder.getProducts()) {
            orderItem.setDiscount(0.0);
            
            Product p = productService.findOne(orderItem.getProduct().getId());
            orderItem.setProduct(p);
            orderItem.setPrice(p.getPrice());
            orderItem.setOrder(storedOrder);
        }
    }

    private void storeClientOrderItems(ClientOrder order) {
        fillOrderItemsUsingItsId(order);
        
        clientOrderItemService.save(order.getProducts());
    }

    private void storeClientOrderPayment(ClientOrder order) {
        if (order.getPayment() instanceof BoletoPayment) {
            BoletoPayment payment = (BoletoPayment) order.getPayment();

            boletoService.fillPayment(payment, order.getDate());
        }
        
        paymentService.save(order.getPayment());
    }

    public Page<ClientOrder> findPage(Integer page, Integer linesPerPage,
                                      String orderBy, String direction) {
        UserSpringSecurity user = UserService.authenticatedUser();

        if (user == null) {
            throw new AuthorizationException("Access denied");
        }

        Client client = clientService.findById(user.getId());
        PageRequest pageRequest = PageRequest.of(
            page, 
            linesPerPage, 
            Sort.Direction.valueOf(direction), 
            orderBy
        );

        return repository.findByClient(client, pageRequest);
    }
}
