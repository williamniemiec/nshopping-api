package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.*;
import wniemiec.api.nshop.dto.ClientOrderDTO;
import wniemiec.api.nshop.security.UserSpringSecurity;
import wniemiec.api.nshop.services.exceptions.AuthorizationException;
import wniemiec.api.nshop.services.exceptions.ObjectNotFoundException;
import wniemiec.api.nshop.repositories.ClientOrderRepository;
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
                "Object not found! Id: " + id + ", Type: " + ClientOrder.class.getName()
        ));
    }

    public ClientOrder insert(ClientOrderDTO orderDto) {
        ClientOrder order = orderDto.toClientOrder(
            clientService.findById(orderDto.getClient().getId())
        );

/*        
        order.setId(null);
        order.setClient();
        order.setDate(new Date());
        order.getPayment().setStatus(PaymentStatus.PENDING);
        order.getPayment().setClientOrder(order);
*/

        if (order.getPayment() instanceof BoletoPayment) {
            BoletoPayment payment = (BoletoPayment) order.getPayment();
            boletoService.fillPayment(payment, order.getDate());
        }

        ClientOrder storedOrder = repository.save(order);
        paymentService.save(storedOrder.getPayment());

        for (ClientOrderItem orderItem : storedOrder.getProducts()) {
            orderItem.setDiscount(0.0);
            Product p = productService.findOne(orderItem.getProduct().getId());
            orderItem.setProduct(p);
            orderItem.setPrice(p.getPrice());
            orderItem.setOrder(storedOrder);
        }

        clientOrderItemService.save(order.getProducts());

        //System.out.println(storedOrder);
        emailService.sendOrderConfirmationHtmlEmail(storedOrder);

        return storedOrder;
    }

    public Page<ClientOrder> findPage(Integer page, Integer linesPerPage,
                                      String orderBy, String direction) {
        UserSpringSecurity user = UserService.authenticatedUser();

        if (user == null)
            throw new AuthorizationException("Access denied");

        Client client = clientService.findById(user.getId());
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repository.findByClient(client, pageRequest);
    }
}
