package wniemiec.api.nshopping.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshopping.domain.ClientOrderItem;
import wniemiec.api.nshopping.repositories.ClientOrderItemRepository;
import java.util.Set;


/**
 * Responsible for providing client order item services.
 */
@Service
public class ClientOrderItemService {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private ClientOrderItemRepository repository;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public void save(Set<ClientOrderItem> products) {
        repository.saveAll(products);
    }
}
