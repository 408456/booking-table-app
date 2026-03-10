package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.mapper.TableMapper;
import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.entity.TableEntity;
import goltsman.bookingtableapp.model.request.restaurant.CreateTableRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateTableRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.TableListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.TableResponse;
import goltsman.bookingtableapp.repository.RestaurantRepository;
import goltsman.bookingtableapp.repository.TableRepository;
import goltsman.bookingtableapp.repository.specification.TableSpecification;
import goltsman.bookingtableapp.service.TableService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final TableMapper tableMapper;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public TableResponse create(CreateTableRequest request) {
        log.info("Попытка создать стол для ресторана с id {}", request.getRestaurantId());

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id " + request.getRestaurantId() + " не найден"));

        TableEntity table = tableMapper.toEntity(request);
        table.setRestaurant(restaurant);

        tableRepository.save(table);
        log.info("Стол успешно создан с id {}", table.getId());

        return tableMapper.toResponse(table);
    }

    @Override
    @Transactional
    public TableResponse update(Long id, UpdateTableRequest request) {
        log.info("Попытка обновить стол с id {}", id);

        TableEntity table = tableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Стол с id " + id + " не найден"));

        // Если передан новый restaurantId, проверяем существование ресторана
        if (request.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                    .orElseThrow(() -> new EntityNotFoundException("Ресторан с id " + request.getRestaurantId() + " не найден"));
            table.setRestaurant(restaurant);
        }

        tableMapper.updateEntity(table, request);
        tableRepository.save(table);

        log.info("Стол с id {} успешно обновлен", id);
        return tableMapper.toResponse(table);
    }

    @Override
    @Transactional
    public MessageResponse delete(Long id) {
        log.info("Попытка удалить стол с id {}", id);

        TableEntity table = tableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Стол с id " + id + " не найден"));

        tableRepository.delete(table);
        log.info("Стол с id {} успешно удален", id);

        return MessageResponse.builder().message("Стол успешно удален").build();
    }

    @Override
    @Transactional(readOnly = true)
    public TableResponse getTable(Long id) {
        TableEntity table = tableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Стол с id " + id + " не найден"));
        return tableMapper.toResponse(table);
    }

    @Override
    @Transactional(readOnly = true)
    public TableListResponse getTables(Long restaurantId, Integer minSeats, Integer maxSeats,
                                       Boolean isAvailable, Pageable pageable) {

        Specification<TableEntity> specification = Specification
                .where(TableSpecification.byRestaurantId(restaurantId))
                .and(TableSpecification.byMinSeats(minSeats))
                .and(TableSpecification.byMaxSeats(maxSeats))
                .and(TableSpecification.byIsAvailable(isAvailable));

        Page<TableEntity> page = tableRepository.findAll(specification, pageable);
        var tables = page.getContent().stream()
                .map(tableMapper::toResponse)
                .toList();

        return TableListResponse.builder()
                .totalCount((int) page.getTotalElements())
                .page(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .limit(pageable.getPageSize())
                .tables(tables)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableResponse> getTablesByRestaurant(Long restaurantId) {
        log.info("Получение всех столов ресторана {}", restaurantId);
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id " + restaurantId + " не найден"));
        List<TableEntity> tables = tableRepository.findAllByRestaurantId(restaurantId);
        return tables.stream()
                .map(tableMapper::toResponse)
                .toList();
    }
}