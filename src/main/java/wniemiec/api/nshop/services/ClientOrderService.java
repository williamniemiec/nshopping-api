package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.*;
import wniemiec.api.nshop.domain.enums.PaymentStatus;
import wniemiec.api.nshop.exceptions.ObjectNotFoundException;
import wniemiec.api.nshop.repositories.ClientOrderRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class ClientOrderService {

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

    public ClientOrder searchById(Integer id) {
        Optional<ClientOrder> order = repository.findById(id);

        return order.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + ClientOrder.class.getName()
        ));
    }

    public ClientOrder insert(ClientOrder order) {
        order.setId(null);
        order.setDate(new Date());
        order.getPayment().setStatus(PaymentStatus.PENDING);
        order.getPayment().setClientOrder(order);

        if (order.getPayment() instanceof BoletoPayment) {
            BoletoPayment payment = (BoletoPayment) order.getPayment();
            boletoService.fillPayment(payment, order.getDate());
        }

        ClientOrder storedOrder = repository.save(order);
        paymentService.save(storedOrder.getPayment());

        for (ClientOrderItem orderItem : storedOrder.getProducts()) {
            orderItem.setDiscount(0.0);
            Product p2 = orderItem.getProduct();
            Product p = productService.findOne(orderItem.getProduct().getId());
            orderItem.setPrice(p.getPrice());
            orderItem.setOrder(storedOrder);
        }

        clientOrderItemService.save(order.getProducts());

        return storedOrder;
    }
}
