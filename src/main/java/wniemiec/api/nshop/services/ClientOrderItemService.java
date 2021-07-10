package wniemiec.api.nshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wniemiec.api.nshop.domain.ClientOrderItem;
import wniemiec.api.nshop.repositories.ClientOrderItemRepository;

import java.util.Set;

@Service
public class ClientOrderItemService {

    @Autowired
    private ClientOrderItemRepository repository;

    public void save(Set<ClientOrderItem> products) {
        repository.saveAll(products);
    }
}
