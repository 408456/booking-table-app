package goltsman.bookingtableapp.service;


import goltsman.bookingtableapp.model.request.restaurant.CreateTableRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateTableRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.TableListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.TableResponse;
import org.springframework.data.domain.Pageable;

public interface TableService {

    TableResponse create(CreateTableRequest request);

    TableResponse update(Long id, UpdateTableRequest request);

    MessageResponse delete(Long id);

    TableResponse getTable(Long id);

    TableListResponse getTables(
            Long restaurantId,
            Integer minSeats,
            Integer maxSeats,
            Boolean isAvailable,
            Pageable pageable
    );
}
